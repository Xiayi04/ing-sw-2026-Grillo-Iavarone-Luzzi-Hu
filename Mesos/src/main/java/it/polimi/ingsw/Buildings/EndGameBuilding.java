package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Game.Player;



public abstract class EndGameBuilding extends Building {
    protected final String typeIcons;

    public EndGameBuilding(int era, int price, int pp,String name, String typeIcons){
        super(era, price,pp, name);
        this.typeIcons=typeIcons;
    }
    public String getTypeIcons(){
        return typeIcons;
    }


    public abstract int countPP(Player player);

}
