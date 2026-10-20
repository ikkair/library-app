package dev.ikkair.library_app.view;

import java.util.List;

import dev.ikkair.library_app.model.Book;
import dev.ikkair.library_app.model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MemberSelectionPageView extends VBox {

	private final Label bookLabel;
	private final TableView<Member> memberTable;
	private final ObservableList<Member> memberData;
	private final Button confirmButton;
	private final Button cancelButton;
	private final Label statusLabel;

	public MemberSelectionPageView() {
		getStyleClass().add("page");

		Label title = new Label("Select Member");

		bookLabel = new Label("No book selected");

		memberData = FXCollections.observableArrayList();
		memberTable = new TableView<>(memberData);

		TableColumn<Member, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(
				new PropertyValueFactory<>("id"));

		TableColumn<Member, String> nameColumn = new TableColumn<>("Nama");
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(
				new PropertyValueFactory<>("name"));

		TableColumn<Member, String> emailColumn = new TableColumn<>("Email");
		emailColumn.setMinWidth(250);
		emailColumn.setCellValueFactory(
				new PropertyValueFactory<>("email"));

		memberTable.getColumns().addAll(
				List.of(idColumn, nameColumn, emailColumn));

		memberTable.setColumnResizePolicy(
				TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

		memberTable.setPlaceholder(
				new Label("Tidak ada anggota ditemukan"));

		VBox.setVgrow(memberTable, Priority.ALWAYS);

		statusLabel = new Label();

		confirmButton = new Button("Confirm Borrow");
		cancelButton = new Button("Cancel");

		javafx.scene.layout.HBox actions = new javafx.scene.layout.HBox(
				8, cancelButton, confirmButton);

		actions.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

		getChildren().addAll(
				title,
				bookLabel,
				memberTable,
				statusLabel,
				actions);
	}

	public void setBook(Book book) {
		bookLabel.setText(
				"Book: " + book.getTitle()
						+ " (ISBN: " + book.getIsbn() + ")");
	}

	public void setMembers(List<Member> members) {
		memberData.setAll(members);
	}

	public Member getSelectedMember() {
		return memberTable.getSelectionModel().getSelectedItem();
	}

	public Button getConfirmButton() {
		return confirmButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public Label getStatusLabel() {
		return statusLabel;
	}
}
