package levi.progettone.model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Pipe extends Sprite{

    public Pipe() {
        this.spriteList = new ArrayList<>();

        width = 60;
        height = 60;

        Image up = new Image(getClass().getResource("/levi/progettone/imgs/sprites/pipe/pipe-sprite.png").toExternalForm());
        spriteList.add(up);

        this.currentSprite = up;
    }

    @Override
    public void setSprite(double i){
    }
}
