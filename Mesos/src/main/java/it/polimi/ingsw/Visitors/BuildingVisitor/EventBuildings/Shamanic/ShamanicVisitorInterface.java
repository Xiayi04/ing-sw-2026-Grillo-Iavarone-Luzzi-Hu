package it.polimi.ingsw.Visitors.BuildingVisitor.EventBuildings.Shamanic;

import it.polimi.ingsw.Model.Cards.Buildings.*;
import it.polimi.ingsw.Model.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Model.Game.Player;

public interface ShamanicVisitorInterface {
    void visit(AddCard addCard, Player player, ShamanicEvent shamanicEvent);
    void visit(BonusStarBuilding bonusStarBuilding, Player player, ShamanicEvent shamanicEvent);
    void visit(BonusFood bonusFood, Player player, ShamanicEvent shamanicEvent);
    void visit(BonusPPBuilding bonusPPBuilding, Player player, ShamanicEvent shamanicEvent);
    void visit(DiscountBuilding discountBuilding, Player player, ShamanicEvent shamanicEvent);
    void visit(DoubleBonusBuilding doubleBonusBuilding, Player player, ShamanicEvent shamanicEvent);
    void visit(MultiplicationBuilding multiplicationBuilding, Player player, ShamanicEvent shamanicEvent);
    void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player, ShamanicEvent shamanicEvent);
    void visit(NoMalusBuilding noMalusBuilding, Player player, ShamanicEvent shamanicEvent);
    void visit(SameIconBuilding sameIconBuilding, Player player, ShamanicEvent shamanicEvent);
    void visit(SetBonus setBonus, Player player, ShamanicEvent shamanicEvent);
}
