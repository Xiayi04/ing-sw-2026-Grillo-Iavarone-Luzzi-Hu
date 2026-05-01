package it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Discount;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Game.Player;

public abstract class DiscountAbstractVisitor implements DiscountVisitorInterface {

    @Override
    public int visit(AddCard addCard, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public int visit(BonusStarBuilding bonusStarBuilding, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public int visit(BonusFood bonusFood, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public int visit(BonusPPBuilding bonusPPBuilding, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public int visit(DiscountBuilding discountBuilding, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public int visit(DoubleBonusBuilding doubleBonusBuilding, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public int visit(MultiplicationBuilding multiplicationBuilding, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public int visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public int visit(NoMalusBuilding noMalusBuilding, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public int visit(SameIconBuilding sameIconBuilding, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public int visit(SetBonus setBonus, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public void visit(AddCard addCard, Player player, PaintingEvent event) {

    }

    @Override
    public void visit(BonusStarBuilding bonusStarBuilding, Player player, PaintingEvent event) {

    }

    @Override
    public void visit(BonusFood bonusFood, Player player, PaintingEvent event) {

    }

    @Override
    public void visit(BonusPPBuilding bonusPPBuilding, Player player, PaintingEvent event) {

    }

    @Override
    public void visit(DiscountBuilding discountBuilding, Player player, PaintingEvent event) {

    }

    @Override
    public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player, PaintingEvent event) {

    }

    @Override
    public void visit(MultiplicationBuilding multiplicationBuilding, Player player, PaintingEvent event) {

    }

    @Override
    public void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player, PaintingEvent event) {

    }

    @Override
    public void visit(NoMalusBuilding noMalusBuilding, Player player, PaintingEvent event) {

    }

    @Override
    public void visit(SameIconBuilding sameIconBuilding, Player player, PaintingEvent event) {

    }

    @Override
    public void visit(SetBonus setBonus, Player player, PaintingEvent event) {

    }

    @Override
    public void visit(AddCard addCard, Player player, HuntingEvent event) {

    }

    @Override
    public void visit(BonusStarBuilding bonusStarBuilding, Player player, HuntingEvent event) {

    }

    @Override
    public void visit(BonusFood bonusFood, Player player, HuntingEvent event) {

    }

    @Override
    public void visit(BonusPPBuilding bonusPPBuilding, Player player, HuntingEvent event) {

    }

    @Override
    public void visit(DiscountBuilding discountBuilding, Player player, HuntingEvent event) {

    }

    @Override
    public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player, HuntingEvent event) {

    }

    @Override
    public void visit(MultiplicationBuilding multiplicationBuilding, Player player, HuntingEvent event) {

    }

    @Override
    public void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player, HuntingEvent event) {

    }

    @Override
    public void visit(NoMalusBuilding noMalusBuilding, Player player, HuntingEvent event) {

    }

    @Override
    public void visit(SameIconBuilding sameIconBuilding, Player player, HuntingEvent event) {

    }

    @Override
    public void visit(SetBonus setBonus, Player player, HuntingEvent event) {

    }
}
