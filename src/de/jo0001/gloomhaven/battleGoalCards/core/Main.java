package de.jo0001.gloomhaven.battleGoalCards.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;

import java.util.Date;

public class Main extends Application {
    static final FXMLLoader loader = new FXMLLoader();
    private static boolean ready = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = loader.getController();

        loader.setController(controller);
        Parent root = loader.load(getClass().getResource("/mainUI.fxml"));
        primaryStage.setTitle("Gloomhaven Battle Goal Cards");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        Scene s = new Scene(root, 500, 500);
        s.setCursor(Cursor.HAND);
        primaryStage.setScene(s);
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/design.css").toExternalForm());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing Discord hook.");
            DiscordRPC.discordShutdown();
        }));

        initDiscord();
        primaryStage.show();
        System.out.println("Running callbacks...");
        do {
            DiscordRPC.discordRunCallbacks();
        } while (!ready);
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    private static void initDiscord() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            Main.ready = true;
            System.out.println("Welcome " + user.username + "#" + user.discriminator + ".");
            net.arikia.dev.drpc.DiscordRichPresence.Builder presence = new net.arikia.dev.drpc.DiscordRichPresence.Builder("");
            Date date = new Date();
            long timeMilli = date.getTime();
            presence.setStartTimestamps(timeMilli);
            presence.setBigImage("logo", "");
            DiscordRPC.discordUpdatePresence(presence.build());
        }).build();
        DiscordRPC.discordInitialize("710170554543374377", handlers, false);
    }
}