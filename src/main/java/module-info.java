module levi.progettone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;
    requires jdk.xml.dom;


    opens levi.progettone to javafx.fxml;
    exports levi.progettone;
    exports levi.progettone.controller;
    opens levi.progettone.controller to javafx.fxml;
}