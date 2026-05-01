package it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Discount;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Game.Player;

public interface DiscountVisitorInterface {
    int visit(AddCard addCard, Player player, SustenanceEvent event);
    int visit(BonusStarBuilding bonusStarBuilding, Player player, SustenanceEvent event);
    int visit(BonusFood bonusFood, Player player, SustenanceEvent event);
    int visit(BonusPPBuilding bonusPPBuilding, Player player, SustenanceEvent event);
    int visit(DiscountBuilding discountBuilding, Player player, SustenanceEvent event);
    int visit(DoubleBonusBuilding doubleBonusBuilding, Player player, SustenanceEvent event);
    int visit(MultiplicationBuilding multiplicationBuilding, Player player, SustenanceEvent event);
    int visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player, SustenanceEvent event);
    int visit(NoMalusBuilding noMalusBuilding, Player player, SustenanceEvent event);
    int visit(SameIconBuilding sameIconBuilding, Player player, SustenanceEvent event);
    int visit(SetBonus setBonus, Player player, SustenanceEvent event);

    void visit(AddCard addCard, Player player, PaintingEvent event);
    void visit(BonusStarBuilding bonusStarBuilding, Player player, PaintingEvent event);
    void visit(BonusFood bonusFood, Player player, PaintingEvent event);
    void visit(BonusPPBuilding bonusPPBuilding, Player player, PaintingEvent event);
    void visit(DiscountBuilding discountBuilding, Player player, PaintingEvent event);
    void visit(DoubleBonusBuilding doubleBonusBuilding, Player player, PaintingEvent event);
    void visit(MultiplicationBuilding multiplicationBuilding, Player player, PaintingEvent event);
    void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player, PaintingEvent event);
    void visit(NoMalusBuilding noMalusBuilding, Player player, PaintingEvent event);
    void visit(SameIconBuilding sameIconBuilding, Player player, PaintingEvent event);
    void visit(SetBonus setBonus, Player player, PaintingEvent event);

    void visit(AddCard addCard, Player player, HuntingEvent event);
    void visit(BonusStarBuilding bonusStarBuilding, Player player, HuntingEvent event);
    void visit(BonusFood bonusFood, Player player, HuntingEvent event);
    void visit(BonusPPBuilding bonusPPBuilding, Player player, HuntingEvent event);
    void visit(DiscountBuilding discountBuilding, Player player, HuntingEvent event);
    void visit(DoubleBonusBuilding doubleBonusBuilding, Player player, HuntingEvent event);
    void visit(MultiplicationBuilding multiplicationBuilding, Player player, HuntingEvent event);
    void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player, HuntingEvent event);
    void visit(NoMalusBuilding noMalusBuilding, Player player, HuntingEvent event);
    void visit(SameIconBuilding sameIconBuilding, Player player, HuntingEvent event);
    void visit(SetBonus setBonus, Player player, HuntingEvent event);
}
