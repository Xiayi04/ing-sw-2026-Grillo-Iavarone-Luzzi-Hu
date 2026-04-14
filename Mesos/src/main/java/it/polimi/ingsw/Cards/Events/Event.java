package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Game.Player;

import java.util.ArrayList;

public abstract class Event extends Card {
    private final EventName eventName;

    public String getEventName() {
        return eventName.toString();
    }
    //CONSTRUCTOR
    public Event(int era, CardType cardType, EventName eventName) {
        super(era, cardType);
        this.eventName = eventName;
    }

    public void printCard(){
        super.printCard();
        System.out.println("tipo di evento:"+eventName.toString());
    }


    public abstract void resolveEvent(ArrayList<Player> players);
}
