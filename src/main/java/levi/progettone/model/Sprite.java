package levi.progettone.model;

import javafx.scene.image.Image;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class Sprite {
    ArrayList<Image> spriteList;
    Image currentSprite;
    double width;
    double height;
    Point2D offset = new Point2D.Double();

    public double getWidth() {
        return width;
    }

    public Point2D getOffset() {
        return offset;
    }

    public double getHeight() {
        return height;
    }

    public Image getCurrentSprite() {
        return currentSprite;
    }

    public ArrayList<Image> getSpriteList() {
        return spriteList;
    }

    public abstract void setSprite(double i);
}
