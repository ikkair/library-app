package dev.ikkair.library_app;

import dev.ikkair.library_app.view.Dashboard;
import dev.ikkair.library_app.view.SidePanel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        SidePanel sidePanel = new SidePanel(root);

        root.setLeft(sidePanel);
        root.setCenter(new Dashboard());

        Scene scene = new Scene(root, 1000, 700);

        scene.getStylesheets().add(
            getClass().getResource("/style.css").toExternalForm()
        );

        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
