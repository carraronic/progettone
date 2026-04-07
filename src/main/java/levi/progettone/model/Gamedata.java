package levi.progettone.model;

public class Gamedata {
    public static double velocitaY = 0;
    public static double gravita = 0.2;
    public static double potenzaSalto = -5;
    public static double obsSpeed = -1.2;
    public static Difficolta diff = Difficolta.NORMAL;

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
