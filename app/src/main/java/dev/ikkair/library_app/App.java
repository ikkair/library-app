package dev.ikkair.library_app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dev.ikkair.library_app.controller.BooksController;
import dev.ikkair.library_app.controller.SidePanelController;
import dev.ikkair.library_app.view.BooksPageView;
import dev.ikkair.library_app.view.BorrowingPageView;
import dev.ikkair.library_app.view.DashboardPageView;
import dev.ikkair.library_app.view.MembersPageView;
import dev.ikkair.library_app.view.SidePanel;
import dev.ikkair.library_app.database.Database;
import dev.ikkair.library_app.database.DatabaseInitializer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        try (Connection connection = Database.connect()) {
            DatabaseInitializer.initialize(connection);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(
                "Failed to initialize the database", e
            );
        }
        BorderPane root = new BorderPane();
        SidePanel sidePanel = new SidePanel();

        DashboardPageView dashboardPageView = new DashboardPageView();
        BooksPageView booksPageView = new BooksPageView();
        MembersPageView membersPageView = new MembersPageView();
        BorrowingPageView borrowingPageView = new BorrowingPageView();

        @SuppressWarnings("unused")
		BooksController bookController = new BooksController(booksPageView);
        @SuppressWarnings("unused")
		SidePanelController sidePanelController = new SidePanelController(
        	sidePanel,
        	root,
        	dashboardPageView,
            booksPageView,
            membersPageView,
            borrowingPageView
        );

        root.setLeft(sidePanel);
        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(
            getClass().getResource("/style.css").toExternalForm()
        );
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
