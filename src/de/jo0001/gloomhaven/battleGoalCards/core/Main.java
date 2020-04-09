package de.jo0001.gloomhaven.battleGoalCards.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    static final FXMLLoader loader = new FXMLLoader();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = loader.getController();
        loader.setController(controller);
        Parent root = loader.load(getClass().getResource("/mainUI.fxml"));
        primaryStage.setTitle("Gloomhaven Battle Goal Cards  v1.2Dev");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        Scene s = new Scene(root, 500, 500);
        s.setCursor(Cursor.HAND);
        primaryStage.setScene(s);
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}