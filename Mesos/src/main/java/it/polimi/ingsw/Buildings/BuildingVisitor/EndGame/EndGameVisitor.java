package it.polimi.ingsw.Buildings.BuildingVisitor.EndGame;

import it.polimi.ingsw.Buildings.MultiplicationBuilding;
import it.polimi.ingsw.Buildings.MultiplierPPBuilderBuilding;
import it.polimi.ingsw.Game.Player;

public class EndGameVisitor {
    public int visit(MultiplicationBuilding multiplicationBuilding, Player player){
        return multiplicationBuilding.countPP(player);
    }

    public int visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player){
        return multiplierPPBuilderBuilding.countPP(player);
    }
}
