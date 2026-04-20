package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Game.Player;

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

    public abstract void buildingActivation(Player player);

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
