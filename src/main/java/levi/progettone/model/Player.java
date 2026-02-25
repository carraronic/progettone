package levi.progettone.model;

public class Player {
    private int speed;
    private int gravity;
    private int x;
    private int y;
    private boolean movement;

    public Player(int x, int y) {
        movement = true;
        this.x = x;
        this.y = y;
        speed = 50;
        gravity = 50;
    }

    public void setMovement(boolean movement) {
        this.movement = movement;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void move(int direction){

        if(!movement){
            return;
        }

        // 1: su | 2: giu | 3: sinistra | 4: destra
        switch(direction){
            case 1:
                y += gravity;
                break;
            case 2:
                y -= gravity;
                break;
            case 3:
                x += speed;
                break;
            case 4:
                x -= speed;
                break;
            default:
                break;
        }

    }
}
