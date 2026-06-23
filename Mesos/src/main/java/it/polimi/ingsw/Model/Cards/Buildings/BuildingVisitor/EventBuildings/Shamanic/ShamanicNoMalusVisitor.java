package it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Shamanic;

import it.polimi.ingsw.Model.Cards.Buildings.NoMalusBuilding;
import it.polimi.ingsw.Model.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Model.Game.Player;

import static java.lang.Math.abs;

public class ShamanicNoMalusVisitor extends ShamanicAbstractVisitor{
    public void visit(NoMalusBuilding noMalusBuilding, Player player, ShamanicEvent event){
        player.modifyPP(abs( event.getShEvePenPoints()));
    }
}
