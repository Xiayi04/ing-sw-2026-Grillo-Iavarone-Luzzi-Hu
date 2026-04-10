package it.polimi.ingsw.Buildings;
import Game.Player;

public abstract class Building {
    private final int era;
    private final int price;
    private final int pp;
/*costruttore */
    public Building(int era, int price, int pp){
        this.era = era;
        this.price = price;
        this.pp = pp;
    }
//metodi getter
    public int getEra(){
        return era;
    }

    public int getPrice(){
        return price;
    }

    public int getpp(){
        return pp;
    }


}
