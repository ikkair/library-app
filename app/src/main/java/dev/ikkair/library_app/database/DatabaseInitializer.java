package dev.ikkair.library_app.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

	private DatabaseInitializer() {}

	public static void initialize(Connection connection)
		throws SQLException, IOException {

		try (InputStream input = DatabaseInitializer.class
				.getResourceAsStream("/db/schema.sql")) {

			if (input == null) {
				throw new IOException(
					"Missing /db/schema.sql");
			}

			String schema = new String(
					input.readAllBytes(),
					StandardCharsets.UTF_8);

			try (Statement statement = connection.createStatement()) {
				statement.executeUpdate(schema);
			}
		}
	}
}
