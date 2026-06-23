package it.polimi.ingsw.Model.Cards.Buildings;


import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;

public class BonusPPBuilding extends Building implements BuildingInterface {
    public BonusPPBuilding(int era, int price) {
        super(era, price, 25, "BonusPPBuilding");
    }


    public void printCard(){
        super.printCard();
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }

    @Override
    public void acceptActivation(ActivationVisitor activationVisitor, Player player){
        activationVisitor.visit(this, player);
    }
}


