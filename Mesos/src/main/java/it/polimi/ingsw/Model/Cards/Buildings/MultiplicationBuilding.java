package it.polimi.ingsw.Model.Cards.Buildings;

import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EndGame.EndGameVisitorInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;

public class MultiplicationBuilding extends EndGameBuilding {
    private final int multiplier;

    //costruttore
    public MultiplicationBuilding(int era, int price, int pp, String typeIcons, int multiplier){
        super (era, price, pp, "MultiplicationBuilding", typeIcons);
        this.multiplier = multiplier;
    }

    //getter
    public int getMultiplier(){
        return multiplier;
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }

    @Override
    public int countPP(Player player){
        if(getTypeIcons().equals("SET")){
            return player.countSet()*multiplier;
        }else{
            return player.countTribeCardsByIcon(getTypeIcons())*multiplier;
        }
    }

    @Override
    public void acceptActivation(ActivationVisitor activationVisitor, Player player) {
        activationVisitor.visit(this,player);
    }
    //HO fatto due metodi in player per contare le carte di un tipo e i set completi
    @Override
    public int acceptEndGame(EndGameVisitorInterface visitor, Player player){
        return visitor.visit(this,player);
    }

    public String getImagePath(){
        return "/images/cards/buildings/"+getName()+"_"+getTypeIcons().toUpperCase()+".png";
    }
}



