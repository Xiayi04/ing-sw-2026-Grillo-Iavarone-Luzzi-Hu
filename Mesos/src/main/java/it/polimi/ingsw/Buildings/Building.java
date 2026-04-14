package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.CardType;

public abstract class Building extends Card {
    private final int price;
    private final int pp;
    private final String name;
/*costruttore */
    public Building(int era, int price, int pp, String name){
        super(era, CardType.BUILDING);
        this.price = price;
        this.pp = pp;
        this.name = name;
    }
//metodi getter


    public int getPrice(){
        return price;
    }

    public int getPP(){
        return pp;
    }

    public String getName(){return name; }

    public void printCard(){
        System.out.println("era:"+getEra());
        System.out.println("prezzo:"+price);
        System.out.println("pp:"+pp);
        System.out.println("nome:"+name);
    }

}
