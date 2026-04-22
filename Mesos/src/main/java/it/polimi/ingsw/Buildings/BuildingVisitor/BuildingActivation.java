package it.polimi.ingsw.Buildings.BuildingVisitor;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Game.Player;

/*
metto metodi visit per tutti i building anche se non devono implementare buildingactivation
 */

public abstract class BuildingActivation implements Visitor {
    public void visit(BonusStarBuilding visitorBonusStarBuilding, Player player){};
    public void visit(AddCard visitorAddCard, Player player){}
    public void visit(BonusFood bonusFood, Player player){}
    public void visit(BonusPPBuilding bonusPPBuilding, Player player){}
    public void visit(DiscountBuilding discountBuilding, Player player){}
    public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player){}
    public void visit(MultiplicationBuilding multiplicationBuilding, Player player){}
    public void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player){}
    public void visit(NoMalusBuilding noMalusBuilding, Player player){}
    public void visit(SameIconBuilding sameIconBuilding, Player player){}
    public void visit(SetBonus setBonus, Player player){}
}
