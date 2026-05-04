package levi.progettone.model;

import javafx.scene.image.Image;

public class Player implements Comparable<Player>{
    Sprite sprite;
    String nome;
    int punteggio;

    public Player(Sprite sprite, String nome, int punteggio) {
        this.sprite = sprite;
        this.nome = nome;
        this.punteggio = punteggio;
    }

    public String getNome() {
        return nome;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Image getSelectedSprite(){
        return sprite.currentSprite;
    }

    @Override
    public String toString(){
        return nome + " " + punteggio;
    }

    @Override
    public int compareTo(Player o) {
        return (this.punteggio - o.getPunteggio())*-1;
    }
}
