package levi.progettone.model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

public class NyanCat extends Sprite{

    public NyanCat() {
        this.spriteList = new ArrayList<>();

        Image sprite = new Image(getClass().getResource("/levi/progettone/imgs/sprites/nyan/car.png").toExternalForm());
        currentSprite = sprite;
    }

    @Override
    public void setSprite(double i){
    }
}
