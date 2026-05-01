package it.polimi.ingsw.Buildings.BuildingVisitor;

import it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitorInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.EndGame.EndGameVisitorInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Shamanic.ShamanicVisitorInterface;
import it.polimi.ingsw.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Game.Player;

public interface BuildingInterface {
    public void acceptActivation(Visitor visitor, Player player);
    //discount
    int acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, SustenanceEvent event);
    void acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, HuntingEvent event);
    void acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, PaintingEvent event);
    //shamanic
    void acceptShamanicEvent(ShamanicVisitorInterface visitor, Player player, ShamanicEvent shamanicEvent);
    //end game buildings
    int acceptEndGame(EndGameVisitorInterface visitor, Player player);
    //for

}
