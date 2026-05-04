package levi.progettone.model;

import javafx.scene.image.Image;
import java.util.ArrayList;

public class NyanCat extends Sprite{

    public NyanCat() {
        this.spriteList = new ArrayList<>();

        width = 250;
        height = 140;

        offset.setLocation(-190, 0);

        var stream = getClass().getResourceAsStream("/levi/progettone/imgs/sprites/nyan/nyan-cat.gif");
        Image gif = null;
        if (stream != null) {
            gif = new Image(stream);
        }

        Image sprite = new Image(getClass().getResource("/levi/progettone/imgs/sprites/nyan/car.png").toExternalForm());

        spriteList.add(gif);
        spriteList.add(sprite);

        currentSprite = sprite;

        id = "cat";
    }

    @Override
    public void setSprite(double i){
    }
}
