package levi.progettone.controller;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import levi.progettone.model.Player;
import javafx.scene.paint.Color;

public class GameController {

    @FXML
    private Button back;
    @FXML
    private VBox gameScreen;
    @FXML
    private Pane level;

    double v = 100;

    double baseX = (v/2);
    double baseY = (v/2);

    Player p = new Player(baseX, baseY);
    Rectangle player = new Rectangle(v, v); // w -> h
    Rectangle obstacle = new Rectangle(v*2, 0, v*2, v*2); // sinistra -> alto | w -> h
    Rectangle invisible = new Rectangle(v, v);

    public void initialize(){
        level.setPrefWidth(2000);
        level.setPrefHeight(1500);
        level.getChildren().clear();
        start();
        //test();
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
            case W, KP_UP:
                p.move(1);
                break;
            case S, KP_DOWN:
                p.move(2);
                break;
            case A, KP_LEFT:
                p.move(3);
                break;
            case D, KP_RIGHT:
                p.move(4);
                break;
            default:
                break;
        }

        invisible.setY(p.getY());
        invisible.setX(p.getX());
        if(!collisionCheck(invisible)){
            movement(player);
            System.out.println("test");
        }

    }

    @FXML
    public void start(){
        player.setX(0); // da sinistra
        player.setY(0); // dall'alto
        player.setFill(Color.PURPLE);

        invisible.setX(player.getX());
        invisible.setY(player.getY());
        invisible.setFill(Color.TRANSPARENT);

        obstacle.setFill(Color.BLACK);

        level.getChildren().addAll(player, obstacle);
    }

    public boolean collisionCheck(Rectangle r){
        return r.getBoundsInParent().intersects(obstacle.getBoundsInParent());
    }

    public void movement(Rectangle r){
        Path path = new Path();
        path.getElements().add(new MoveTo(baseX, baseY)); // starting point
        path.getElements().add(new LineTo(p.getX(), p.getY()));

        baseX = p.getX();
        baseY = p.getY();
        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.millis(70));
        transition.setNode(r);
        transition.setPath(path);
        transition.setInterpolator(Interpolator.LINEAR);

        transition.play();

//        r.setY(p.getY());
//        r.setX(p.getX());
    }
}
