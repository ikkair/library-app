package dev.ikkair.library_app.view;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class BooksPageView extends VBox {
    private final TextField searchField;
    private final Label searchStatus;

    public BooksPageView() {
        Label title = new Label("Books");

        this.searchField = new TextField();
        this.searchField.setPromptText("Search books...");
        this.searchStatus = new Label();

        getChildren().addAll(title, searchField, searchStatus);
    }

    public TextField getSearchField(){
        return this.searchField;
    }

    public Label getSearchStatus(){
        return this.searchStatus;
    }
}
