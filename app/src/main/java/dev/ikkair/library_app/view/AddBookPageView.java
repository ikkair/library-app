package dev.ikkair.library_app.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
        setSpacing(12);

        Label title = new Label("Tambah Buku");

        isbnField = new TextField();
        isbnField.setPromptText("ISBN (wajib)");

        titleField = new TextField();
        titleField.setPromptText("Judul buku (wajib)");

        authorField = new TextField();
        authorField.setPromptText("Pengarang (opsional)");

        publicationDatePicker = new DatePicker();
        publicationDatePicker.setPromptText("Pilih tanggal terbit");
        publicationDatePicker.setMaxWidth(Double.MAX_VALUE);
        publicationDatePicker.setEditable(false);
        publisherField = new TextField();
        publisherField.setPromptText("Penerbit (opsional)");

        saveButton = new Button("Simpan");
        cancelButton = new Button("Batal");

        HBox buttons = new HBox(8, saveButton, cancelButton);
        buttons.setAlignment(Pos.CENTER_LEFT);

        statusLabel = new Label();

        getChildren().addAll(
                title,
                new Label("ISBN"),
                isbnField,
                new Label("Judul"),
                titleField,
                new Label("Pengarang"),
                authorField,
                new Label("Tahun Terbit"),
                publicationDatePicker,
                new Label("Penerbit"),
                publisherField,
                buttons,
                statusLabel);
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
