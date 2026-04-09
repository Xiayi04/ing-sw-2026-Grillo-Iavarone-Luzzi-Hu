package it.polimi.ingsw.Cards.Events;

public class PaintingEvent extends Event {
    private final Integer PaEveNumMinPainters;
    private final Integer PaEveMultiplierPP;
    private final Integer PaEvePointsLoss;
    //CONSTRUCTOR
    public PaintingEvent(int era, EventName eventName,Integer minPainters, Integer multiplierPP, Integer pointsLoss) {
        super(era, eventName);
        this.PaEveNumMinPainters = minPainters;
        this.PaEveMultiplierPP = multiplierPP;
        this.PaEvePointsLoss = pointsLoss;
    }

    public Integer getPaEveNumMinPainters() {
        return PaEveNumMinPainters;
    }

    public Integer getPaEveMultiplierPP() {
        return PaEveMultiplierPP;
    }

    public Integer getPaEvePointsLoss() { //returns a negative integer
        return PaEvePointsLoss;
    }
}
