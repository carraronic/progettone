package levi.progettone.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import levi.progettone.model.Player;
import javafx.scene.paint.Color;

public class GameController {

    @FXML
    private Button back;
    @FXML
    private VBox gameScreen;
    @FXML
    private Pane level;

    Player p = new Player(50, 50);

    public void initialize(){
        level.getChildren().clear();
        start();
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

    public void input(KeyEvent event){
        KeyCode c = event.getCode();

        switch(c){
            case W, UP:
                p.move(1);
                break;
            case S, DOWN:
                p.move(2);
                break;
            case A, LEFT:
                p.move(3);
                break;
            case D, RIGHT:
                p.move(4);
                break;
            default:
                break;
        }
    }

    @FXML
    public void start(){

        Rectangle player = new Rectangle(30, 60); // w -> h
        player.setX(60); // da sinistra
        player.setY(930); // dall'alto
        player.setFill(Color.PURPLE);

        Rectangle obstacle = new Rectangle(200, 0, 200, 200); // sinistra -> alto | w -> h
        obstacle.setFill(Color.BLACK);

        level.getChildren().addAll(player, obstacle);


        if (player.getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
            p.setMovement(false);
            System.out.println("Collisione rilevata!");
        }else{
            p.setMovement(true);
        }
    }
}
