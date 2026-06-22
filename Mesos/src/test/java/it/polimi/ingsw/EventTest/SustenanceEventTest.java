package it.polimi.ingsw.EventTest;

import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Cards.Events.EventName;
import it.polimi.ingsw.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SustenanceEventTest {
    Player p1 = new Player("ALFA", Totem.YELLOW,0,null);
    Player p2 = new Player("BETA", Totem.BLUE,0,null);


    @Test
    void payCharacterFoodInTheTribe_Test(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        p1.modifyFood(10);
        p2.modifyFood(5);

        p1.getTribeCard().add(new Shaman(2,"CHARACTER",3, "SHAMAN",3));
        p1.getTribeCard().add(new Inventor(1,"CHARACTER",2,"INVENTOR","boat"));
        p1.getTribeCard().add(new Inventor(1,"CHARACTER",2,"INVENTOR","tree"));
        p2.getTribeCard().add(new Hunter(1,"CHARACTER",2,"HUNTER",true));
        p2.getTribeCard().add(new Painter(1,"CHARACTER",2,"PAINTER "));
        SustenanceEvent s = new SustenanceEvent(2 , "EVENT", "SUSTENANCE_EVENT",-2);

        s.resolveEvent(players);

        assertEquals(7, p1.getFood());
        assertEquals(3, p2.getFood());
    }
    @Test
    public void payCharacterInTheTribeWithPP_Test(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        p1.modifyPP(10);
        p2.modifyPP(5);

        p1.getTribeCard().add(new Shaman(2,"CHARACTER",3, "SHAMAN",3));
        p1.getTribeCard().add(new Inventor(1,"CHARACTER",2,"INVENTOR","boat"));
        p1.getTribeCard().add(new Inventor(1,"CHARACTER",2,"INVENTOR","tree"));
        p2.getTribeCard().add(new Hunter(1,"CHARACTER",2,"HUNTER",true));
        p2.getTribeCard().add(new Painter(1,"CHARACTER",2,"PAINTER "));
        SustenanceEvent s = new SustenanceEvent(2 , "EVENT", "SUSTENANCE_EVENT",-2);

        s.resolveEvent(players);
        assertEquals(4,p1.getPrestigePoints());
        assertEquals(1,p2.getPrestigePoints());
    }//

    @Test
    public void payCharacterInTheTribeWithFoodAndPP_Test(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        p1.modifyFood(1);
        p1.modifyPP(10);
        p2.modifyFood(1);
        p2.modifyPP(5);

        p1.getTribeCard().add(new Shaman(2,"CHARACTER",3, "SHAMAN",3));
        p1.getTribeCard().add(new Inventor(1,"CHARACTER",2,"INVENTOR","boat"));
        p1.getTribeCard().add(new Inventor(1,"CHARACTER",2,"INVENTOR","tree"));
        p2.getTribeCard().add(new Hunter(1,"CHARACTER",2,"HUNTER",true));
        p2.getTribeCard().add(new Painter(1,"CHARACTER",2,"PAINTER "));
        SustenanceEvent s = new SustenanceEvent(2 , "EVENT", "SUSTENANCE_EVENT",-2);

        s.resolveEvent(players);
        assertEquals(0,p1.getFood());
        assertEquals(6,p1.getPrestigePoints());
        assertEquals(0,p2.getFood());
        assertEquals(3,p2.getPrestigePoints());


    }
    @Test
    void sustenancePrintCardDoesNotThrow() {
        SustenanceEvent event = new SustenanceEvent(2, "EVENT", "SUSTENANCE_EVENT", -2);
        assertDoesNotThrow(event::printCard);
    }
    @Test
    void valueOfCorrectEventName() {
        assertEquals(EventName.SUSTENANCE_EVENT, EventName.valueOf("SUSTENANCE_EVENT"));
    }

    @Test
    void nameShouldReturnCorrectString() {
        assertEquals("SUSTENANCE_EVENT", EventName.SUSTENANCE_EVENT.name());
    }
}
