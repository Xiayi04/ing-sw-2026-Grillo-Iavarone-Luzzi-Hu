package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.EndGame.EndGameVisitorInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Game.Player;

public class MultiplicationBuilding extends EndGameBuilding {
    private final int multiplier;

    //costruttore
    public MultiplicationBuilding(int era, int price, int pp, Icons typeIcons, int multiplier){
        super (era, price, pp, "MultiplicationBuilding", typeIcons);
        this.multiplier = multiplier;
    }

    //getter
    public int getMultiplier(){
        return multiplier;
    }

    @Override
    public int countPP(Player player){
        if(getTypeIcons() == Icons.SET){
            return player.countSet()*multiplier;
        }else{
            return player.countTribeCardsByIcon(getTypeIcons().toString())*multiplier;
        }
    }

    @Override
    public void acceptActivation(Visitor visitor, Player player) {
        visitor.visit(this,player);
    }
    //HO fatto due metodi in player per contare le carte di un tipo e i set completi
    @Override
    public int acceptEndGame(EndGameVisitorInterface visitor, Player player){
        return visitor.visit(this,player);
    }
}



