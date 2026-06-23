package it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor;

import it.polimi.ingsw.Model.Cards.Buildings.*;
import it.polimi.ingsw.Model.Game.Player;

public interface SetAndIconVisitor {
    void visit(AddCard addCard, Player player, String icon);
    void visit(BonusStarBuilding bonusStarBuilding, Player player, String icon);
    void visit(BonusFood bonusFood, Player player, String icon);
    void visit(BonusPPBuilding bonusPPBuilding, Player player, String icon);
    void visit(DiscountBuilding discountBuilding, Player player, String icon);
    void visit(DoubleBonusBuilding doubleBonusBuilding, Player player, String icon);
    void visit(MultiplicationBuilding multiplicationBuilding, Player player, String icon);
    void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player, String icon);
    void visit(NoMalusBuilding noMalusBuilding, Player player, String icon);
    void visit(SameIconBuilding sameIconBuilding, Player player, String icon);
    void visit(SetBonus setBonus, Player player, String icon);
}
