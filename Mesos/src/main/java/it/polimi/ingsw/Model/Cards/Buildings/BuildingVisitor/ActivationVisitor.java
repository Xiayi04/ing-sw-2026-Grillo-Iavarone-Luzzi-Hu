package it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor;

import it.polimi.ingsw.Model.Cards.Buildings.*;
import it.polimi.ingsw.Model.Game.Player;

/*
metto metodi visit per tutti i building anche se non devono implementare building activation
 */
public interface ActivationVisitor {
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
