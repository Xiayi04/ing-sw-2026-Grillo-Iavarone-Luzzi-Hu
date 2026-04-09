package it.polimi.ingsw.Cards.Events;

public abstract class Event {
    private final int era;
    private final EventName eventName;

    public String getEventName() {
        return eventName.toString();
    }
    //CONSTRUCTOR
    public Event(int era, EventName eventName) {
        this.era = era;
        this.eventName = eventName;
    }
}
