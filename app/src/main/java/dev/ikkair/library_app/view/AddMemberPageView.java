package dev.ikkair.library_app.view;

import java.time.LocalDate;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddMemberPageView extends VBox {

	private final TextField nameField;
	private final TextField emailField;
	private final DatePicker registrationDatePicker;

	private final Button saveButton;
	private final Button cancelButton;
	private final Label statusLabel;

	public AddMemberPageView() {
		getStyleClass().add("page");
		setSpacing(12);

		Label title = new Label("Tambah Member");

		nameField = new TextField();
		nameField.setPromptText("Nama anggota (wajib)");

		emailField = new TextField();
		emailField.setPromptText("Email (opsional)");

		registrationDatePicker = new DatePicker(LocalDate.now());
		registrationDatePicker.setPromptText("Tanggal registrasi");
		registrationDatePicker.setMaxWidth(Double.MAX_VALUE);
		registrationDatePicker.setEditable(false);

		saveButton = new Button("Simpan");
		cancelButton = new Button("Batal");

		HBox buttons = new HBox(8, saveButton, cancelButton);
		buttons.setAlignment(Pos.CENTER_LEFT);

		statusLabel = new Label();
		statusLabel.setWrapText(true);

		getChildren().addAll(
				title,
				new Label("Nama"),
				nameField,
				new Label("Email"),
				emailField,
				new Label("Tanggal Registrasi"),
				registrationDatePicker,
				buttons,
				statusLabel);
	}

	public TextField getNameField() {
		return nameField;
	}

	public TextField getEmailField() {
		return emailField;
	}

	public DatePicker getRegistrationDatePicker() {
		return registrationDatePicker;
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public Label getStatusLabel() {
		return statusLabel;
	}

	public void clearForm() {
		nameField.clear();
		emailField.clear();
		registrationDatePicker.setValue(LocalDate.now());
		statusLabel.setText("");
	}
}
