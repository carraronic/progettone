package levi.progettone.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import levi.progettone.Main;
import levi.progettone.model.Difficolta;
import levi.progettone.model.GameData;

import java.io.IOException;

public class GameoverController {

    @FXML
    public Label displayPunti;
    @FXML
    public TextField name;
    @FXML
    public Button save;
    @FXML
    private ImageView image;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private VBox screen;
    @FXML
    private Label warning;

    Font font = Font.loadFont(getClass().getResourceAsStream("/levi/progettone/font/flappy-font.ttf"), 13);

    public void initialize(){
        Image i = new Image(getClass().getResource("/levi/progettone/imgs/others/gameover.png").toExternalForm());
        image.setImage(i);

        btn1.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 25;");
        btn2.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 25;");
        save.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        name.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        warning.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 15; -fx-text-fill: white;");
        displayPunti.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 25; -fx-text-fill: white;");

        displayPunti.setText("IL TUO PUNTEGGIO: " + GameData.player.getPunteggio());

        Image image = new Image(getClass().getResource("/levi/progettone/imgs/others/background-night.png").toExternalForm());
        BackgroundSize size = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage bImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        screen.setBackground(new Background(bImage));

        if(GameData.diff.equals(Difficolta.CUSTOM)){
            save.setDisable(true);
            warning.setText("La difficoltà CUSTOM non si può salvare in classifica");
        }
    }


    @FXML
    public void restart() throws IOException {
        Main.setRoot("views/game");
    }

    public void mainMenu() throws IOException {
        Main.setRoot("views/menu");
    }

    public void salva() throws IOException {
        if(!name.getText().isEmpty()){
            GameData.player.setNome(name.getText());
            RecordController.savePlayers(GameData.player);
            Main.setRoot("views/leaderboards");
        }else{
            warning.setText("Inserire un nome valido");
        }
    }
}
