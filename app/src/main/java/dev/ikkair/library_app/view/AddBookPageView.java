package dev.ikkair.library_app.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AddBookPageView extends VBox {

    private final TextField isbnField;
    private final TextField titleField;
    private final TextField authorField;
    private final DatePicker publicationDatePicker;
    private final TextField publisherField;

    private final Button saveButton;
    private final Button cancelButton;
    private final Label statusLabel;

    public AddBookPageView() {
        getStyleClass().add("page");
        setSpacing(16);

        // Header
        Label title = new Label("Tambah Buku");
        title.getStyleClass().add("page-title");

        Label subtitle = new Label(
                "Tambahkan buku baru ke koleksi perpustakaan");

        subtitle.getStyleClass().add("page-subtitle");

        VBox titleText = new VBox(4, title, subtitle);

        VBox header = new VBox(titleText);
        header.getStyleClass().add("page-header");
        header.setAlignment(Pos.CENTER_LEFT);

        // Form fields
        isbnField = createTextField("Masukkan ISBN");
        titleField = createTextField("Masukkan judul buku");
        authorField = createTextField("Masukkan nama pengarang");
        publisherField = createTextField("Masukkan nama penerbit");

        publicationDatePicker = new DatePicker();
        publicationDatePicker.setPromptText("Pilih tanggal terbit");
        publicationDatePicker.setMaxWidth(Double.MAX_VALUE);
        publicationDatePicker.setEditable(false);
        publicationDatePicker.getStyleClass().add("form-date-picker");

        VBox form = new VBox(14);
        form.getStyleClass().add("book-form");

        form.getChildren().addAll(
                createField("ISBN", isbnField),
                createField("Judul Buku", titleField),
                createField("Pengarang", authorField),
                createField("Tanggal Terbit", publicationDatePicker),
                createField("Penerbit", publisherField));
        saveButton = new Button("Simpan Buku");
        saveButton.getStyleClass().add("primary-button");
        cancelButton = new Button("Batal");
        cancelButton.getStyleClass().add("secondary-button");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox buttons = new HBox(10, cancelButton, spacer, saveButton);
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

    private VBox createField(String labelText,
            javafx.scene.Node input) {
        Label label = new Label(labelText);
        label.getStyleClass().add("form-label");

        VBox fieldBox = new VBox(6, label, input);
        fieldBox.getStyleClass().add("form-field-container");

        VBox.setVgrow(input, Priority.NEVER);
        return fieldBox;
    }

    public TextField getIsbnField() {
        return isbnField;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public TextField getAuthorField() {
        return authorField;
    }

    public DatePicker getPublicationDateField() {
        return publicationDatePicker;
    }

    public TextField getPublisherField() {
        return publisherField;
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
        isbnField.clear();
        titleField.clear();
        authorField.clear();
        publicationDatePicker.setValue(null);
        publisherField.clear();
        statusLabel.setText("");
    }
}
