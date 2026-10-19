package dev.ikkair.library_app.view;

import java.time.format.DateTimeFormatter;
import java.util.List;

import dev.ikkair.library_app.model.Member;
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

public class MembersPageView extends VBox {

    private final TextField searchField;
    private final Button searchButton;
    private final Label searchStatus;
    private final TableView<Member> memberTable;
    private final ObservableList<Member> memberData;
    private final Button previousButton;
    private final Button nextButton;
    private final Label pageLabel;
    private final ComboBox<String> searchColumn;

    public MembersPageView() {
        Label title = new Label("Members");
        getStyleClass().add("page");
        searchField = new TextField();
        searchField.setPromptText("Cari anggota...");
        searchField.getStyleClass().add("search-field");
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
        searchStatus = new Label();
        HBox searchHBox = new HBox(
                8,
                searchField,
                searchColumn,
                searchButton);
        searchHBox.getStyleClass().add("search-container");
        memberData = FXCollections.observableArrayList();
        memberTable = new TableView<>(memberData);
        TableColumn<Member, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setMaxWidth(80);
        idColumn.setSortable(false);
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        TableColumn<Member, String> nameColumn = new TableColumn<>("Nama");
        nameColumn.setMinWidth(200);
        nameColumn.setSortable(false);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        TableColumn<Member, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(250);
        emailColumn.setSortable(false);
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        TableColumn<Member, String> registrationDateColumn = new TableColumn<>("Tanggal Registrasi");
        registrationDateColumn.setSortable(false);
        registrationDateColumn.setCellValueFactory(
                cellData -> {
                    var date = cellData.getValue()
                            .getRegistrationDate();
                    String formattedDate = date == null
                            ? ""
                            : date.format(
                                    DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    return new javafx.beans.property.SimpleStringProperty(
                            formattedDate);
                });
        memberTable.getColumns().addAll(
                java.util.List.of(
                        idColumn,
                        nameColumn,
                        emailColumn,
                        registrationDateColumn));
        memberTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        memberTable.setPlaceholder(
                new Label("Tidak ada anggota ditemukan"));
        VBox.setVgrow(memberTable, Priority.ALWAYS);
        previousButton = new Button("Previous");
        nextButton = new Button("Next");
        pageLabel = new Label("Page 1 of 1");
        HBox paginationBar = new HBox(
                12, previousButton, pageLabel, nextButton);
        paginationBar.getStyleClass().add("pagination-bar");
        paginationBar.setAlignment(Pos.CENTER);
        getChildren().addAll(
                title,
                searchHBox,
                searchStatus,
                memberTable,
                paginationBar);
    }

    public void setMembers(List<Member> members) {
        memberData.setAll(members);
    }

    public void resetTableScroll() {
        memberTable.scrollTo(0);
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public Label getSearchStatus() {
        return searchStatus;
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
        pageLabel.setText(
                "Page " + (currentPage + 1) + " of " + totalPages);

        previousButton.setDisable(!hasPrevious);
        nextButton.setDisable(!hasNext);
    }

    public ComboBox<String> getSearchColumn() {
        return searchColumn;
    }
}
