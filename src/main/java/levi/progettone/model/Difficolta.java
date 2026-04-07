package levi.progettone.model;

public enum Difficolta {
    EASY(0.75, 1), NORMAL(1, 1), HARD(1.5, 1), CUSTOM(1, 1), REVERSE(1, 1);

    Difficolta(double index, double grav) {
        this.obs = index;
        this.grav = grav;
    }

    public final double obs;
    public final double grav;
}
