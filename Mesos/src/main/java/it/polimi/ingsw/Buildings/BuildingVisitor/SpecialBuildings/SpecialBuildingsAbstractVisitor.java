package it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Game.Player;

public abstract class SpecialBuildingsAbstractVisitor implements SpecialBuildingsInterface {

    @Override
    public void visit(AddCard addCard, Player player) {
    }

    @Override
    public void visit(BonusStarBuilding bonusStarBuilding, Player player) {
    }

    @Override
    public void visit(BonusFood bonusFood, Player player) {
    }

    @Override
    public void visit(BonusPPBuilding bonusPPBuilding, Player player) {
    }

    @Override
    public void visit(DiscountBuilding discountBuilding, Player player) {
    }

    @Override
    public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player) {

    }

    @Override
    public void visit(MultiplicationBuilding multiplicationBuilding, Player player) {

    }

    @Override
    public void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player) {

    }

    @Override
    public void visit(NoMalusBuilding noMalusBuilding, Player player) {

    }

    @Override
    public void visit(SameIconBuilding sameIconBuilding, Player player) {

    }

    @Override
    public void visit(SetBonus setBonus, Player player) {

    }

}
