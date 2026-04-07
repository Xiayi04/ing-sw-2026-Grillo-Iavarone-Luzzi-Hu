package it.polimi.ingsw.Cards.Events;

public enum EventName {
    SHAMANIC_EVENT,
    HUNTING_EVENT,
    PAINTING_EVENT,
    SUSTENANCE_EVENT;

    public static boolean containsEventName(EventName eventName) {
        boolean c = false;
        for (EventName event: EventName.values()) {
            if (eventName.toString().equals(event.toString())) {
                c = true;
                break;
            }
        }
        return c;
    }
}
