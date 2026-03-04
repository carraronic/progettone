package levi.progettone.model;

public class Player {
    private final double value;
    private double x;
    private double y;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        value = 100;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getValue() {
        return value;
    }

    public void move(int direction){

        // 1: su | 2: giu | 3: sinistra | 4: destra
        switch(direction){
            case 1:
                y -= value;
                break;
            case 2:
                y += value;
                break;
            case 3:
                x -= value;
                break;
            case 4:
                x += value;
                break;
            default:
                break;
        }

    }
}
