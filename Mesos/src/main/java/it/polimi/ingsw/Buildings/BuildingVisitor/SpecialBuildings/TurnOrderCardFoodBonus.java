package it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Buildings.SetBonus;
import it.polimi.ingsw.Game.Player;

public interface TurnOrderCardFoodBonus {
    void visit(AddCard addCard, Player player);
    void visit(BonusStarBuilding bonusStarBuilding, Player player);
    void visit(BonusFood bonusFood, Player player);
    void visit(BonusPPBuilding bonusPPBuilding, Player player);
    void visit(DiscountBuilding discountBuilding, Player player);
    void visit(DoubleBonusBuilding doubleBonusBuilding, Player player);
    void visit(MultiplicationBuilding multiplicationBuilding, Player player);
    void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player);
    void visit(NoMalusBuilding noMalusBuilding, Player player);
    void visit(SameIconBuilding sameIconBuilding, Player player);
    void visit(SetBonus setBonus, Player player);
}
