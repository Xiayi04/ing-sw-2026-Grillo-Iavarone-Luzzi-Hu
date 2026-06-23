package it.polimi.ingsw.EventTest;

import it.polimi.ingsw.Visitors.CharacterVisitor.AddAndCountCharacter;
import it.polimi.ingsw.Visitors.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Model.Cards.Characters.Hunter;
import it.polimi.ingsw.Model.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HuntingEventTest {
    Player p1 = new Player("X", Totem.BLACK,0,null);
    Player p2 = new Player("Y", Totem.YELLOW,0,null);
    Player p3 = new Player("Z", Totem.ORANGE,0,null);
    CharacterVisitor visitor = new AddAndCountCharacter();

    @Test
    void shouldThrowExceptionForSinglePlayer(){
        ArrayList<Player> players = new ArrayList<Player>();
        HuntingEvent h = new HuntingEvent(1,"EVENT","HUNTING_EVENT",1);
        players.add(p1);
        assertThrows(IllegalArgumentException.class, () -> {h.resolveEvent(players);
        });
    }

    @Test
    void testHuntingEvent() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        p1.modifyFood(10);
        p1.modifyPP(2);
        p2.modifyFood(10);
        p2.modifyPP(0);
        p3.modifyFood(10);
        p3.modifyPP(1);
        Hunter h1 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        h1.addCard(visitor,p1);
        Hunter h2 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        h2.addCard(visitor,p1);
        Hunter h3 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        h3.addCard(visitor,p1);
        Hunter h4 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        h4.addCard(visitor,p2);
        Hunter h5 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        h5.addCard(visitor,p2);
        Hunter h6 = new Hunter(1,"CHARACTER",3,"HUNTER",true);
        h6.addCard(visitor,p3);
        HuntingEvent h = new HuntingEvent(2,"EVENT","HUNTING_EVENT",2);

        h.resolveEvent(players);

        assertEquals(13,p1.getFood());
        assertEquals(8,p1.getPrestigePoints());
        assertEquals(12,p2.getFood());
        assertEquals(4,p2.getPrestigePoints());
        assertEquals(12,p3.getFood());
        assertEquals(3,p3.getPrestigePoints());
    }

    @Test
    public void printCardDoesNotThrowTest(){
        HuntingEvent event = new HuntingEvent(2,"EVENT","HUNTING_EVENT",2);
        assertDoesNotThrow(event::printCard);
    }
}
