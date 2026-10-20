package dev.ikkair.library_app.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.ikkair.library_app.database.Database;
import dev.ikkair.library_app.model.Borrow;

public class BorrowRepository {

	public List<Borrow> findAll() throws SQLException {
		String sql = """
				SELECT
				    br.id,
				    br.member_id,
				    m.name AS member_name,
				    br.isbn,
				    b.title AS book_title,
				    br.borrow_date,
				    br.due_date,
				    br.return_date
				FROM borrows br
				JOIN members m ON m.id = br.member_id
				JOIN books b ON b.isbn = br.isbn
				ORDER BY br.id DESC
				""";
		List<Borrow> borrow = new ArrayList<>();
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				borrow.add(mapBorrow(resultSet));
			}
		}

		return borrow;
	}

	public void createBorrow(
			int memberId,
			String isbn,
			LocalDate borrowDate,
			LocalDate dueDate) throws SQLException {
		if (dueDate.isBefore(borrowDate)) {
			throw new IllegalArgumentException(
					"Due date cannot be before borrow date");
		}
		String checkSql = """
				SELECT COUNT(*)
				FROM borrows
				WHERE isbn = ?
				  AND return_date IS NULL
				""";
		String insertSql = """
				INSERT INTO borrows (
				    member_id,
				    isbn,
				    borrow_date,
				    due_date
				)
				VALUES (?, ?, ?, ?)
				""";
		try (Connection connection = Database.connect()) {
			connection.setAutoCommit(false);
			try {
				try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
					checkStatement.setString(1, isbn);
					try (ResultSet resultSet = checkStatement.executeQuery()) {
						resultSet.next();
						if (resultSet.getInt(1) > 0) {
							throw new SQLException(
									"This book is already borrowed");
						}
					}
				}
				try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
					insertStatement.setInt(1, memberId);
					insertStatement.setString(2, isbn);
					insertStatement.setString(
							3, borrowDate.toString());
					insertStatement.setString(
							4, dueDate.toString());
					insertStatement.executeUpdate();
				}
				connection.commit();
			} catch (SQLException | RuntimeException e) {
				connection.rollback();
				throw e;
			} finally {
				connection.setAutoCommit(true);
			}
		}
	}

	public void markReturned(int borrowId, LocalDate returnDate)
			throws SQLException {
		String sql = """
				UPDATE borrows
				SET return_date = ?
				WHERE id = ?
				  AND return_date IS NULL
				""";
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, returnDate.toString());
			statement.setInt(2, borrowId);
			int updated = statement.executeUpdate();
			if (updated == 0) {
				throw new SQLException(
						"Borrow record not found or already returned");
			}
		}
	}

	private Borrow mapBorrow(ResultSet resultSet)
			throws SQLException {
		String returnDateString = resultSet.getString("return_date");
		LocalDate returnDate = returnDateString == null
				? null
				: LocalDate.parse(returnDateString);
		return new Borrow(
				resultSet.getInt("id"),
				resultSet.getInt("member_id"),
				resultSet.getString("member_name"),
				resultSet.getString("isbn"),
				resultSet.getString("book_title"),
				LocalDate.parse(
						resultSet.getString("borrow_date")),
				LocalDate.parse(
						resultSet.getString("due_date")),
				returnDate);
	}

	public Set<String> findActiveBorrowedIsbns() throws SQLException {
		String sql = """
				SELECT isbn
				FROM borrows
				WHERE return_date IS NULL
				""";
		Set<String> isbns = new HashSet<>();
		try (Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				isbns.add(resultSet.getString("isbn"));
			}
		}
		return isbns;
	}

	public List<Borrow> search(String column, String query)
			throws SQLException {
		String sqlColumn = switch (column) {
			case "Anggota" -> "m.name";
			case "Buku" -> "b.title";
			case "ISBN" -> "br.isbn";
			default -> throw new IllegalArgumentException(
					"Unsupported search column: " + column);
		};
		String sql = """
				SELECT
				    br.id,
				    br.member_id,
				    m.name AS member_name,
				    br.isbn,
				    b.title AS book_title,
				    br.borrow_date,
				    br.due_date,
				    br.return_date
				FROM borrows br
				JOIN members m ON m.id = br.member_id
				JOIN books b ON b.isbn = br.isbn
				WHERE %s LIKE ?
				ORDER BY br.id DESC
				""".formatted(sqlColumn);
		List<Borrow> borrows = new ArrayList<>();
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "%" + query + "%");
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					borrows.add(mapBorrow(resultSet));
				}
			}
		}
		return borrows;
	}
}
