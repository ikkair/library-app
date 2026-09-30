package dev.ikkair.library_app.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Books extends VBox {

    public Books() {
        Label title = new Label("Books");

        getChildren().add(title);
    }
}
