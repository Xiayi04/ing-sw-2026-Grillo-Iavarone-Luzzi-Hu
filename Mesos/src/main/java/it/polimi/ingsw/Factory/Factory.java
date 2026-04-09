package it.polimi.ingsw.Factory;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.InventorIcon;
import it.polimi.ingsw.Cards.Events.Event;

public interface Factory {
    public abstract Character createInventor(int era, int numPlayers, String typeIcon);
    public abstract Character createHunter(int era, int numPlayers, boolean symbol);
    public abstract Character createBuilder(int era, int numPlayers, int discount, int pp);
    public abstract Character createPainter(int era, int numPlayers);
    public abstract Character createShaman(int era, int numPlayers, int shamanStars);
    public abstract Character createPicker(int era, int numPlayers);
    public abstract Event createShamanicEvent(int penaltyPoints, int prizePoints);
    public abstract Event createHuntingEvent(int pp);
    public abstract Event createPaintingEvent(int numMinPainters, int multiplierPP, int pointsLoss);
    public abstract Event createSustenanceEvent(int pointsLossMultiplier);
    public abstract Building createBonusPPBuilding();
    public abstract Building createMoltiplicationBuilding(Icons typeIcon, int multiplier);
    public abstract Building createMultiplierPPBuilderBuilding();
    public abstract Building createDiscountBuilding(int food, int pp, Icons typeIcon, Events typeEvent);
    public abstract Building createNoMalusBuilding();
    public abstract Building createDoubleBonusBuilding();
    public abstract Building createBonusStarsBuilding();
    public abstract Building createAddCardBuilding();
    public abstract Building createBonusFoodBuilding();
    public abstract Building createSetBonusBuilding();
    public abstract Building createSameIconBuilding();
}
