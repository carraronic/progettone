package levi.progettone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class MainController {

    //MENU
    @FXML
    private Button start;
    @FXML
    private Button options;
    @FXML
    private Button exit;
    @FXML
    private Label errors;

    //CARICAMENTO
    @FXML
    public AnchorPane caricamento;
    @FXML
    private ProgressBar loading;
    @FXML
    private ProgressIndicator circleLoad;

    double percentuale = 0d;

    public void initialize(){
        System.out.println("ciao");
    }

    @FXML
    public void goDelete(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/levi/progettone/views/game.fxml"));

            Scene scene = new Scene(loader.load(),320, 240);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setTitle("game");
            window.setScene(scene);
            window.setFullScreen(true);
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void apriMenu(){
        errors.setText("non ci sono opzioni in sto gioco");
    }

    @FXML
    public void esci(ActionEvent event){
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

}