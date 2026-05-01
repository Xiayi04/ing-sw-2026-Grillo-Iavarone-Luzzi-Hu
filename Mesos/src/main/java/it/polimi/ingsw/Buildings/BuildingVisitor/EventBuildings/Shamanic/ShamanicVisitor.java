package it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Shamanic;

import it.polimi.ingsw.Buildings.DoubleBonusBuilding;
import it.polimi.ingsw.Buildings.NoMalusBuilding;
import it.polimi.ingsw.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Game.Player;

import static java.lang.Math.abs;

public class ShamanicVisitor extends ShamanicAbstractVisitor {
    public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player, ShamanicEvent shamanicEvent) {
        player.modifyPP(shamanicEvent.getShEvePrizePoints());
    }

    public void visit(NoMalusBuilding noMalusBuilding, Player player, ShamanicEvent event){
        player.modifyPP(abs( event.getShEvePenPoints()));
    }
}


