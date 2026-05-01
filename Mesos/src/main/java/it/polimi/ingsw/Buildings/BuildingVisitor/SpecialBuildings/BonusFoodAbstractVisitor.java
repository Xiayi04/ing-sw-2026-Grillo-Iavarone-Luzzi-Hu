package it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Game.Player;

public abstract class BonusFoodAbstractVisitor implements TurnOrderCardFoodBonus{

    @Override
    public int visit(AddCard addCard, Player player) {
        return 0;
    }

    @Override
    public int visit(BonusStarBuilding bonusStarBuilding, Player player) {
        return 0;
    }

    @Override
    public int visit(BonusFood bonusFood, Player player) {
        return 0;
    }

    @Override
    public int visit(BonusPPBuilding bonusPPBuilding, Player player) {
        return 0;
    }

    @Override
    public int visit(DiscountBuilding discountBuilding, Player player) {
        return 0;
    }

    @Override
    public int visit(DoubleBonusBuilding doubleBonusBuilding, Player player) {
        return 0;
    }

    @Override
    public int visit(MultiplicationBuilding multiplicationBuilding, Player player) {
        return 0;
    }

    @Override
    public int visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player) {
        return 0;
    }

    @Override
    public int visit(NoMalusBuilding noMalusBuilding, Player player) {
        return 0;
    }

    @Override
    public int visit(SameIconBuilding sameIconBuilding, Player player) {
        return 0;
    }

    @Override
    public int visit(SetBonus setBonus, Player player) {
        return 0;
    }
}
