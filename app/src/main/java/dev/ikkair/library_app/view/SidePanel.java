package dev.ikkair.library_app.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SidePanel extends VBox {

    private final Button dashboardButton;
    private final Button booksButton;
    private final Button membersButton;
    private final Button borrowingButton;

    public SidePanel() {

		getStyleClass().add("sidebar");

        this.dashboardButton = new Button("Dashboard");
        this.booksButton = new Button("Books");
        this.membersButton = new Button("Members");
        this.borrowingButton = new Button("Borrowing");

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
    }

    public Button getDashboardButton(){
        return this.dashboardButton;
    }

    public Button getBooksButton(){
        return this.booksButton;
    }

    public Button getMembersButton(){
        return this.membersButton;
    }
   
    public Button getBorrowingButton(){
        return this.borrowingButton;
    }
}
