package dev.ikkair.library_app.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MembersPageView extends VBox {

    public MembersPageView() {
        Label title = new Label("Members");

        getChildren().add(title);
    }
}
