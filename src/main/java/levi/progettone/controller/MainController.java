package levi.progettone.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import levi.progettone.MainApplication;

import javax.swing.*;
import java.io.IOException;

public class MainController {

    @FXML
    private Button start;
    @FXML
    private Button options;
    @FXML
    private Button exit;
    @FXML
    private Label errors;

    @FXML
    private ProgressBar loading;
    @FXML
    private ProgressIndicator circleLoad;

    private boolean menuInterface = true;

    public void initialize(){
        System.out.println("ciao");
    }

    @FXML
    public void goDelete(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/levi/progettone/views/menu.fxml"));
            Parent menu = loader.load();

            Scene scene = new Scene(menu);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
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