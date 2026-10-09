package dev.ikkair.library_app.database;

import dev.ikkair.library_app.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private Database() {}

    public static Connection connect()
	    throws SQLException {
	        return DriverManager.getConnection(
	            DatabaseConfig.getJdbcUrl()
	        );
    }
}
