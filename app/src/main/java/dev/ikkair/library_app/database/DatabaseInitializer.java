package dev.ikkair.library_app.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

	private DatabaseInitializer() {
	}

	public static void initialize(Connection connection)
			throws SQLException, IOException {

		executeResource(connection, "/db/schema.sql");

		if (!hasBooks(connection)) {
			executeResource(connection, "/db/initial-books.sql");
		}

		if (!hasMembers(connection)) {
			executeResource(connection, "/db/initial-members.sql");
		}
	}

	private static boolean hasMembers(Connection connection)
			throws SQLException {

		String sql = "SELECT EXISTS (SELECT 1 FROM members)";

		try (
				Statement statement = connection.createStatement();
				var resultSet = statement.executeQuery(sql)) {

			resultSet.next();
			return resultSet.getBoolean(1);
		}
	}

	private static boolean hasBooks(Connection connection)
			throws SQLException {

		String sql = "SELECT EXISTS (SELECT 1 FROM books)";

		try (
				Statement statement = connection.createStatement();
				var resultSet = statement.executeQuery(sql)) {

			resultSet.next();
			return resultSet.getBoolean(1);
		}
	}

	private static void executeResource(
			Connection connection, String resource)
			throws SQLException, IOException {

		try (InputStream input = DatabaseInitializer.class.getResourceAsStream(resource)) {

			if (input == null) {
				throw new IOException("Missing resource: " + resource);
			}

			String sql = new String(
					input.readAllBytes(),
					StandardCharsets.UTF_8);

			try (Statement statement = connection.createStatement()) {
				statement.executeUpdate(sql);
			}
		}
	}
}
