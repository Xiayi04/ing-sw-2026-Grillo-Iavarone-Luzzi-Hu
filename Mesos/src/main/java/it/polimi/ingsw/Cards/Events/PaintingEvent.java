package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Cards.CardType;

public class PaintingEvent extends Event {
    private final Integer PaEveNumMinPainters;
    private final Integer PaEveMultiplierPP;
    private final Integer PaEvePointsLoss;
    //CONSTRUCTOR
    public PaintingEvent(int era, CardType cardType, EventName eventName, Integer minPainters, Integer multiplierPP, Integer pointsLoss) {
        super(era, cardType ,eventName);
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

    public void printCard() {
        super.printCard();
        System.out.println("pittori minimi:"+PaEveNumMinPainters);
        System.out.println("moltiplicatore:"+PaEveMultiplierPP);
        System.out.println("punti persi:"+PaEvePointsLoss);
    }
}
