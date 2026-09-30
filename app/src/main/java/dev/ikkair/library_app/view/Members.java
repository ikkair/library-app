package dev.ikkair.library_app.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Members extends VBox {

    public Members() {
        Label title = new Label("Members");

        getChildren().add(title);
    }
}
