package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Cards.CardType;

public class ShamanicEvent extends Event {
    private final Integer ShEvePenPoints;
    private final Integer ShEvePrizePoints;
    //CONSTRUCTOR
    public ShamanicEvent(int era, CardType cardType, EventName eventName, Integer penPoints, Integer prizePoints) {
        super(era, cardType, eventName);
        this.ShEvePenPoints = penPoints;
        this.ShEvePrizePoints = prizePoints;
    }

    /**
     *  @return a negative integer
     */
    public Integer getShEvePenPoints() { //returns a negative integer
        return ShEvePenPoints;
    }

    public Integer getShEvePrizePoints() {
        return ShEvePrizePoints;
    }

    public void printCard() {
        super.printCard();
        System.out.println("punti penalità:"+ShEvePenPoints);
        System.out.println("punti guadagnati:"+ShEvePrizePoints);
    }
}
