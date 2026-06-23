package it.polimi.ingsw.Visitors.BuildingVisitor.SetAndIconVisitor;

import it.polimi.ingsw.Model.Cards.Buildings.SameIconBuilding;
import it.polimi.ingsw.Model.Cards.Buildings.SetBonus;
import it.polimi.ingsw.Model.Game.Player;

public class ConcreteSetAndIconVisitor extends AbstractSetAndIconVisitor{

    @Override
    public void visit(SameIconBuilding sameIconBuilding, Player player, String icon) {
        if (sameIconBuilding.giveFoodBonus(player, icon)){
            throw new AddedFoodException("");
        }
    }

    @Override
    public void visit(SetBonus setBonus, Player player, String icon) {
        if (setBonus.giveExtraFoodSet(player)){
            throw new AddedFoodException("");
        }
    }
}
