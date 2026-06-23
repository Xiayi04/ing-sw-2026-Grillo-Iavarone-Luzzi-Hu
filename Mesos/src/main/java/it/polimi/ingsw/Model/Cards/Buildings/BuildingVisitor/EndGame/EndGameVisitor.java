package it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EndGame;

import it.polimi.ingsw.Model.Cards.Buildings.MultiplicationBuilding;
import it.polimi.ingsw.Model.Cards.Buildings.MultiplierPPBuilderBuilding;
import it.polimi.ingsw.Model.Game.Player;

public class EndGameVisitor extends EndGameAbstractVisitor {
    public int visit(MultiplicationBuilding multiplicationBuilding, Player player){
        return multiplicationBuilding.countPP(player);
    }

    public int visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player){
        return multiplierPPBuilderBuilding.countPP(player);
    }
}
