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
    String id;

    public double getWidth() {
        return width;
    }

    public String getId() {
        return id;
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

    public static Sprite getInstance(String id){
        return switch (id) {
            case "cat" -> new NyanCat();
            case "bird" -> new Bird();
            case "pipe" -> new Pipe();
            default -> null;
        };
    }
}
