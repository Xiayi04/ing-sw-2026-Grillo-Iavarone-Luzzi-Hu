package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Game.Player;

public class DoubleBonusBuilding extends Building implements BuildingInterface {
    public DoubleBonusBuilding(int era, int price, int pp){
        super(era,price,pp,"DoubleBonusBuilding");
    }
    public int giveDouble(){
        return 2;
    }

    @Override
    public void accept(Visitor visitor, Player player) {
        visitor.visit(this, player);
    }
}
