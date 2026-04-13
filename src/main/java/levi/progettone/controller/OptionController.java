package levi.progettone.controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import levi.progettone.Main;
import levi.progettone.model.Difficolta;
import levi.progettone.model.GameData;
import levi.progettone.model.Sprite;

import java.io.IOException;
import java.util.ArrayList;


public class OptionController {

    @FXML
    private Button applica;
    @FXML
    private Button annulla;
    @FXML
    private Button menoGravita;
    @FXML
    private Button piuGravita;
    @FXML
    private Button menoObs;
    @FXML
    private Button piuObs;
    @FXML
    private ComboBox<Difficolta> comboDiff;
    @FXML
    private TextField vGravity;
    @FXML
    private TextField vObs;
    @FXML
    private Label l1;
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Label title;
    @FXML
    private VBox optionVbox;
    @FXML
    private Button spriteIndietro;
    @FXML
    private Button spriteAvanti;
    @FXML
    private ImageView character;

    Font font = Font.loadFont(getClass().getResourceAsStream("/levi/progettone/font/flappy-font.ttf"), 13);

    double vGravita = 1.0;
    double vObstacles = 1.0;

    ArrayList<Sprite> sprites = GameData.characters;
    int i = 0;

    public void initialize(){
        comboDiff.getItems().addAll(Difficolta.EASY, Difficolta.NORMAL, Difficolta.HARD, Difficolta.CUSTOM, Difficolta.REVERSE);
        comboDiff.getSelectionModel().select(Difficolta.NORMAL);
        aggiornaDefault(Difficolta.NORMAL);
        impostaFont();
        cambiaDiff();
    }

    @FXML
    private void impostaFont(){
        l1.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 22;");
        l2.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 22;");
        l3.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 22;");
        comboDiff.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        menoGravita.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        menoObs.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        piuGravita.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        piuObs.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        annulla.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        applica.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        title.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 40;");
        vObs.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        vGravity.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 20;");
        spriteAvanti.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 50;");
        spriteIndietro.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: 50;");

        spriteAvanti.setText("\\"+"\n/");
        spriteIndietro.setText("/\n" + "\\");

        Image image = new Image(getClass().getResource("/levi/progettone/imgs/others/background-day.png").toExternalForm());
        BackgroundSize size = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage bImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        optionVbox.setBackground(new Background(bImage));

        character.setImage(sprites.getFirst().getCurrentSprite());
        controlloBottoni();
    }

    @FXML
    public void back() throws IOException {
        Main.setRoot("views/menu");
    }

    @FXML
    public void save() throws IOException {
        Main.setRoot("views/menu");
        setStats();
        GameData.setCharacter(sprites.get(i));
    }

    private void setStats(){
        if(comboDiff.getSelectionModel().getSelectedItem().equals(Difficolta.CUSTOM)){
            GameData.set(vGravita, vObstacles);
        }else{
            GameData.set(comboDiff.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void cambiaDiff(){
        Difficolta d = comboDiff.getSelectionModel().getSelectedItem();

        if(d.equals(Difficolta.CUSTOM)){
            editable(false);
            aggiornaDefault(d);
        }else{
            editable(true);
            GameData.set(d);
            aggiornaDefault(d);
            aggiornaTesto();
        }
    }

    private void editable(boolean b){
        menoObs.setDisable(b);
        piuObs.setDisable(b);
        menoGravita.setDisable(b);
        piuGravita.setDisable(b);
    }

    @FXML
    public void setObsSpeed(javafx.event.ActionEvent actionEvent){
        String segno = ((Button)actionEvent.getSource()).getText();

        if(vObstacles == 0.5){
            menoObs.setDisable(true);

        }else if(vObstacles == 1.75){
            piuObs.setDisable(true);
        }else{
            menoObs.setDisable(false);
            piuObs.setDisable(false);
        }

        switch (segno){
            case "+":
                vObstacles += 0.25;
                break;
            case "-":
                vObstacles -= 0.25;
                break;
        }
        aggiornaTesto();
    }

    @FXML
    public void setGravity(javafx.event.ActionEvent actionEvent) {
        String segno = ((Button)actionEvent.getSource()).getText();

        if(vGravita == 0.5){
            menoGravita.setDisable(true);
        }else if(vGravita == 1.75){
            piuGravita.setDisable(true);
        }else{
            menoGravita.setDisable(false);
            piuGravita.setDisable(false);
        }

        switch (segno){
            case "+":
                vGravita += 0.25;
                break;
            case "-":
                vGravita -= 0.25;
                break;
        }
        aggiornaTesto();
    }

    private void aggiornaTesto(){
        vGravity.setText("x" + vGravita);
        vObs.setText("x" + vObstacles);
    }

    private void aggiornaDefault(Difficolta d){
        vGravita = d.grav;
        vObstacles = d.obs;
        aggiornaTesto();
    }

    public void cambiaSprite(ActionEvent actionEvent) {
        String s = ((Button)actionEvent.getSource()).getText();

        controlloBottoni();

        if(s.equals(spriteAvanti.getText())){
            i++;
            character.setImage(sprites.get(i).getCurrentSprite());
        }else{
            i--;
            character.setImage(sprites.get(i).getCurrentSprite());
        }

        controlloBottoni();
    }

    private void controlloBottoni(){
        if(i == 0){
            spriteIndietro.setDisable(true);
            if(sprites.size() == 1){
                spriteAvanti.setDisable(true);
            }else{
                spriteAvanti.setDisable(false);
            }
        }else if(i+1 == sprites.size()){
            spriteAvanti.setDisable(true);
            spriteIndietro.setDisable(false);
        }else{
            spriteAvanti.setDisable(false);
            spriteIndietro.setDisable(false);
        }
    }
}
