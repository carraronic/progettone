package levi.progettone.model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Sprite {
    ArrayList<Image> spriteList;
    Image currentSprite;

    public Image getCurrentSprite() {
        return currentSprite;
    }

    public abstract void setSprite(double i);
}
