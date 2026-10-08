package dev.ikkair.library_app.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardPageView extends VBox {

    public DashboardPageView() {
        Label title = new Label("Dashboard");

        getChildren().addAll(title);
    }
}
