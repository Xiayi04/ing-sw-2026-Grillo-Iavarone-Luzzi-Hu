package it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings;

import it.polimi.ingsw.Buildings.AddCard;
import it.polimi.ingsw.Game.Player;

public class AddCardVisitor extends SpecialBuildingsAbstractVisitor {
    @Override
    public void visit(AddCard addCard, Player player) {
        //Nuova richiesta di una carta
       // player.getVirtualClient().askForBuildingIndex(addCard.addArrow());
    }
}
