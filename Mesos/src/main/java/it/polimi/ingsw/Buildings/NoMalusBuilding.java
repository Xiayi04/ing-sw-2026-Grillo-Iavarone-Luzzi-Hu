package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Shamanic.ShamanicVisitorInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;

public class NoMalusBuilding extends Building implements BuildingInterface {
    public NoMalusBuilding(int era, int price, int pp) {
        super(era, price, pp, "NoMalusBuilding");
    }
    public boolean noMalus(){
        return true;
    }

    @Override
    public void acceptActivation(ActivationVisitor activationVisitor, Player player) {
        activationVisitor.visit(this, player);
    }

    @Override
    public void acceptShamanicEvent(ShamanicVisitorInterface visitor, Player player, ShamanicEvent shamanicEvent) {
        visitor.visit(this, player, shamanicEvent);
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }
}
