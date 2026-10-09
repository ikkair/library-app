package dev.ikkair.library_app.config;

import java.nio.file.Files;
import java.nio.file.Path;

public final class DatabaseConfig {
    private DatabaseConfig() {}

    public static Path getDatabasePath() {
        String os = System.getProperty("os.name")
                          .toLowerCase();

        Path dataDir;

        if (os.contains("win")) {
            String localAppData =
                System.getenv("LOCALAPPDATA");

            if (localAppData == null
                    || localAppData.isBlank()) {
                throw new IllegalStateException(
                    "LOCALAPPDATA is not configured"
                );
            }

            dataDir = Path.of(
                localAppData, "LibraryApp"
            );
        } else if (os.contains("linux")) {
            dataDir = Path.of(
                System.getProperty("user.home"),
                ".local", "share", "library-app"
            );
        } else {
            throw new UnsupportedOperationException(
                "Unsupported operating system: " + os
            );
        }

        try {
            Files.createDirectories(dataDir);
        } catch (java.io.IOException e) {
            throw new RuntimeException(
                "Cannot create application data directory",
                e
            );
        }

        return dataDir.resolve("library.db");
    }

    public static String getJdbcUrl() {
        return "jdbc:sqlite:"
            + getDatabasePath().toAbsolutePath();
    }
}
