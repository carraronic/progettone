package levi.progettone.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import levi.progettone.Main;

import java.io.IOException;

public class GameoverController {

    @FXML
    private ImageView image;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private VBox screen;

    Font font = Font.loadFont(getClass().getResourceAsStream("/levi/progettone/font/flappy-font.ttf"), 13);

    public void initialize(){
        Image i = new Image(getClass().getResource("/levi/progettone/imgs/others/gameover.png").toExternalForm());
        image.setImage(i);

        btn1.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 25;");
        btn2.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 25;");

        Image image = new Image(getClass().getResource("/levi/progettone/imgs/others/background-night.png").toExternalForm());
        BackgroundSize size = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage bImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        screen.setBackground(new Background(bImage));
    }


    @FXML
    public void restart() throws IOException {
        Main.setRoot("views/game");
    }

    public void mainMenu() throws IOException {
        Main.setRoot("views/menu");
    }
}
