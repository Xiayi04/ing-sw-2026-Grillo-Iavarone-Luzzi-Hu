package it.polimi.ingsw.Cards.Events;

public class HuntingEvent extends Event {
    private final Integer HuEvePP;
    //CONSTRUCTOR
    public HuntingEvent(int era, EventName eventName, Integer HuEvePP) {
        super(era, eventName);
        this.HuEvePP = HuEvePP;
    }

    public Integer getHuEvePP() {
        return HuEvePP;
    }
}
