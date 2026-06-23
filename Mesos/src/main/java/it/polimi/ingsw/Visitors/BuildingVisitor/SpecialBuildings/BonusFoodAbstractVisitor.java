package it.polimi.ingsw.Visitors.BuildingVisitor.SpecialBuildings;

import it.polimi.ingsw.Model.Cards.Buildings.*;
import it.polimi.ingsw.Model.Game.Player;

public abstract class BonusFoodAbstractVisitor implements TurnOrderCardFoodBonus{


    public void visit(AddCard addCard, Player player) {

    }

    public void visit(BonusStarBuilding bonusStarBuilding, Player player) {

    }

    public void visit(BonusFood bonusFood, Player player) {

    }

    public void visit(BonusPPBuilding bonusPPBuilding, Player player) {

    }

    public void visit(DiscountBuilding discountBuilding, Player player) {

    }

    public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player) {

    }

    public void visit(MultiplicationBuilding multiplicationBuilding, Player player) {

    }

    public void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player) {

    }

    public void visit(NoMalusBuilding noMalusBuilding, Player player) {

    }

    public void visit(SameIconBuilding sameIconBuilding, Player player) {

    }

    public void visit(SetBonus setBonus, Player player) {

    }
}
