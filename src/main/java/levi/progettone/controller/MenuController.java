package levi.progettone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class MenuController {

    @FXML
    private Button start;
    @FXML
    private Button options;
    @FXML
    private Button exit;
    @FXML
    private Label errors;

    @FXML
    public void goStart(ActionEvent event) {
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
        errors.setText("non ci sono opzioni, semplicemnete fa figo avere un bottone in più :)");
    }

    @FXML
    public void esci(ActionEvent event){
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }






}