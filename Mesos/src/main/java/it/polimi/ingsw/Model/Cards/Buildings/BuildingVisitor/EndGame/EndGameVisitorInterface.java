package it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EndGame;

import it.polimi.ingsw.Model.Cards.Buildings.*;
import it.polimi.ingsw.Model.Game.Player;

public interface EndGameVisitorInterface {
    int visit(AddCard addCard, Player player);
    int visit(BonusStarBuilding bonusStarBuilding, Player player);
    int visit(BonusFood bonusFood, Player player);
    int visit(BonusPPBuilding bonusPPBuilding, Player player);
    int visit(DiscountBuilding discountBuilding, Player player);
    int visit(DoubleBonusBuilding doubleBonusBuilding, Player player);
    int visit(MultiplicationBuilding multiplicationBuilding, Player player);
    int visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player);
    int visit(NoMalusBuilding noMalusBuilding, Player player);
    int visit(SameIconBuilding sameIconBuilding, Player player);
    int visit(SetBonus setBonus, Player player);
}
