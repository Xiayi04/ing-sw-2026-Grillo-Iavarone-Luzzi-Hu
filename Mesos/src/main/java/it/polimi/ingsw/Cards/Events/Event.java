package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Game.Player;

import java.util.ArrayList;

public abstract class Event extends Card {
    private final String eventName;

    public String getEventName() {
        return eventName;
    }
    //CONSTRUCTOR
    public Event(int era, String cardType, String eventName) {
        super(era, "EVENT");
        this.eventName = eventName;
    }

    public void printCard(){
        super.printCard();
        System.out.println("tipo di evento:"+eventName);
    }


    public abstract void resolveEvent(ArrayList<Player> players);
}
