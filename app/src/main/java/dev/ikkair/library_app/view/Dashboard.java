package dev.ikkair.library_app.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Dashboard extends VBox {

    public Dashboard() {
        Label title = new Label("Dashboard");

        getChildren().addAll(title);
    }
}
