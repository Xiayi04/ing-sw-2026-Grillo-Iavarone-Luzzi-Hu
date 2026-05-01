package it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Discount;

import it.polimi.ingsw.Buildings.DiscountBuilding;
import it.polimi.ingsw.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Game.Player;

public class DiscountVisitor extends DiscountAbstractVisitor{

    public int visit(DiscountBuilding discountBuilding, Player player, SustenanceEvent event) {
        return discountBuilding.getFoodBonusForPlayer(player);
    }

    public void visit(DiscountBuilding discountBuilding, Player player, PaintingEvent event) {
        player.modifyFood(discountBuilding.getFoodBonusForPlayer(player));
    }

    public void visit(DiscountBuilding discountBuilding, Player player, HuntingEvent event) {
        player.modifyFood(discountBuilding.getFoodBonusForPlayer(player));
        player.modifyPP(discountBuilding.getPpBonus(player));
    }
}
