package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Game.Player;

public class NoMalusBuilding extends Building implements BuildingInterface {
    public NoMalusBuilding(int era, int price, int pp) {
        super(era, price, pp, "NoMalusBuilding");
    }
    public boolean noMalus(){
        return true;
    }

    @Override
    public void accept(Visitor visitor, Player player) {
        visitor.visit(this, player);
    }
}
