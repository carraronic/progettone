package levi.progettone.controller;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import levi.progettone.model.Player;
import javafx.scene.paint.Color;
import org.w3c.dom.css.Rect;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;

public class GameController {

    @FXML
    private Button back;
    @FXML
    private VBox gameScreen;
    @FXML
    private Pane level;
    @FXML
    private Label coinCount;

    double v = 100;

    double baseX = (v/2);
    double baseY = (v/2);

    int pos;
    boolean controllo = true;
    int monete;

    ArrayList<Rectangle> obsList = new ArrayList<>();
    ArrayList<Rectangle> coinList = new ArrayList<>();

    Player p = new Player(baseX, baseY);
    Rectangle player = new Rectangle(v, v); // w -> h
    Rectangle obstacle = new Rectangle(v*pos, 0, v*2, v*2); // sinistra -> alto | w -> h
    Rectangle invisible = new Rectangle(v, v);



    public void initialize(){
        level.setPrefWidth(2000);
        level.setPrefHeight(1500);
        level.getChildren().clear();
        coinCount.setText("Monete: " + monete);
        start();
        System.out.println(pos);
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

        invisible.setY(p.getY() - v/2);
        invisible.setX(p.getX() - v/2);
        if(!collisionCheck(invisible)){
            movement(player);
            collisionCheck(player);
        }

    }

    @FXML
    public void start(){
        player.setWidth(v);
        player.setHeight(v);
        player.setX(0);
        player.setY(0);
        player.setFill(Color.PURPLE);

        invisible.setWidth(v);
        invisible.setHeight(v);
        invisible.setX(0);
        invisible.setY(0);
        invisible.setFill(Color.TRANSPARENT);

        for (int i = 0; i < 7; i++) {
            pos = (int) (Math.random() * 10);
            Rectangle obstacle = new Rectangle(v*pos, (pos>5? 0 : 800), v*2, v*2);
            obstacle.setFill(Color.BLACK);
            obsList.add(obstacle);
            level.getChildren().add(obstacle);
        }

        pos = 0;
        for (int i = 0; i < 10; i++) {
            Rectangle coin = new Rectangle(pos>5? 330 : 500, 400, v/2, v/2);
            coin.setFill(Color.YELLOW);
            coinList.add(coin);
            level.getChildren().add(coin);
        }

        level.getChildren().addAll(player);
    }

    public boolean collisionCheck(Rectangle r){
        for(Node n : level.getChildren()){
            if(r.getBoundsInParent().intersects(n.getBoundsInParent())){
                if(coinList.contains(n)){
                    monete++;
                    n = new Rectangle(pos>5? 200 : 470, 400, v/2, v/2);
                    coinCount.setText("Monete: " + monete);
                    return true;
                }
                if(obsList.contains(n)){
                    return true;
                }
            }
        }
        return false;
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
