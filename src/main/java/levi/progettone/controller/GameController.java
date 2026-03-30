package levi.progettone.controller;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import levi.progettone.Main;
import levi.progettone.model.Difficolta;
import levi.progettone.model.Gamedata;

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

    //immagini
    Image up = new Image(getClass().getResource("/levi/progettone/imgs/sprites/yellowbird-upflap.png").toExternalForm());
    Image down = new Image(getClass().getResource("/levi/progettone/imgs/sprites/yellowbird-downflap.png").toExternalForm());
    Image mid = new Image(getClass().getResource("/levi/progettone/imgs/sprites/yellowbird-midflap.png").toExternalForm());

    //font
    Font font = Font.loadFont(getClass().getResourceAsStream("/levi/progettone/font/flappy-font.ttf"), 13);

    //gameplay
    int punti;
    AnimationTimer loop;
    double velocitaY;
    double gravita;
    double potenzaSalto;
    double obsSpeed;
    Difficolta d;

    Random rand = new Random();
    int time = 0;
    ArrayList<ImageView> obs = new ArrayList<>();



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
        velocitaY = Gamedata.velocitaY;
        gravita = Gamedata.gravita;
        potenzaSalto = Gamedata.potenzaSalto;
        obsSpeed = Gamedata.obsSpeed;
        d = Gamedata.diff;
    }

    @FXML
    public void goBack() throws IOException {
        Main.setRoot("views/menu");
    }

    @FXML
    public void input(MouseEvent event){
        if(event.getButton().equals(MouseButton.PRIMARY)){
            jump();
        }
    }

    private void jump(){
        velocitaY = potenzaSalto;  // minore --> salto più potente
    }

    public boolean collisionCheck(){
        for(ImageView o : obs){
            if(player.getBoundsInParent().intersects(o.getBoundsInParent())){
                return true;
            }
        }
        return false;
    }

    public void moveY(double v){
        player.setY(player.getY() + v);
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
                    break;
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

        // Cambio sfondo
        switch(punti){
            case 50, 150, 250, 350, 450, 550, 650, 750, 850, 950:
                setBG(false);
                break;
            case 100, 200, 300, 400, 500, 600, 700, 800, 900:
                setBG(true);
                break;
        }

        // Animazione sprite in base alla velocità verticale
        if(velocitaY < -1){
            player.setImage(up);
            ruota(1);
        } else if(velocitaY > 1){
            player.setImage(down);
            ruota(2);
        } else {
            player.setImage(mid);
            ruota(3);
        }

        // Collisione con il bordo inferiore
        double bottomBound = level.getHeight();
        if(bottomBound > 0 && player.getLayoutY() + player.getY() >= bottomBound){
            reset();
            return;
        }

        // Collisione con il bordo superiore
        if(player.getLayoutY() + player.getY() < 0){
            player.setY(-player.getLayoutY());
            velocitaY = 0;
        }

        // Collisione con ostacolo
        if(collisionCheck()){
            reset();
            return;
        }

        // punti
        checkScore();
    }

    private void ruota(int index){
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

    public void init(){
        stats();
        //inizializza il background, il font e i punti
        setBG(true);
        back.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        diff.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 25; -fx-alignment: center; -fx-background-color: white;");
        punti = 0;
        diff.setText(diff.getText() + d);
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

    private void initObsacles(){
        int w = 52;
        double x = level.getWidth() - 50;
        double spaceBtw = 200;
        double topHeight = rand.nextInt((int)(level.getHeight() - spaceBtw - 100) + 50);
        double botHeight = level.getHeight() - spaceBtw - topHeight;

        ImageView topPipe = new ImageView(new Image(getClass().getResource("/levi/progettone/imgs/sprites/pipe-green-flip.png").toExternalForm()));
        topPipe.setX(x);
        topPipe.setFitWidth(w);
        topPipe.setY(0);
        topPipe.setFitHeight(topHeight);

        ImageView botPipe = new ImageView(new Image(getClass().getResource("/levi/progettone/imgs/sprites/pipe-green.png").toExternalForm()));
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
