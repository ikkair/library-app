package dev.ikkair.library_app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dev.ikkair.library_app.controller.AddBookController;
import dev.ikkair.library_app.controller.BooksController;
import dev.ikkair.library_app.controller.BorrowController;
import dev.ikkair.library_app.controller.MemberSelectionController;
import dev.ikkair.library_app.controller.MembersController;
import dev.ikkair.library_app.controller.SidePanelController;
import dev.ikkair.library_app.database.Database;
import dev.ikkair.library_app.database.DatabaseInitializer;
import dev.ikkair.library_app.view.AddBookPageView;
import dev.ikkair.library_app.view.BooksPageView;
import dev.ikkair.library_app.view.BorrowPageView;
import dev.ikkair.library_app.view.MemberSelectionPageView;
import dev.ikkair.library_app.view.MembersPageView;
import dev.ikkair.library_app.view.SidePanel;
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
					"Failed to initialize the database", e);
		}
		BorderPane root = new BorderPane();
		SidePanel sidePanel = new SidePanel();
		BooksPageView booksPageView = new BooksPageView();
		BooksController bookController = new BooksController(booksPageView);
		AddBookPageView addBookPageView = new AddBookPageView();
		@SuppressWarnings("unused")
		AddBookController addBookController = new AddBookController(addBookPageView,
				() -> {
					bookController.refreshBooks();
					root.setCenter(booksPageView);
				},
				() -> {
					root.setCenter(booksPageView);
				});
		booksPageView.getAddBookButton().setOnAction(
				event -> {
					addBookPageView.clearForm();
					root.setCenter(addBookPageView);
				});
		MembersPageView membersPageView = new MembersPageView();
		@SuppressWarnings("unused")
		MembersController memberController = new MembersController(membersPageView);
		BorrowPageView borrowPageView = new BorrowPageView();
		BorrowController borrowController = new BorrowController(borrowPageView);
		borrowController.setOnBookReturned(
				bookController::refreshBorrowedBooks);
		bookController.setOnBorrowRequested(book -> {
			MemberSelectionPageView selectionPage = new MemberSelectionPageView();
			new MemberSelectionController(
					selectionPage,
					book,
					() -> root.setCenter(booksPageView),
					() -> {
						borrowController.refreshBorrow();
						bookController.refreshBorrowedBooks();
						root.setCenter(booksPageView);
					});
			root.setCenter(selectionPage);
		});
		@SuppressWarnings("unused")
		SidePanelController sidePanelController = new SidePanelController(
				sidePanel,
				root,
				booksPageView,
				membersPageView,
				borrowPageView);
		root.setLeft(sidePanel);
		Scene scene = new Scene(root, 1280, 720);
		scene.getStylesheets().add(
				getClass().getResource("/style.css").toExternalForm());
		stage.setTitle("Library Management System");
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
