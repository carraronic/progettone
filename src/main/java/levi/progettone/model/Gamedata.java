package levi.progettone.model;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    //STATS
    public static double velocitaY = 0;
    public static double gravita = 0.2;
    public static double potenzaSalto = -5;
    public static double obsSpeed = -1.2;
    public static Difficolta diff = Difficolta.NORMAL;

    //SPRITES
    private static final Bird b = new Bird();
    private static final NyanCat c = new NyanCat();
    private static final Pipe p = new Pipe();
    public static ArrayList<Sprite> characters = new ArrayList<>(List.of(b, c, p));
    public static Sprite character;

    public static void setCharacter(Sprite character) {
        GameData.character = character;
    }

    public static void set(Difficolta d){
        def();
        gravita *= d.grav;
        diff = d;
        obsSpeed *= d.obs;

        if (d.equals(Difficolta.REVERSE)){
            gravita *= -1;
        }
    }

    public static void set(double grav, double obs){
        def();
        gravita *= grav;
        diff = Difficolta.CUSTOM;
        obsSpeed *= obs;
    }

    private static void def(){
        gravita = 0.2;
        obsSpeed = -1.2;
    }

}
