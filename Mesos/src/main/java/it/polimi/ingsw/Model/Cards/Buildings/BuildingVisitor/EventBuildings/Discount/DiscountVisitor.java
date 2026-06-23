package it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Discount;

import it.polimi.ingsw.Model.Cards.Buildings.DiscountBuilding;
import it.polimi.ingsw.Model.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Model.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Model.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Model.Game.Player;

import static java.lang.Math.abs;

public class DiscountVisitor extends DiscountAbstractVisitor{

    public int visit(DiscountBuilding discountBuilding, Player player, SustenanceEvent event) {
        return discountBuilding.getFoodBonusForSustenance(player);
    }

    public void visit(DiscountBuilding discountBuilding, Player player, PaintingEvent event) {
        player.modifyFood(abs(discountBuilding.getFoodBonusForPainting(player)));
    }

    public void visit(DiscountBuilding discountBuilding, Player player, HuntingEvent event) {
        player.modifyFood(abs(discountBuilding.getFoodBonusForHunting(player)));
        player.modifyPP(abs(discountBuilding.getPpBonus(player)));
    }
}
