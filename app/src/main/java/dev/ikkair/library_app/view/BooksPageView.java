package dev.ikkair.library_app.view;

import java.time.Year;
import java.util.List;

import dev.ikkair.library_app.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BooksPageView extends VBox {
	private final TextField searchField;
	private final Button searchButton;
	private final Label searchStatus;
	private final TableView<Book> bookTable;
	private final ObservableList<Book> bookData;
	private final Button previousButton;
	private final Button nextButton;
	private final Label pageLabel;
	private final ComboBox<String> searchColumn;

	public BooksPageView() {
		Label title = new Label("Books");
		getStyleClass().add("page");
		this.searchField = new TextField();
		this.searchField.setPromptText("Cari buku...");
		this.searchField.getStyleClass().add("search-field");
		this.searchButton = new Button("Cari");
		this.searchColumn = new ComboBox<>();
		this.searchColumn.getItems().addAll(
				"ISBN",
				"Judul",
				"Pengarang",
				"Tahun",
				"Penerbit");
		this.searchColumn.setValue("Judul");
		this.searchColumn.setPromptText("Cari berdasarkan");
		this.searchColumn.setMinWidth(130);
		this.searchButton.getStyleClass().add("search-button");
		HBox searchHBox = new HBox(
				8,
				searchField,
				searchColumn,
				searchButton);
		searchHBox.getStyleClass().add("search-container");
		this.searchButton.getStyleClass().add("search-button");
		this.searchStatus = new Label();
		bookData = FXCollections.observableArrayList();
		bookTable = new TableView<>(bookData);
		TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
		isbnColumn.setMaxWidth(100);
		isbnColumn.setSortable(false);
		isbnColumn.setCellValueFactory(
				new PropertyValueFactory<>("isbn"));
		TableColumn<Book, String> titleColumn = new TableColumn<>("Judul");
		titleColumn.setMinWidth(300);
		titleColumn.setSortable(false);
		titleColumn.setCellValueFactory(
				new PropertyValueFactory<>("title"));
		TableColumn<Book, String> authorColumn = new TableColumn<>("Pengarang");
		authorColumn.setSortable(false);
		authorColumn.setCellValueFactory(
				new PropertyValueFactory<>("author"));
		TableColumn<Book, Year> yearColumn = new TableColumn<>("Tahun");
		yearColumn.setMaxWidth(50);
		yearColumn.setSortable(false);
		yearColumn.setStyle("-fx-alignment: CENTER;");
		yearColumn.setCellValueFactory(
				new PropertyValueFactory<>("publicationYear"));
		TableColumn<Book, String> publisherColumn = new TableColumn<>("Penerbit");
		publisherColumn.setSortable(false);
		publisherColumn.setCellValueFactory(
				new PropertyValueFactory<>("publisher"));
		bookTable.getColumns().addAll(
				java.util.List.of(
						isbnColumn,
						titleColumn,
						authorColumn,
						yearColumn,
						publisherColumn));
		bookTable.setColumnResizePolicy(
				TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
		bookTable.setPlaceholder(
				new Label("No books found"));
		VBox.setVgrow(bookTable, Priority.ALWAYS);
		previousButton = new Button("Previous");
		nextButton = new Button("Next");
		pageLabel = new Label("Page 1 of 1");
		HBox paginationBar = new HBox(
				12,
				previousButton,
				pageLabel,
				nextButton);
		paginationBar.getStyleClass().add("pagination-bar");
		paginationBar.setAlignment(javafx.geometry.Pos.CENTER);
		getChildren().addAll(title, searchHBox, searchStatus, bookTable, paginationBar);
	}

	public void resetTableScroll() {
		bookTable.scrollTo(0);
	}

	public void setBooks(List<Book> books) {
		bookData.setAll(books);
	}

	public TextField getSearchField() {
		return this.searchField;
	}

	public Label getSearchStatus() {
		return this.searchStatus;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public Button getPreviousButton() {
		return previousButton;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public void setPaginationState(
			int currentPage, int totalPages,
			boolean hasPrevious, boolean hasNext) {
		pageLabel.setText(
				"Page " + (currentPage + 1) + " of " + totalPages);
		previousButton.setDisable(!hasPrevious);
		nextButton.setDisable(!hasNext);
	}

	public ComboBox<String> getSearchColumn() {
		return searchColumn;
	}
}
