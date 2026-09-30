package dev.ikkair.library_app.view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SidePanel extends VBox {

    public SidePanel(BorderPane root) {

		getStyleClass().add("sidebar");

        Button dashboardButton = new Button("Dashboard");
        Button booksButton = new Button("Books");
        Button membersButton = new Button("Members");
        Button borrowingButton = new Button("Borrowing");

        dashboardButton.getStyleClass().add("menu-button");
        booksButton.getStyleClass().add("menu-button");
        membersButton.getStyleClass().add("menu-button");
        borrowingButton.getStyleClass().add("menu-button");

        getChildren().addAll(
            dashboardButton,
            booksButton,
            membersButton,
            borrowingButton
        );

        dashboardButton.setOnAction(event -> {
            root.setCenter(new Dashboard());
        });

        booksButton.setOnAction(event -> {
            root.setCenter(new Books());
        });

        membersButton.setOnAction(event -> {
            root.setCenter(new Members());
        });

        borrowingButton.setOnAction(event -> {
            root.setCenter(new Borrowing());
        });
    }
}
