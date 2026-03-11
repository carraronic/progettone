package levi.progettone.controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import levi.progettone.Main;

import java.io.IOException;

public class GameController {

    @FXML
    private Button back;
    @FXML
    private VBox gameScreen;
    @FXML
    private Pane level;
    @FXML
    private ImageView p1;
    @FXML
    private ImageView p2;
    @FXML
    private ImageView p3;
    @FXML
    private ImageView player;
    @FXML
    private HBox piattaforma;

    //immagini
    Image up = new Image(getClass().getResource("/levi/progettone/imgs/sprites/yellowbird-upflap.png").toExternalForm());
    Image down = new Image(getClass().getResource("/levi/progettone/imgs/sprites/yellowbird-downflap.png").toExternalForm());
    Image mid = new Image(getClass().getResource("/levi/progettone/imgs/sprites/yellowbird-midflap.png").toExternalForm());

    //font
    Font font = Font.loadFont(getClass().getResourceAsStream("/levi/progettone/font/flappy-font.ttf"), 13);

    //gameplay
    int punti;
    AnimationTimer loop;
    double velocitaY = 0;
    double gravita = 0.2; // minore --> lento

    public void initialize(){
        reset();
        loop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        loop.start();

        init();
        aggiornaPunti();
    }

    @FXML
    public void goBack() throws IOException {
        Main.setRoot("views/menu");
    }

    @FXML
    public void input(MouseEvent event){
        if(event.getButton().equals(MouseButton.PRIMARY)){
            jump();
            punti++;
            aggiornaPunti();
        }
    }

    private void jump(){
        velocitaY = -6;  // minore --> salto più potente
    }

    public boolean collisionCheck(){
        return player.getLayoutY() + player.getY() >= level.getHeight();
    }

    public void moveY(double v){
        player.setY(player.getY() + v);
    }

    public void reset(){
        player.setY(0);
        velocitaY = 0;
        punti = 0;
        aggiornaPunti();
    }

    public void update(){
        velocitaY += gravita;
        moveY(velocitaY);

        //cambio sfondo
        switch(punti){
            case 50, 150, 250, 350, 450, 550, 650, 750, 850, 950:
                setBG(false);
                break;
            case 100, 200, 300, 400, 500, 600, 700, 800, 900:
                setBG(true);
                break;
        }

        //controllo per l'immagine
        if(velocitaY < -1){
            player.setImage(up);
        }else if(velocitaY > 1){
            player.setImage(down);
        }else{
            player.setImage(mid);
        }

        // collisione con il bordo inferiore
        if(collisionCheck()){
            reset();
        }

        // collisione con il bordo superiore
        if(player.getLayoutY() + player.getY() < 0){
            player.setY(-player.getLayoutY());
            velocitaY = 0;
        }
    }

    public void init(){
        //inizializza il background, il font e i punti
        setBG(true);
        back.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        punti = 0;
    }

    public void aggiornaPunti(){
        //aggiorna il display dei punti
        if(punti < 10){
            p1.setImage(new Image(getClass().getResource("/levi/progettone/imgs/numbers/" + punti + ".png").toExternalForm()));
            p2.setImage(new Image(getClass().getResource("/levi/progettone/imgs/numbers/0.png").toExternalForm()));
            p3.setImage(new Image(getClass().getResource("/levi/progettone/imgs/numbers/0.png").toExternalForm()));
        }else if (punti < 100){
            p1.setImage(new Image(getClass().getResource("/levi/progettone/imgs/numbers/" + (punti % 10) + ".png").toExternalForm()));
            p2.setImage(new Image(getClass().getResource("/levi/progettone/imgs/numbers/" + punti/10 + ".png").toExternalForm()));
            p3.setImage(new Image(getClass().getResource("/levi/progettone/imgs/numbers/0.png").toExternalForm()));
        }else{
            p1.setImage(new Image(getClass().getResource("/levi/progettone/imgs/numbers/" + (punti % 10) + ".png").toExternalForm()));
            p2.setImage(new Image(getClass().getResource("/levi/progettone/imgs/numbers/" + ((punti/10) % 10) + ".png").toExternalForm()));
            p3.setImage(new Image(getClass().getResource("/levi/progettone/imgs/numbers/" + (punti/100) + ".png").toExternalForm()));
        }
    }

    public void setBG(boolean dn){
        Image image;
        if(dn){
            image = new Image(getClass().getResource("/levi/progettone/imgs/others/background-day.png").toExternalForm());
        }else{
            image = new Image(getClass().getResource("/levi/progettone/imgs/others/background-night.png").toExternalForm());
        }
        BackgroundSize size = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage bImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        gameScreen.setBackground(new Background(bImage));

        image = new Image(getClass().getResource("/levi/progettone/imgs/others/base.png").toExternalForm());
        size = new BackgroundSize(100, 100, true, true, true, false);
        bImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, size);
        piattaforma.setBackground(new Background(bImage));
    }




}
