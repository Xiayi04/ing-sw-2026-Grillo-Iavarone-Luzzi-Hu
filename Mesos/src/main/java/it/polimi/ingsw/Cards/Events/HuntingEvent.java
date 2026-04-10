package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Cards.CardType;

public class HuntingEvent extends Event {
    private final Integer HuEvePP;
    //CONSTRUCTOR
    public HuntingEvent(int era, CardType cardType, EventName eventName, Integer HuEvePP) {
        super(era, cardType, eventName);
        this.HuEvePP = HuEvePP;
    }

    public Integer getHuEvePP() {
        return HuEvePP;
    }

    public void printCard(){
        super.printCard();
        System.out.println("punti pp:"+HuEvePP);
    }
}
