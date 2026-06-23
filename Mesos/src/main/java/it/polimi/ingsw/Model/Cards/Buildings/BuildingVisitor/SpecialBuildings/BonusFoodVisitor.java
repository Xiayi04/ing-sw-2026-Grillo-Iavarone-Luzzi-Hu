package it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SpecialBuildings;

import it.polimi.ingsw.Model.Cards.Buildings.BonusFood;
import it.polimi.ingsw.Model.Game.Player;

public class BonusFoodVisitor extends BonusFoodAbstractVisitor {
    @Override
    public void visit(BonusFood bonusFood, Player player) {
        player.modifyFood(bonusFood.giveExtraFood());
    }
}
