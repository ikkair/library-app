package dev.ikkair.library_app.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SidePanel extends VBox {

    private final Button booksButton;
    private final Button membersButton;
    private final Button borrowingButton;

    public SidePanel() {
        getStyleClass().add("sidebar");
        this.booksButton = new Button("Buku");
        this.membersButton = new Button("Anggota");
        this.borrowingButton = new Button("Peminjaman");
        booksButton.getStyleClass().add("menu-button");
        membersButton.getStyleClass().add("menu-button");
        borrowingButton.getStyleClass().add("menu-button");
        getChildren().addAll(
                booksButton,
                membersButton,
                borrowingButton);
    }

    public Button getBooksButton() {
        return this.booksButton;
    }

    public Button getMembersButton() {
        return this.membersButton;
    }

    public Button getBorrowingButton() {
        return this.borrowingButton;
    }
}
