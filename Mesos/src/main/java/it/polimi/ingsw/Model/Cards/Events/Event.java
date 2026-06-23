package it.polimi.ingsw.Model.Cards.Events;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.Game.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Event extends Card implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String eventName;

    public String getEventName() {
        return eventName;
    }
    //CONSTRUCTOR
    public Event(int era, String cardType, String eventName) {
        super(era, "EVENT", false);
        this.eventName = eventName;
    }

    public void printCard(){
        super.printCard();
        System.out.println("tipo di evento:"+eventName);
    }


    public abstract void resolveEvent(ArrayList<Player> players);

    public String getImagePath(){
        return "/images/cards/events/"+getEventName()+"_era"+getEra()+".png";
    }
}
