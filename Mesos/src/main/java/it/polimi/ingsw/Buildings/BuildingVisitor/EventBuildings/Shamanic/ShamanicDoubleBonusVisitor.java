package it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Shamanic;

import it.polimi.ingsw.Buildings.DoubleBonusBuilding;
import it.polimi.ingsw.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Game.Player;

import static java.lang.Math.abs;

public class ShamanicDoubleBonusVisitor extends ShamanicAbstractVisitor {
    public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player, ShamanicEvent shamanicEvent) {
        player.modifyPP(shamanicEvent.getShEvePrizePoints());
    }

}


