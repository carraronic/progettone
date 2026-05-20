package levi.progettone.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import levi.progettone.Main;
import levi.progettone.model.Difficolta;
import levi.progettone.model.Player;
import levi.progettone.model.Sprite;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class RecordController {

    @FXML
    public VBox lista;
    @FXML
    public Button mm;
    @FXML
    public Label label1;
    @FXML
    public VBox screen;

    static ArrayList<Player> players = new ArrayList<>();
    static String path = "record.txt";
    Font font = Font.loadFont(getClass().getResourceAsStream("/levi/progettone/font/flappy-font.ttf"), 13);

    public void initialize(){

        label1.setText("CLASSIFICA\nPUNTEGGI");
        label1.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 40;");
        mm.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");

        try{
            readPlayers();
        }catch(IOException ignored){}

        caricaPlayer();

        Image image = new Image(getClass().getResource("/levi/progettone/imgs/others/background-day.png").toExternalForm());
        BackgroundSize size = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage bImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        screen.setBackground(new Background(bImage));
    }

    private void readPlayers() throws IOException {

        players.clear();
        BufferedReader bf = new BufferedReader(new FileReader(path));
        String r = null;
        String[] p;

        do{
            r = bf.readLine();

            if(r != null){
                p = r.split(";");
                Sprite s = Sprite.getInstance(p[0]);
                String nome = p[1];
                int punti = Integer.parseInt(p[2]);
                Difficolta diff = Difficolta.getDiff(p[3]);

                Player pl = new Player(s, nome, punti, diff);
                System.out.println(pl);

                players.add(pl);
            }

        }while(r != null);

        bf.close();
    }

    public static void savePlayers(Player p) throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter(path, true));

        String s = p.getSprite().getId() + ";" + p.getNome() + ";" + p.getPunteggio() + ";" + p.getDiff();
        pw.println(s);

        pw.close();
    }

    private void caricaPlayer(){

        lista.getChildren().clear();

        Collections.sort(players);

        for(Player p : players){

            HBox h = new HBox();
            Label spazio = new Label("   ");
            Label nome = new Label(p.getNome() + " (" + p.getDiff() + ")");
            Label punti = new Label(String.valueOf(p.getPunteggio()));
            Region r = new Region();

            String fontStyle = "-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 25;";
            nome.setStyle(fontStyle);
            punti.setStyle(fontStyle);

            h.setPrefSize(400, 100);
            HBox.setHgrow(r, Priority.ALWAYS);

            ImageView i = new ImageView(p.getSelectedSprite());
            i.setFitWidth(80);
            i.setFitHeight(50);

            h.getChildren().addAll(i, spazio, nome, r, punti);

            lista.getChildren().add(h);
        }

    }

    public void mainMenu() throws IOException {
        Main.setRoot("views/menu");
    }
}
