package it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor;

import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitorInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EndGame.EndGameVisitorInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Shamanic.ShamanicVisitorInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor.SetAndIconVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SpecialBuildings.AddCardVisitorInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SpecialBuildings.TurnOrderCardFoodBonus;
import it.polimi.ingsw.Model.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Model.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Model.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Model.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Model.Game.Player;

public interface BuildingInterface {
    public void acceptActivation(ActivationVisitor activationVisitor, Player player);
    //discount
    int acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, SustenanceEvent event);
    void acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, HuntingEvent event);
    void acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, PaintingEvent event);
    //shamanic
    void acceptShamanicEvent(ShamanicVisitorInterface visitor, Player player, ShamanicEvent shamanicEvent);
    //end game buildings
    int acceptEndGame(EndGameVisitorInterface visitor, Player player);
    //for
    void acceptFoodBonus(TurnOrderCardFoodBonus visitor, Player player);
    int acceptAddCard(AddCardVisitorInterface visitor, Player player);
    void acceptSameIconBonus(SetAndIconVisitor visitor, Player player, String icon);
    void acceptSetBonus(SetAndIconVisitor visitor, Player player);
}
