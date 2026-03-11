package levi.progettone.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import levi.progettone.Main;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button start;
    @FXML
    private Button exit;
    @FXML
    private Label author;
    @FXML
    private ImageView gameImage;
    @FXML
    private VBox menuScreen;

    Font font = Font.loadFont(getClass().getResourceAsStream("/levi/progettone/font/flappy-font.ttf"), 13);

    public void initialize(){
        impostaFont();
        impostaBanner();
    }

    @FXML
    public void goStart() throws IOException {
        Main.setRoot("views/game");
    }

    @FXML
    public void esci(ActionEvent event){
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    private void impostaBanner(){
        gameImage.setImage(new Image(getClass().getResource("/levi/progettone/imgs/others/message.png").toExternalForm()));
        Image image = new Image(getClass().getResource("/levi/progettone/imgs/others/background-day.png").toExternalForm());
        BackgroundSize size = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage bImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        menuScreen.setBackground(new Background(bImage));
    }

    @FXML
    private void impostaFont(){
        author.setText("by nic :P");
        author.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 16;");
        exit.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 30;");
        start.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 30;");
    }
}