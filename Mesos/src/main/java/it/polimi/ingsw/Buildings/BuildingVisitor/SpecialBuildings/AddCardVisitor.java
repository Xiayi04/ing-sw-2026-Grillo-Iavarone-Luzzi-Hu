package it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings;

import it.polimi.ingsw.Buildings.AddCard;
import it.polimi.ingsw.Game.Player;

public class AddCardVisitor extends AddCardAbstractVisitor {
    @Override
    public int visit(AddCard addCard, Player player) {
        return 1;
    }
}
