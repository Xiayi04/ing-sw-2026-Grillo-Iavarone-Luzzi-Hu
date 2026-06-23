package it.polimi.ingsw.Model.Cards.Buildings;

import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Shamanic.ShamanicVisitorInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;

public class DoubleBonusBuilding extends Building implements BuildingInterface {
    public DoubleBonusBuilding(int era, int price, int pp){
        super(era,price,pp,"DoubleBonusBuilding");
    }
    public int giveDouble(){
        return 2;
    }

    @Override
    public void acceptActivation(ActivationVisitor activationVisitor, Player player) {
        activationVisitor.visit(this, player);
    }

    @Override
    public void acceptShamanicEvent(ShamanicVisitorInterface visitor, Player player, ShamanicEvent shamanicEvent){
        visitor.visit(this, player, shamanicEvent);
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }
}
