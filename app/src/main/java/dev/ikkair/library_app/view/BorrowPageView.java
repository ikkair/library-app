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

	public BorrowPageView() {
		getStyleClass().add("page");
		Label title = new Label("Borrow");
		searchField = new TextField();
		searchField.setPromptText("Cari peminjaman...");
		searchField.getStyleClass().add("search-field");
		searchColumn = new ComboBox<>();
		searchColumn.getItems().addAll(
				"Anggota",
				"Buku",
				"ISBN");
		searchColumn.setValue("Anggota");
		searchColumn.setMinWidth(130);
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
		borrowData = FXCollections.observableArrayList();
		borrowTable = new TableView<>(borrowData);
		TableColumn<Borrow, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(
				new PropertyValueFactory<>("id"));
		TableColumn<Borrow, String> memberColumn = new TableColumn<>("Anggota");
		memberColumn.setMinWidth(160);
		memberColumn.setCellValueFactory(
				new PropertyValueFactory<>("memberName"));
		TableColumn<Borrow, String> bookColumn = new TableColumn<>("Buku");
		bookColumn.setMinWidth(200);
		bookColumn.setCellValueFactory(
				new PropertyValueFactory<>("bookTitle"));
		TableColumn<Borrow, String> isbnColumn = new TableColumn<>("ISBN");
		isbnColumn.setCellValueFactory(
				new PropertyValueFactory<>("isbn"));
		TableColumn<Borrow, LocalDate> borrowDateColumn = new TableColumn<>("Tanggal Pinjam");
		borrowDateColumn.setCellValueFactory(
				new PropertyValueFactory<>("borrowDate"));
		TableColumn<Borrow, LocalDate> dueDateColumn = new TableColumn<>("Jatuh Tempo");
		dueDateColumn.setCellValueFactory(
				new PropertyValueFactory<>("dueDate"));
		TableColumn<Borrow, LocalDate> returnDateColumn = new TableColumn<>("Tanggal Kembali");
		returnDateColumn.setCellValueFactory(
				new PropertyValueFactory<>("returnDate"));
		TableColumn<Borrow, String> statusColumn = new TableColumn<>("Status");
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
		HBox actions = new HBox(8, returnButton);
		actions.setAlignment(Pos.CENTER_RIGHT);
		actions.getStyleClass().add("pagination-bar");
		getChildren().addAll(
				title,
				searchBar,
				statusLabel,
				borrowTable,
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
}
