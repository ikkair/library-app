package dev.ikkair.library_app.view;

import java.time.LocalDate;
import java.util.List;

import dev.ikkair.library_app.model.Borrow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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

public class BorrowPageView extends VBox {

	private final Button returnButton;
	private final Label statusLabel;
	private final TextField searchField;
	private final Button searchButton;
	private final ComboBox<String> searchColumn;
	private final TableView<Borrow> borrowTable;
	private final ObservableList<Borrow> borrowData;
	private final Button previousButton;
	private final Button nextButton;
	private final Label paginationLabel;

	public BorrowPageView() {
		getStyleClass().add("page");
		Label title = new Label("Peminjaman");
		title.getStyleClass().add("page-title");
		Label subtitle = new Label("Kelola transaksi peminjaman dan pengembalian buku");
		subtitle.getStyleClass().add("page-subtitle");
		VBox titleText = new VBox(4, title, subtitle);
		HBox titleBar = new HBox(titleText);
		titleBar.getStyleClass().add("page-header");
		titleBar.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(titleText, Priority.ALWAYS);
		searchField = new TextField();
		searchField.setPromptText("Cari peminjaman...");
		searchField.getStyleClass().add("search-field");
		searchField.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(searchField, Priority.ALWAYS);
		searchColumn = new ComboBox<>();
		searchColumn.getItems().addAll(
				"Anggota",
				"Buku",
				"ISBN");
		searchColumn.setValue("Anggota");
		searchColumn.setMinWidth(160);
		searchButton = new Button("Cari");
		searchButton.getStyleClass().add("search-button");
		HBox searchBar = new HBox(
				8,
				searchField,
				searchColumn,
				searchButton);
		searchBar.setAlignment(Pos.CENTER_LEFT);
		searchBar.getStyleClass().add("search-container");
		statusLabel = new Label();
		statusLabel.getStyleClass().add("search-status");
		previousButton = new Button("Sebelumnya");
		previousButton.getStyleClass().add("secondary-button");
		paginationLabel = new Label("Halaman 1 dari 1");
		paginationLabel.getStyleClass().add("search-status");
		nextButton = new Button("Berikutnya");
		nextButton.getStyleClass().add("secondary-button");
		HBox paginationBar = new HBox(
				8,
				previousButton,
				paginationLabel,
				nextButton);
		paginationBar.getStyleClass().add("pagination-bar");
		paginationBar.setAlignment(Pos.CENTER);
		borrowData = FXCollections.observableArrayList();
		borrowTable = new TableView<>(borrowData);
		TableColumn<Borrow, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setMinWidth(55);
		idColumn.setPrefWidth(65);
		idColumn.setMaxWidth(80);
		idColumn.setCellValueFactory(
				new PropertyValueFactory<>("id"));
		TableColumn<Borrow, String> memberColumn = new TableColumn<>("Anggota");
		memberColumn.setMinWidth(140);
		memberColumn.setCellValueFactory(
				new PropertyValueFactory<>("memberName"));
		TableColumn<Borrow, String> bookColumn = new TableColumn<>("Buku");
		bookColumn.setMinWidth(180);
		bookColumn.setCellValueFactory(
				new PropertyValueFactory<>("bookTitle"));
		TableColumn<Borrow, String> isbnColumn = new TableColumn<>("ISBN");
		isbnColumn.setMinWidth(110);
		isbnColumn.setCellValueFactory(
				new PropertyValueFactory<>("isbn"));
		TableColumn<Borrow, LocalDate> borrowDateColumn = new TableColumn<>("Tanggal Pinjam");
		borrowDateColumn.setMinWidth(120);
		borrowDateColumn.setCellValueFactory(
				new PropertyValueFactory<>("borrowDate"));
		TableColumn<Borrow, LocalDate> dueDateColumn = new TableColumn<>("Jatuh Tempo");
		dueDateColumn.setMinWidth(110);
		dueDateColumn.setCellValueFactory(
				new PropertyValueFactory<>("dueDate"));
		TableColumn<Borrow, LocalDate> returnDateColumn = new TableColumn<>("Tanggal Kembali");
		returnDateColumn.setMinWidth(130);
		returnDateColumn.setCellValueFactory(
				new PropertyValueFactory<>("returnDate"));
		TableColumn<Borrow, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setMinWidth(100);
		statusColumn.setCellValueFactory(
				new PropertyValueFactory<>("status"));
		borrowTable.getColumns().addAll(
				List.of(
						idColumn,
						memberColumn,
						bookColumn,
						isbnColumn,
						borrowDateColumn,
						dueDateColumn,
						returnDateColumn,
						statusColumn));
		borrowTable.setColumnResizePolicy(
				TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
		borrowTable.setPlaceholder(
				new Label("Belum ada transaksi peminjaman"));
		VBox.setVgrow(borrowTable, Priority.ALWAYS);
		returnButton = new Button("Tandai Dikembalikan");
		returnButton.getStyleClass().add("primary-button");
		HBox actions = new HBox(8, returnButton);
		actions.setAlignment(Pos.CENTER_RIGHT);
		getChildren().addAll(
				titleBar,
				searchBar,
				statusLabel,
				borrowTable,
				paginationBar,
				actions);
	}

	public void setBorrows(List<Borrow> borrows) {
		borrowData.setAll(borrows);
	}

	public TextField getSearchField() {
		return searchField;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public ComboBox<String> getSearchColumn() {
		return searchColumn;
	}

	public Borrow getSelectedBorrow() {
		return borrowTable.getSelectionModel().getSelectedItem();
	}

	public Button getReturnButton() {
		return returnButton;
	}

	public Label getStatusLabel() {
		return statusLabel;
	}

	public Button getPreviousButton() {
		return previousButton;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public void setPaginationState(
			int currentPage,
			int totalPages,
			boolean hasPrevious,
			boolean hasNext) {
		paginationLabel.setText(
				"Halaman " + currentPage + " dari " + totalPages);
		previousButton.setDisable(!hasPrevious);
		nextButton.setDisable(!hasNext);
	}
}
