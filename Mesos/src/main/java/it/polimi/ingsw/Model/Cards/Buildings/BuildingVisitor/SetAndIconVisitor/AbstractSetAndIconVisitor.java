package it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor;

import it.polimi.ingsw.Model.Cards.Buildings.*;
import it.polimi.ingsw.Model.Game.Player;

public abstract class AbstractSetAndIconVisitor implements SetAndIconVisitor {

    public void visit(AddCard addCard, Player player, String icon) {

    }

    public void visit(BonusStarBuilding bonusStarBuilding, Player player, String icon) {

    }

    public void visit(BonusFood bonusFood, Player player, String icon) {

    }

    public void visit(BonusPPBuilding bonusPPBuilding, Player player, String icon) {

    }

    public void visit(DiscountBuilding discountBuilding, Player player, String icon) {

    }

    public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player, String icon) {

    }

    public void visit(MultiplicationBuilding multiplicationBuilding, Player player, String icon) {

    }

    public void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player, String icon) {

    }

    public void visit(NoMalusBuilding noMalusBuilding, Player player, String icon) {

    }

    public void visit(SameIconBuilding sameIconBuilding, Player player, String icon) {

    }

    public void visit(SetBonus setBonus, Player player, String icon) {

    }
}
