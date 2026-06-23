package it.polimi.ingsw.Visitors.BuildingVisitor.SpecialBuildings;

import it.polimi.ingsw.Model.Cards.Buildings.AddCard;
import it.polimi.ingsw.Model.Game.Player;

public class AddCardVisitor extends AddCardAbstractVisitor {
    @Override
    public int visit(AddCard addCard, Player player) {
        return 1;
    }
}
