package it.polimi.ingsw.Cards.Events;

public class ShamanicEvent extends Event {
    private final Integer ShEvePenPoints;
    private final Integer ShEvePrizePoints;
    //CONSTRUCTOR
    public ShamanicEvent(int era, EventName eventName, Integer penPoints, Integer prizePoints) {
        super(era, eventName);
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
}
