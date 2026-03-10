package levi.progettone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 432, 768);
        stage.getIcons().add(new Image(Main.class.getResource("imgs/icon.png").toExternalForm()));
        stage.setTitle("Flappy Bird!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}