package dev.ikkair.library_app.view;

import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import dev.ikkair.library_app.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
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
	private final Button addBookButton;
	private static final PseudoClass BORROWED = PseudoClass.getPseudoClass("borrowed");
	private Consumer<Book> onBorrowRequested = book -> {
	};
	private final Set<String> borrowedIsbns = new HashSet<>();

	public BooksPageView() {
		getStyleClass().add("page");
		Label title = new Label("Buku");
		title.getStyleClass().add("page-title");
		Label subtitle = new Label("Kelola koleksi buku perpustakaan Anda");
		subtitle.getStyleClass().add("page-subtitle");
		VBox titleText = new VBox(4, title, subtitle);
		addBookButton = new Button("Tambah Buku");
		addBookButton.getStyleClass().add("primary-button");
		HBox titleBar = new HBox(16, titleText, addBookButton);
		titleBar.getStyleClass().add("page-header");
		titleBar.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
		HBox.setHgrow(titleText, Priority.ALWAYS);
		this.searchField = new TextField();
		this.searchField.setPromptText("Cari buku...");
		this.searchField.getStyleClass().add("search-field");
		searchField.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(searchField, Priority.ALWAYS);
		this.searchColumn = new ComboBox<>();
		this.searchColumn.getItems().addAll(
				"ISBN",
				"Judul",
				"Pengarang",
				"Tahun",
				"Penerbit");
		this.searchColumn.setValue("Judul");
		this.searchColumn.setPromptText("Cari berdasarkan");
		this.searchColumn.setMinWidth(160);
		this.searchButton = new Button("Cari");
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
		bookTable.setRowFactory(table -> {
			TableRow<Book> row = new TableRow<>();

			row.setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.PRIMARY
						&& event.getClickCount() == 2
						&& !row.isEmpty()) {

					Book book = row.getItem();

					if (!borrowedIsbns.contains(book.getIsbn())) {
						onBorrowRequested.accept(book);
					}
				}
			});
			row.itemProperty().addListener((observable, oldBook, newBook) -> {
				updateRowStyle(row, newBook);
			});
			return row;
		});
		TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
		isbnColumn.setMaxWidth(100);
		isbnColumn.setSortable(false);
		isbnColumn.setCellValueFactory(
				new PropertyValueFactory<>("isbn"));
		TableColumn<Book, String> titleColumn = new TableColumn<>("Judul");
		titleColumn.setMinWidth(200);
		titleColumn.setPrefWidth(350);
		titleColumn.setSortable(false);
		titleColumn.setCellValueFactory(
				new PropertyValueFactory<>("title"));
		TableColumn<Book, String> authorColumn = new TableColumn<>("Pengarang");
		authorColumn.setSortable(false);
		authorColumn.setCellValueFactory(
				new PropertyValueFactory<>("author"));
		TableColumn<Book, Year> yearColumn = new TableColumn<>("Tahun");
		yearColumn.setMaxWidth(100);
		yearColumn.setPrefWidth(100);
		yearColumn.setMinWidth(90);
		yearColumn.setSortable(false);
		yearColumn.setStyle("-fx-alignment: CENTER_LEFT;");
		yearColumn.setCellValueFactory(
				new PropertyValueFactory<>("publicationYear"));
		TableColumn<Book, String> publisherColumn = new TableColumn<>("Penerbit");
		publisherColumn.setSortable(false);
		publisherColumn.setCellValueFactory(
				new PropertyValueFactory<>("publisher"));
		bookTable.getColumns().addAll(
				List.of(
						isbnColumn,
						titleColumn,
						authorColumn,
						yearColumn,
						publisherColumn));
		bookTable.setColumnResizePolicy(
				TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
		bookTable.setPlaceholder(
				new Label("Tidak ada buku ditemukan"));
		VBox.setVgrow(bookTable, Priority.ALWAYS);
		previousButton = new Button("Sebelumnya");
		nextButton = new Button("Berikutnya");
		pageLabel = new Label("Page 1 of 1");
		HBox paginationBar = new HBox(
				12,
				previousButton,
				pageLabel,
				nextButton);
		paginationBar.getStyleClass().add("pagination-bar");
		paginationBar.setAlignment(javafx.geometry.Pos.CENTER);
		getChildren().addAll(titleBar, searchHBox, searchStatus, bookTable, paginationBar);
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

	public void setOnBorrowRequested(Consumer<Book> callback) {
		this.onBorrowRequested = callback;
	}

	private void updateRowStyle(TableRow<Book> row, Book book) {
		boolean borrowed = book != null
				&& borrowedIsbns.contains(book.getIsbn());
		row.pseudoClassStateChanged(BORROWED, borrowed);
	}

	public void setBorrowedIsbns(Set<String> isbns) {
		borrowedIsbns.clear();
		borrowedIsbns.addAll(isbns);
		bookTable.refresh();
	}

	public Button getAddBookButton() {
		return addBookButton;
	}
}
