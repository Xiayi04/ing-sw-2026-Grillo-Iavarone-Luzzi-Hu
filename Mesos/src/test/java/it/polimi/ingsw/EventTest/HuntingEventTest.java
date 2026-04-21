package it.polimi.ingsw.EventTest;

import it.polimi.ingsw.Buildings.DiscountBuilding;
import it.polimi.ingsw.Buildings.Events;
import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.Builder;
import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Cards.Characters.Hunter;
import it.polimi.ingsw.Cards.Events.EventName;
import it.polimi.ingsw.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HuntingEventTest {

    @Test
    void shouldThrowExceptionForSinglePlayer(){
        ArrayList<Player> players = new ArrayList<Player>();
        HuntingEvent h = new HuntingEvent(1,CardType.EVENT,EventName.HUNTING_EVENT,1);
        players.add(new Player("X", Totem.BLACK, 10));
        assertThrows(IllegalArgumentException.class, () -> {h.resolveEvent(players);
        });
    }

    @Test
    void testHuntingEvent() {
        HuntingEvent h = new HuntingEvent(2,CardType.EVENT,EventName.HUNTING_EVENT,1);
        Player p1 = new Player("X", Totem.BLACK,10);
        Player p2 = new Player("Y", Totem.RED,10);
        Player p3 = new Player("Z", Totem.ORANGE,10);
        p1.getTribeCard().add(new Hunter(1,CardType.CHARACTER,3, CharacterType.HUNTER,false));
        p1.getTribeCard().add(new Hunter(1,CardType.CHARACTER,3, CharacterType.HUNTER,true));
        p1.getTribeCard().add(new Hunter(1,CardType.CHARACTER,3, CharacterType.HUNTER,false));
        p2.getTribeCard().add(new Hunter(1,CardType.CHARACTER,3, CharacterType.HUNTER,false));
        p2.getTribeCard().add(new Hunter(1,CardType.CHARACTER,3, CharacterType.HUNTER,true));
        p3.getTribeCard().add(new Hunter(1,CardType.CHARACTER,3, CharacterType.HUNTER,true));

        ArrayList<Player> players = new ArrayList<>(List.of(p1,p2,p3));
        h.resolveEvent(players);

        assertEquals(13,p1.getFood());
        assertEquals(3,p1.getPrestigePoints());
        assertEquals(12,p2.getFood());
        assertEquals(2,p2.getPrestigePoints());
        assertEquals(11,p3.getFood());
        assertEquals(1,p3.getPrestigePoints());




    }
}
