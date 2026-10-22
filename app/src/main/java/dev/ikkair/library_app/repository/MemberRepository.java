package dev.ikkair.library_app.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dev.ikkair.library_app.database.Database;
import dev.ikkair.library_app.model.Member;

public class MemberRepository {

	public List<Member> findAll() throws SQLException {
		String sql = """
				SELECT id, name, email, registration_date
				FROM members
				ORDER BY id COLLATE NOCASE
				""";
		List<Member> members = new ArrayList<>();
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				members.add(mapMember(resultSet));
			}
		}
		return members;
	}

	public int countMembers() throws SQLException {
		String sql = "SELECT COUNT(*) FROM members";
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			resultSet.next();
			return resultSet.getInt(1);
		}
	}

	public List<Member> findPage(int page, int pageSize)
			throws SQLException {
		if (page < 0 || pageSize < 1) {
			throw new IllegalArgumentException(
					"Page must be non-negative and page size must be positive");
		}
		String sql = """
				SELECT id, name, email, registration_date
				FROM members
				ORDER BY id COLLATE NOCASE, id
				LIMIT ? OFFSET ?
				""";
		List<Member> members = new ArrayList<>();
		int offset = Math.multiplyExact(page, pageSize);
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, pageSize);
			statement.setInt(2, offset);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					members.add(mapMember(resultSet));
				}
			}
		}
		return members;
	}

	private Member mapMember(ResultSet resultSet)
			throws SQLException {
		String dateString = resultSet.getString("registration_date");
		LocalDate registrationDate = dateString == null
				? null
				: LocalDate.parse(dateString);
		return new Member(
				resultSet.getInt("id"),
				resultSet.getString("name"),
				resultSet.getString("email"),
				registrationDate);
	}

	public int countSearchResults(String column, String query)
			throws SQLException {
		String columnSql = getSearchColumnSql(column);
		String sql = """
				SELECT COUNT(*)
				FROM members
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

	public List<Member> searchPage(
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
				SELECT id, name, email, registration_date
				FROM members
				WHERE CAST(%s AS TEXT) LIKE ? COLLATE NOCASE
				ORDER BY id
				LIMIT ? OFFSET ?
				""".formatted(columnSql);
		List<Member> members = new ArrayList<>();
		int offset = Math.multiplyExact(page, pageSize);
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "%" + query + "%");
			statement.setInt(2, pageSize);
			statement.setInt(3, offset);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					members.add(mapMember(resultSet));
				}
			}
		}
		return members;
	}

	private String getSearchColumnSql(String column) {
		return switch (column) {
			case "ID" -> "id";
			case "Nama" -> "name";
			case "Email" -> "email";
			case "Tanggal Registrasi" -> "registration_date";
			default -> throw new IllegalArgumentException(
					"Unsupported search column: " + column);
		};
	}

	public void createMember(Member member) throws SQLException {
		String sql = """
				INSERT INTO members (
				    name,
				    email,
				    registration_date
				)
				VALUES (?, ?, ?)
				""";
		try (
				Connection connection = Database.connect();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, member.getName());
			statement.setString(2, member.getEmail());
			statement.setString(
					3,
					member.getRegistrationDate().toString());
			statement.executeUpdate();
		}
	}
}
