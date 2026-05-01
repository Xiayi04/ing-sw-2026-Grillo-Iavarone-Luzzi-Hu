package it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings;

import it.polimi.ingsw.Buildings.BonusFood;
import it.polimi.ingsw.Game.Player;

public class BonusFoodVisitor extends BonusFoodAbstractVisitor {
    @Override
    public int visit(BonusFood bonusFood, Player player) {
        return bonusFood.giveExtraFood();
    }
}
