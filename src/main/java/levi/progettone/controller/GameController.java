package levi.progettone.controller;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import levi.progettone.Main;
import levi.progettone.model.*;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
    @FXML
    private HBox pointCounter;
    @FXML
    private Label diff;

    Font font = Font.loadFont(getClass().getResourceAsStream("/levi/progettone/font/flappy-font.ttf"), 13);

    Sprite s = GameData.character;

    //gameplay
    int punti;
    AnimationTimer loop;
    double velocitaY;
    double gravita;
    double potenzaSalto;
    double obsSpeed;
    Difficolta d;
    boolean puntiAggiornati = false;

    Random rand = new Random();
    int time = 0;
    ArrayList<ImageView> obs = new ArrayList<>();

    Rectangle sup = null;

    public void initialize(){

        // posiziono il counter dei punti
        pointCounter.layoutXProperty().bind(level.widthProperty().subtract(pointCounter.widthProperty()).divide(2));
        pointCounter.setLayoutY(10);
        pointCounter.toFront();

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

    private void stats(){
        velocitaY = GameData.velocitaY;
        gravita = GameData.gravita;
        potenzaSalto = GameData.potenzaSalto;
        obsSpeed = GameData.obsSpeed;
        d = GameData.diff;
    }

    @FXML
    public void goBack() throws IOException {
        loop.stop();
        Main.setRoot("views/menu");
    }

    @FXML
    public void input(MouseEvent event){
        if(event.getButton().equals(MouseButton.PRIMARY)){
            jump();
        }
    }

    @FXML
    public void tastiera(KeyEvent event){
        if(event.getCode().equals(KeyCode.SPACE)){
            jump();
        }
    }

    private void jump(){
        // minore --> salto più potente
        if(d.equals(Difficolta.REVERSE)){
            velocitaY = -(potenzaSalto);
        }else{
            velocitaY = potenzaSalto;
        }
    }

    public boolean collisionCheck(){
        Bounds b = (s instanceof NyanCat)? sup.getBoundsInParent() : player.getBoundsInParent();

        for(ImageView o : obs){
            if(b.intersects(o.getBoundsInParent())){
                return true;
            }
        }
        return false;
    }

    public void moveY(double v){
        player.setY(player.getY() + v);

        if(s instanceof NyanCat && sup != null){
            sup.setY(player.getY() + (player.getFitHeight() + 135));
            sup.setX(player.getLayoutX() + player.getX() - s.getOffset().getX() - sup.getWidth()/2);
        }
    }

    public void reset(){
        level.getChildren().removeAll(obs);
        obs.clear();

        player.setY(0);
        time = 0;
        velocitaY = 0;
        punti = 0;
        aggiornaPunti();
    }

    private void checkScore() {
        boolean top = true;

        for (ImageView pipe : obs) {
            if(top){
                top = false;
                if (pipe.getUserData() == null && pipe.getX() + pipe.getFitWidth() < player.getLayoutX()) {
                    punti++;
                    System.out.println(punti);
                    pipe.setUserData("passed");
                    aggiornaPunti();
                    puntiAggiornati = true;
                    break;
                }else{
                    puntiAggiornati = false;
                }
            }

        }
    }

    public void update(){
        time++;
        velocitaY += gravita;
        moveY(velocitaY);

        // Muovi gli ostacoli ad ogni frame
        moveObstacles(obs);

        // Genera nuovi ostacoli ogni 200 frame
        if(time % 400 == 0){
            initObsacles();
        }

        // Animazione sprite in base alla velocità verticale
        if(velocitaY < -1){
            ruota(1);
        } else if(velocitaY > 1){
            ruota(2);
        } else {
            ruota(3);
        }

        if(s instanceof Pipe){
            ruotaTubo();
        }

        if(s instanceof Bird || s instanceof Pipe){
            s.setSprite(velocitaY);
            player.setImage(s.getCurrentSprite());
        }

        // Collisione con il bordo inferiore
        double bottomBound = level.getHeight();
        if(bottomBound > 0 && player.getLayoutY() + player.getY() >= bottomBound){
            try {
                gameOver();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Collisione con il bordo superiore
        if(player.getLayoutY() + player.getY() < 0){
            player.setY(-player.getLayoutY());
            velocitaY = 0;
        }

        // Collisione con ostacolo
        if(collisionCheck()){
            try {
                gameOver();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // punti
        checkScore();
    }

    @FXML
    private void gameOver() throws IOException {
        reset();
        loop.stop();
        Main.setRoot("views/game-over");
    }

    private void ruota(int index){

        if(s instanceof Pipe || s instanceof NyanCat){
            return;
        }

        RotateTransition transition = new RotateTransition(Duration.seconds(0.5), player);

        switch(index){
            case 1: //sale
                transition.setFromAngle(player.getRotate());
                transition.setToAngle(-45);
                break;
            case 2: //scende
                transition.setFromAngle(player.getRotate());
                transition.setToAngle(45);
                break;
            case 3: //fermo
                transition.setFromAngle(player.getRotate());
                transition.setToAngle(0);
                break;
        }

        transition.setInterpolator(Interpolator.LINEAR);

        transition.play();
    }

    private void ruotaTubo(){
        RotateTransition transition = new RotateTransition(Duration.seconds(0.5), player);
        transition.setFromAngle(player.getRotate());
        transition.setToAngle(player.getRotate() + 360);
        transition.setInterpolator(Interpolator.LINEAR);

        //dopo 15 ottilioni di anni il programma crasherà per overflow (1,5 x 10^28)

        transition.play();
    }

    public void init(){
        stats();
        //inizializza il background, il font e i punti
        setBG();
        back.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        diff.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 25; -fx-alignment: center; -fx-background-color: white;");
        punti = 0;
        diff.setText(diff.getText() + d);

        player.setFitWidth(s.getWidth());
        player.setFitHeight(s.getHeight());

        System.out.println(player.getX());
        System.out.println(player.getY());

        if(s instanceof NyanCat){
            player.setImage(s.getSpriteList().getFirst());
            player.setX(s.getOffset().getX());
            player.setY(s.getOffset().getY());

            sup = new Rectangle(player.getX(), player.getY(), 70, 50);

//            System.out.println(sup.getX() + " " + sup.getY());
//            System.out.println(player.getX() + " " + sup.getY());

            sup.setFill(Color.TRANSPARENT);

            level.getChildren().add(sup);
        }
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

    public void setBG(){
        Image image = s.getSfondo();
        BackgroundSize size = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage bImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        gameScreen.setBackground(new Background(bImage));

        image = new Image(getClass().getResource("/levi/progettone/imgs/others/base.png").toExternalForm());
        size = new BackgroundSize(100, 100, true, true, true, false);
        bImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, size);
        piattaforma.setBackground(new Background(bImage));
    }

    private void initObsacles(){
        int w = 52;
        double x = level.getWidth();// - 50
        double spaceBtw = 200;
        double topHeight = rand.nextInt((int)(level.getHeight() - spaceBtw - 100) + 50);
        double botHeight = level.getHeight() - spaceBtw - topHeight;

        ImageView topPipe = new ImageView(new Image(getClass().getResource("/levi/progettone/imgs/sprites/pipe/pipe-green-flip.png").toExternalForm()));
        topPipe.setX(x);
        topPipe.setFitWidth(w);
        topPipe.setY(0);
        topPipe.setFitHeight(topHeight);

        ImageView botPipe = new ImageView(new Image(getClass().getResource("/levi/progettone/imgs/sprites/pipe/pipe-green.png").toExternalForm()));
        botPipe.setX(x);
        botPipe.setFitWidth(w);
        botPipe.setY(topHeight + spaceBtw);
        botPipe.setFitHeight(botHeight);

        obs.add(topPipe);
        obs.add(botPipe);
        level.getChildren().addAll(topPipe, botPipe);
    }

    private void moveObstacles(ArrayList<ImageView> obstacles){

        ArrayList<ImageView> outOfScreen = new ArrayList<>();

        for(ImageView o : obstacles){
            moveObs(o, obsSpeed);
            if(o.getX() <= -o.getFitWidth()){
                outOfScreen.add(o);
            }
        }
        obstacles.removeAll(outOfScreen);
        level.getChildren().removeAll(outOfScreen);

        pointCounter.toFront();
    }

    private void moveObs(ImageView o, double v){
        o.setX(o.getX() + v);
        pointCounter.toFront();
    }


}
