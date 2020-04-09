package de.jo0001.gloomhaven.battleGoalCards.network;

import de.jo0001.gloomhaven.battleGoalCards.core.Controller;
import de.jo0001.gloomhaven.battleGoalCards.other.Card;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Client extends Thread {
    public Card[] cards = new Card[24];
    private boolean filled = false;
    private Socket client;

    private String host;
    private int port;
    private Controller controller;

    public Client(String host, int port, Controller controller) {
        this.host = host;
        this.port = port;
        this.controller = controller;
    }

    public void run() {
        System.out.println(1);
        startClient();
    }

    private void fill() {
        if (!filled) {
            System.out.println("Filled Cards");
            cards[0] = new Card("Aalglatt", "Habe am Ende des Abenteuers insgesamt 5 oder mehr Karten in Deiner Hand und in Deinem Abwurfstapel.", 1, 458);
            cards[1] = new Card("Faulenzer", "Erhalte 7 oder weniger Erfahrungspunkte während des Abenteuers.", 2, 459);
            cards[2] = new Card("Arbeitstier", "Erhalte 13 oder mehr Erfahrungspunkte während des Abenteuers.", 1, 460);
            cards[3] = new Card("Fanatiker", "Habe am Ende des Abenteuers insgesamt 3 oder weniger Karten in Deiner Hand und in Deinem Abwurfstapel.", 1, 461);
            cards[4] = new Card("Masochist", "Habe am Ende des Abenteuers 2 oder weniger Lebenspunkte.", 1, 462);
            cards[5] = new Card("Gute Heilungskräfte", "Am Ende des Abenteuers musst Du (wieder) Deine maximale Anzahl an Lebenspunkten haben.", 1, 463);
            cards[6] = new Card("Neutralisator", "Bringe während einer Deiner Züge oder während der Züge einesvon Dir beschworenen Verbündeten eine Falle zum Auslösen oder entschärfe sie.", 1, 464);
            cards[7] = new Card("Plünderer", "Plündere während des Abenteuers eine Schatzkiste", 1, 465);
            cards[8] = new Card("Beschützer", "Erlaube keinem Deiner Gefährten (Abenteurer),sich während des Abenteuers zu erschöpfen.", 1, 466);
            cards[9] = new Card("Entdecker", "Entdecke während des Abenteuers in einem Deiner Züge einen Raum, indem Du eine Tür öffnest.", 1, 467);
            cards[10] = new Card("Sammler", "Sammle während des Abenteuers 5 oder mehr Geld- Marker ein.", 1, 468);
            cards[11] = new Card("Mittellos", "Sammle während des Abenteuers weder Geld- Marker ein noch plündere Schatzkisten.", 2, 469);
            cards[12] = new Card("Pazifist", "Töte 3 oder weniger Monster während des Abenteuers.", 1, 470);
            cards[13] = new Card("Sadist", "Töte 5 oder mehr Monster während des Abenteuers.", 1, 471);
            cards[14] = new Card("Jäger", "Töte ein oder mehr Elite-Monster während des Abenteuers.", 1, 472);
            cards[15] = new Card("Profi", "Verwende während des Abenteuers Deine mitgenommenen Gegenstände mindestens (Deine Stufe + 2)-mal.", 1, 473);
            cards[16] = new Card("Aggressor", "Sorge dafür, dass während des Abenteuers zu Beginn jeder Runde mindestens ein Monsterauf dem Plan ist.", 2, 474);
            cards[17] = new Card("Energisch", "Töte während des Abenteuers ein Monstermit mindestens 4 Schadenspunkten mehr, als eigentlich nötig wären.", 1, 475);
            cards[18] = new Card("Purist", "Verwende während des Abenteuers keine Gegenstände.", 2, 476);
            cards[19] = new Card("Erster", "Sei während des Abenteuers der erste, der ein Monster tötet.", 1, 477);
            cards[20] = new Card("Zäher Kämpfer", "Lasse es während des Abenteuers nicht zu, dass Deine Lebenspunkte unter die Hälfte Deiner Maximalpunktzahl (aufgerundet) fallen.", 1, 478);
            cards[21] = new Card("Vollstrecker", "Töte während des Abenteuers ein unverletztes Monster mit einem einzelnen Angriff.", 1, 479);
            cards[22] = new Card("Nachzügler", "Mache während des Abenteuers nur lange Rasten.", 1, 480);
            cards[23] = new Card("Zerstückler", "Mache während des Abenteuers nur kurze Rasten.", 1, 481);
            filled = true;
        }
    }

    public void startClient() {
        fill();
        try {
            client = new Socket(host, port);
            System.out.println("Client running");
            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            String[] tmpIn = inputStream.readUTF().split(",");
            int id1 = Integer.parseInt(tmpIn[0]), id2 = Integer.parseInt(tmpIn[1]);

            for (Card card : cards) {
                if (card.getId() == id1) {
                    Platform.runLater(() -> {
                        controller.title1.setText(card.getTitle());
                        controller.desc1.setText(card.getDescription());
                        controller.checks1.setText((card.getChecks() == 2) ? "✔✔" : "✔");
                        controller.id1.setText(Integer.toString(card.getId()));
                    });
                }
                if (card.getId() == id2) {
                    Platform.runLater(() -> {
                        controller.title2.setText(card.getTitle());
                        controller.desc2.setText(card.getDescription());
                        controller.checks2.setText((card.getChecks() == 2) ? "✔✔" : "✔");
                        controller.id2.setText(Integer.toString(card.getId()));
                    });
                }
            }
            controller.btn.setDisable(true);
            controller.show();
        } catch (IOException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Verbindungsfehler");
                alert.setHeaderText(null);
                alert.setContentText("Es konnte keine Verbindung zu " + host + "unter Port " + port + " herstellen. Bitte versuche es erneut.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Client.class.getResourceAsStream("/logo.png")));
                alert.showAndWait();
            });
            controller.btn.setText("Connect to server");
            controller.connected = false;
            e.printStackTrace();
        }
    }

    public void getInfo() {
        try {
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            System.out.println("OutputStream ready");
            output.writeUTF("getnumber");
            System.out.println("Send number request done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
