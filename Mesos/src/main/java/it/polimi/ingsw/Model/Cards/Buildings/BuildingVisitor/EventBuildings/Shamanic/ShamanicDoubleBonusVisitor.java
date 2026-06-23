package it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Shamanic;

import it.polimi.ingsw.Model.Cards.Buildings.DoubleBonusBuilding;
import it.polimi.ingsw.Model.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Model.Game.Player;

import static java.lang.Math.abs;

public class ShamanicDoubleBonusVisitor extends ShamanicAbstractVisitor {
    public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player, ShamanicEvent shamanicEvent) {
        player.modifyPP(shamanicEvent.getShEvePrizePoints());
    }

}


