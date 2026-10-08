package dev.ikkair.library_app.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BorrowingPageView extends VBox {

    public BorrowingPageView() {
        Label title = new Label("Borrowing");

        getChildren().add(title);
    }
}
