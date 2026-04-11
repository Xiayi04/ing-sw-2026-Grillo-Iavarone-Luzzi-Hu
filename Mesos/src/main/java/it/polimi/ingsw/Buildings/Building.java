package it.polimi.ingsw.Buildings;
import it.polimi.ingsw.Game.Player;

public abstract class Building {
    private final int era;
    private final int price;
    private final int pp;
    private final String name;
/*costruttore */
    public Building(int era, int price, int pp, String name){
        this.era = era;
        this.price = price;
        this.pp = pp;
        this.name = name;
    }
//metodi getter
    public int getEra(){
        return era;
    }

    public int getPrice(){
        return price;
    }

    public int getPP(){
        return pp;
    }

    public String getName(){return name; }


}
