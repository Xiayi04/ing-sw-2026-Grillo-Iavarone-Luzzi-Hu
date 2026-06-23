package it.polimi.ingsw.Factory;
import it.polimi.ingsw.Model.Cards.Characters.Character;
import it.polimi.ingsw.Model.Cards.Events.Event;


public interface Factory {
    public abstract Character createInventor(int era, int numPlayers, String typeIcon);
    public abstract Character createHunter(int era, int numPlayers, boolean symbol);
    public abstract Character createBuilder(int era, int numPlayers, int discount, int pp);
    public abstract Character createPainter(int era, int numPlayers);
    public abstract Character createShaman(int era, int numPlayers, int shamanStars);
    public abstract Character createPicker(int era, int numPlayers);
    public abstract Event createShamanicEvent(int era, int penPoints, int prizePoints);
    public abstract Event createHuntingEvent(int era, int HuEvePP);
    public abstract Event createPaintingEvent(int era, int minPainters, int multiplierPP, int pointsLoss);
    public abstract Event createSustenanceEvent(int era,  int pointsLossMultiplier);
}
