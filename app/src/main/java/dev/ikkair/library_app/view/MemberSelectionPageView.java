package dev.ikkair.library_app.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dev.ikkair.library_app.model.Book;
import dev.ikkair.library_app.model.Member;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MemberSelectionPageView extends VBox {

    private final Label bookLabel;
    private final TableView<Member> memberTable;
    private final ObservableList<Member> memberData;
    private final TextField searchField;
    private final ComboBox<String> searchColumn;
    private final Button searchButton;
    private final Label statusLabel;
    private final Button confirmButton;
    private final Button cancelButton;
    private final Button previousButton;
    private final Button nextButton;
    private final Label pageLabel;
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public MemberSelectionPageView() {
        getStyleClass().add("page");
        setSpacing(12);
        Label title = new Label("Pilih Anggota");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label(
                "Pilih anggota yang akan meminjam buku");
        subtitle.getStyleClass().add("page-subtitle");
        VBox titleText = new VBox(4, title, subtitle);
        VBox header = new VBox(titleText);
        header.getStyleClass().add("page-header");
        header.setAlignment(Pos.CENTER_LEFT);
        bookLabel = new Label("Belum ada buku yang dipilih");
        bookLabel.getStyleClass().add("selection-book-label");
        bookLabel.setWrapText(true);
        searchField = new TextField();
        searchField.setPromptText("Cari anggota...");
        searchField.getStyleClass().add("search-field");
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchField.setMaxWidth(Double.MAX_VALUE);
        searchColumn = new ComboBox<>();
        searchColumn.getItems().addAll(
                "ID",
                "Nama",
                "Email",
                "Tanggal Registrasi");
        searchColumn.setValue("Nama");
        searchColumn.setMinWidth(160);
        searchButton = new Button("Cari");
        searchButton.getStyleClass().add("search-button");
        HBox searchBar = new HBox(
                10,
                searchField,
                searchColumn,
                searchButton);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.getStyleClass().add("search-container");
        memberData = FXCollections.observableArrayList();
        memberTable = new TableView<>(memberData);
        TableColumn<Member, Integer> idColumn =
                new TableColumn<>("ID");
        idColumn.setMinWidth(70);
        idColumn.setMaxWidth(90);
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        TableColumn<Member, String> nameColumn =
                new TableColumn<>("Nama");
        nameColumn.setMinWidth(180);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        TableColumn<Member, String> emailColumn =
                new TableColumn<>("Email");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        TableColumn<Member, String> registrationDateColumn =
                new TableColumn<>("Tanggal Registrasi");
        registrationDateColumn.setMinWidth(150);
        registrationDateColumn.setCellValueFactory(cellData -> {
            LocalDate date =
                    cellData.getValue().getRegistrationDate();
            String formattedDate = date == null
                    ? ""
                    : date.format(DATE_FORMAT);
            return new SimpleStringProperty(formattedDate);
        });
        memberTable.getColumns().addAll(
                List.of(
                        idColumn,
                        nameColumn,
                        emailColumn,
                        registrationDateColumn));
        memberTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        memberTable.setPlaceholder(
                new Label("Tidak ada anggota ditemukan"));
        VBox.setVgrow(memberTable, Priority.ALWAYS);
        statusLabel = new Label();
        statusLabel.getStyleClass().add("search-status");
        statusLabel.setWrapText(true);
        previousButton = new Button("Sebelumnya");
        nextButton = new Button("Berikutnya");
        pageLabel = new Label("Halaman 1 dari 1");
        HBox paginationBar = new HBox(
                12,
                previousButton,
                pageLabel,
                nextButton);
        paginationBar.setAlignment(Pos.CENTER);
        paginationBar.getStyleClass().add("pagination-bar");
        confirmButton = new Button("Konfirmasi Peminjaman");
        confirmButton.getStyleClass().add("primary-button");
        cancelButton = new Button("Batal");
        cancelButton.getStyleClass().add("secondary-button");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox actions = new HBox(
                10,
                cancelButton,
                spacer,
                confirmButton);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.setMaxWidth(Double.MAX_VALUE);
        actions.getStyleClass().add("form-actions");
        getChildren().addAll(
                header,
                bookLabel,
                searchBar,
                statusLabel,
                memberTable,
                paginationBar,
                actions);
    }

    public void setBook(Book book) {
        if (book == null) {
            bookLabel.setText("Belum ada buku yang dipilih");
            return;
        }
        bookLabel.setText(
                "Buku yang dipinjam: "
                        + book.getTitle()
                        + " (ISBN: " + book.getIsbn() + ")");
    }

    public void setMembers(List<Member> members) {
        memberData.setAll(members);
    }

    public Member getSelectedMember() {
        return memberTable.getSelectionModel()
                .getSelectedItem();
    }

    public void resetTableScroll() {
        memberTable.scrollTo(0);
    }

    public void setPaginationState(
            int currentPage,
            int totalPages,
            boolean hasPrevious,
            boolean hasNext) {
        pageLabel.setText(
                "Halaman " + currentPage + " dari " + totalPages);
        previousButton.setDisable(!hasPrevious);
        nextButton.setDisable(!hasNext);
    }

    public Label getBookLabel() {
        return bookLabel;
    }

    public TableView<Member> getMemberTable() {
        return memberTable;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public ComboBox<String> getSearchColumn() {
        return searchColumn;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public Label getSearchStatus() {
        return statusLabel;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getPreviousButton() {
        return previousButton;
    }

    public Button getNextButton() {
        return nextButton;
    }
}
