package dev.ikkair.library_app.repository;

import dev.ikkair.library_app.database.Database;
import dev.ikkair.library_app.model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
	public List<Book> findAll() throws SQLException {
		String sql = """
				SELECT isbn, title, author,
				       publication_year, publisher
				FROM books
				ORDER BY title COLLATE NOCASE
				""";
		List<Book> books = new ArrayList<>();
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int year = resultSet.getInt("publication_year");
				Year publicationYear = resultSet.wasNull() ? null : Year.of(year);
				Book book = new Book(
						resultSet.getString("isbn"),
						resultSet.getString("title"),
						resultSet.getString("author"),
						publicationYear,
						resultSet.getString("publisher"));
				books.add(book);
			}
		}
		return books;
	}

	public int countBooks() throws SQLException {
		String sql = "SELECT COUNT(*) FROM books";
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			resultSet.next();
			return resultSet.getInt(1);
		}
	}

	public List<Book> findPage(int page, int pageSize)
			throws SQLException {
		if (page < 0 || pageSize < 1) {
			throw new IllegalArgumentException(
					"Page must be non-negative and page size must be positive");
		}
		String sql = """
				SELECT isbn, title, author, publication_year, publisher
				FROM books
				ORDER BY
				    CASE
				        WHEN title GLOB '[A-Za-z]*' THEN 0
				        ELSE 1
				    END,
				    title COLLATE NOCASE
				    LIMIT ? OFFSET ?
				""";
		List<Book> books = new ArrayList<>();
		int offset = Math.multiplyExact(page, pageSize);
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, pageSize);
			statement.setInt(2, offset);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int year = resultSet.getInt("publication_year");
					Year publicationYear = resultSet.wasNull()
							? null
							: Year.of(year);
					books.add(new Book(
							resultSet.getString("isbn"),
							resultSet.getString("title"),
							resultSet.getString("author"),
							publicationYear,
							resultSet.getString("publisher")));
				}
			}
		}

		return books;
	}

	public int countSearchResults(String column, String query)
			throws SQLException {
		String columnSql = getSearchColumnSql(column);
		String sql = """
				SELECT COUNT(*)
				FROM books
				WHERE CAST(%s AS TEXT) LIKE ? COLLATE NOCASE
				""".formatted(columnSql);
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "%" + query + "%");
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSet.next();
				return resultSet.getInt(1);
			}
		}
	}

	public List<Book> searchPage(
			String column,
			String query,
			int page,
			int pageSize) throws SQLException {
		if (page < 0 || pageSize < 1) {
			throw new IllegalArgumentException(
					"Page must be non-negative and page size must be positive");
		}
		String columnSql = getSearchColumnSql(column);
		String sql = """
				SELECT isbn, title, author, publication_year, publisher
				FROM books
				WHERE CAST(%s AS TEXT) LIKE ? COLLATE NOCASE
				ORDER BY
				    CASE
				        WHEN title GLOB '[A-Za-z]*' THEN 0
				        ELSE 1
				    END,
				    title COLLATE NOCASE
				LIMIT ? OFFSET ?
				""".formatted(columnSql);
		List<Book> books = new ArrayList<>();
		int offset = Math.multiplyExact(page, pageSize);
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "%" + query + "%");
			statement.setInt(2, pageSize);
			statement.setInt(3, offset);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int year = resultSet.getInt("publication_year");
					Year publicationYear = resultSet.wasNull()
							? null
							: Year.of(year);
					books.add(new Book(
							resultSet.getString("isbn"),
							resultSet.getString("title"),
							resultSet.getString("author"),
							publicationYear,
							resultSet.getString("publisher")));
				}
			}
		}
		return books;
	}

	private String getSearchColumnSql(String column) {
		return switch (column) {
			case "ISBN" -> "isbn";
			case "Judul" -> "title";
			case "Pengarang" -> "author";
			case "Tahun" -> "publication_year";
			case "Penerbit" -> "publisher";
			default -> throw new IllegalArgumentException(
					"Unsupported search column: " + column);
		};
	}
}
