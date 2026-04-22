package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Game.Player;



public abstract class EndGameBuilding extends Building {
    protected final Icons typeIcons;

    public EndGameBuilding(int era, int price, int pp,String name, Icons typeIcons){
        super(era, price,pp, name);
        this.typeIcons=typeIcons;
    }
    public Icons getTypeIcons(){
        return typeIcons;
    }


    public abstract int countPP(Player player);

}
