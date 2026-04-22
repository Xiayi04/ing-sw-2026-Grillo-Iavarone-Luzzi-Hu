package it.polimi.ingsw.Buildings;


import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Game.Player;

public class BonusPPBuilding extends Building implements BuildingInterface {
    public BonusPPBuilding(int era, int price) {
        super(era, price, 25, "BonusPPBuilding");                      //lo gestisco come pp finali dell'edificio(non da sommare subito)
    }

    /*@Override
    public void buildingActivation(Player player) {}*/

    public void printCard(){
        super.printCard();
    }

    @Override
    public void accept(Visitor visitor, Player player){
        visitor.visit(this, player);
    }
}


