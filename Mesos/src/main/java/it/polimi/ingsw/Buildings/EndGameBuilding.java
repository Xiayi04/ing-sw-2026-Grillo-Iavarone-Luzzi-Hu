package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Game.Player;



public abstract class EndGameBuilding extends Building {
    private final Icons typeIcons;

    public EndGameBuilding(int era, int price, int pp, Icons typeIcons){
        super(era, price,pp);
        this.typeIcons=typeIcons;
    }
    public Icons getTypeIcons(){
        return typeIcons;
    }


    public abstract int countPP(Player player);

}
