package dev.ikkair.library_app.view;

import java.time.LocalDate;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
		setSpacing(16);

		// Header
		Label title = new Label("Tambah Anggota");
		title.getStyleClass().add("page-title");

		Label subtitle = new Label(
				"Tambahkan anggota baru ke perpustakaan");

		subtitle.getStyleClass().add("page-subtitle");

		VBox titleText = new VBox(4, title, subtitle);

		VBox header = new VBox(titleText);
		header.getStyleClass().add("page-header");
		header.setAlignment(Pos.CENTER_LEFT);

		// Form fields
		nameField = createTextField("Masukkan nama anggota");
		emailField = createTextField("Masukkan alamat email");

		registrationDatePicker = new DatePicker(LocalDate.now());
		registrationDatePicker.setPromptText("Pilih tanggal registrasi");
		registrationDatePicker.setMaxWidth(Double.MAX_VALUE);
		registrationDatePicker.setEditable(false);
		registrationDatePicker.getStyleClass().add("form-date-picker");

		VBox form = new VBox(14);
		form.getStyleClass().add("book-form");
		form.setMaxWidth(Double.MAX_VALUE);

		form.getChildren().addAll(
				createField("Nama Anggota", nameField),
				createField("Email", emailField),
				createField("Tanggal Registrasi", registrationDatePicker));

		// Buttons
		saveButton = new Button("Simpan Anggota");
		saveButton.getStyleClass().add("primary-button");

		cancelButton = new Button("Batal");
		cancelButton.getStyleClass().add("secondary-button");

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		HBox buttons = new HBox(10, cancelButton, spacer, saveButton);
		buttons.setAlignment(Pos.CENTER_LEFT);
		buttons.setMaxWidth(Double.MAX_VALUE);
		buttons.getStyleClass().add("form-actions");

		// Status message
		statusLabel = new Label();
		statusLabel.getStyleClass().add("search-status");
		statusLabel.setWrapText(true);

		getChildren().addAll(
				header,
				form,
				buttons,
				statusLabel);
	}

	private TextField createTextField(String prompt) {
		TextField field = new TextField();
		field.setPromptText(prompt);
		field.getStyleClass().add("form-field");
		field.setMaxWidth(Double.MAX_VALUE);
		return field;
	}

	private VBox createField(String labelText, Node input) {
		Label label = new Label(labelText);
		label.getStyleClass().add("form-label");

		VBox fieldBox = new VBox(6, label, input);
		fieldBox.getStyleClass().add("form-field-container");

		return fieldBox;
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
