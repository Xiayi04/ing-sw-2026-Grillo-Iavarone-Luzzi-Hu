package it.polimi.ingsw.EventTest;

import it.polimi.ingsw.Model.Cards.Events.SustenanceEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventAbstractTest {
    @Test
    void getImagePathSustenanceEvent() {
        SustenanceEvent event = new SustenanceEvent(2, "EVENT", "SUSTENANCE_EVENT", -2);

        assertEquals("/images/cards/events/SUSTENANCE_EVENT_era2.png", event.getImagePath());

    }
    @Test
    void printCardTest() {
        SustenanceEvent event = new SustenanceEvent(2, "EVENT", "SUSTENANCE_EVENT", -2);
        assertDoesNotThrow(event::printCard);
    }

}
