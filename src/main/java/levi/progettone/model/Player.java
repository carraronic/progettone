package levi.progettone.model;

import javafx.scene.image.Image;

public class Player implements Comparable<Player>{
    Sprite sprite;
    String nome;
    int punteggio;
    Difficolta diff;

    public Player(Sprite sprite, String nome, int punteggio, Difficolta diff) {
        this.sprite = sprite;
        this.nome = nome;
        this.punteggio = punteggio;
        this.diff = diff;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public String getNome() {
        return nome;
    }

    public void setDiff(Difficolta diff) {
        this.diff = diff;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Difficolta getDiff() {
        return diff;
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
