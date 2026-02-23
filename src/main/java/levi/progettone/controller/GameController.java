package levi.progettone.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameController {

    @FXML
    private Button back;
    @FXML
    private VBox gameScreen;
    @FXML
    private Canvas canva;

    public void initialize(){
        impostaSfondo();
    }

    @FXML
    public void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/levi/progettone/views/menu.fxml"));

            Scene scene = new Scene(loader.load(),320, 240);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setTitle("menu");
            window.setScene(scene);
            window.setFullScreen(true);
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void impostaSfondo() {
        Image img = new Image(getClass().getResource("/levi/progettone/imgs/bg.jpg").toExternalForm());
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true);
        BackgroundImage bgImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);

        gameScreen.setBackground(new Background(bgImage));
    }
}
