package levi.progettone.model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Bird extends Sprite{

    public Bird() {
        this.spriteList = new ArrayList<>();

        width = 60;
        height = 40;

        Image up = new Image(getClass().getResource("/levi/progettone/imgs/sprites/bird/yellowbird-upflap.png").toExternalForm());
        Image down = new Image(getClass().getResource("/levi/progettone/imgs/sprites/bird/yellowbird-downflap.png").toExternalForm());
        Image mid = new Image(getClass().getResource("/levi/progettone/imgs/sprites/bird/yellowbird-midflap.png").toExternalForm());

        spriteList.add(up);
        spriteList.add(down);
        spriteList.add(mid);

        this.currentSprite = spriteList.getLast();

        id = "bird";
    }

    @Override
    public void setSprite(double i){
        if(i < -1){
            currentSprite = spriteList.getFirst();
        }else if (i > 1){
            currentSprite = spriteList.get(1);
        }else{
            currentSprite = spriteList.getLast();
        }
    }
}
