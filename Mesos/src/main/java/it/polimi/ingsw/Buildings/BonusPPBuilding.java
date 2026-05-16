package it.polimi.ingsw.Buildings;


import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;

public class BonusPPBuilding extends Building implements BuildingInterface {
    public BonusPPBuilding(int era, int price) {
        super(era, price, 25, "BonusPPBuilding");                      //lo gestisco come pp finali dell'edificio(non da sommare subito)
    }

    /*@Override
    public void buildingActivation(Player player) {}*/

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


