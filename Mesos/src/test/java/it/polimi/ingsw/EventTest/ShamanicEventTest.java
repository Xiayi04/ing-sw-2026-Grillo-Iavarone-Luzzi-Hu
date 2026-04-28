package it.polimi.ingsw.EventTest;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.NoMalusBuilding;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Cards.Characters.Shaman;
import it.polimi.ingsw.Cards.Events.EventName;
import it.polimi.ingsw.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShamanicEventTest {

    @Test
    void onlyPlayer(){
        ShamanicEvent s = new ShamanicEvent(2,CardType.EVENT,EventName.SHAMANIC_EVENT,-2,3);
        Player p1 = new Player("p1",Totem.RED,5);
        ArrayList<Player> players = new ArrayList<>(List.of(p1));
        assertThrows(IllegalArgumentException.class, ()->{s.getMaxStars(players);});
    }

    @Test
    void playersWithMoreStars(){
        ShamanicEvent s = new ShamanicEvent(2, CardType.EVENT, EventName.SHAMANIC_EVENT,-2,3);
        Player p1 = new Player("ALFA", Totem.RED,10);
        Player p2 = new Player("BETA", Totem.BLUE,5);
        Player p3 = new Player("JAMMA", Totem.BLACK,10);

        p1.getTribeCard().add(new Shaman(2,CardType.CHARACTER,3, CharacterType.SHAMAN,3));
        p1.getTribeCard().add(new Shaman(2,CardType.CHARACTER,3, CharacterType.SHAMAN,2));
        p2.getTribeCard().add(new Shaman(2,CardType.CHARACTER,3, CharacterType.SHAMAN,2));
        p3.getTribeCard().add(new Shaman(2,CardType.CHARACTER,3, CharacterType.SHAMAN,0));

        ArrayList<Player> players = new ArrayList<>(List.of(p1,p2,p3));
        p1.modifyStarCounter(5);
        p2.modifyStarCounter(2);
        p3.modifyStarCounter(0);

        assertEquals(5,s.getMaxStars(players));
        assertEquals(0,s.getMinStars(players));
    }

    @Test
    void resolveEventShamanic(){
        ShamanicEvent s = new ShamanicEvent(2, CardType.EVENT, EventName.SHAMANIC_EVENT,-2,3);
        Player p1 = new Player("ALFA", Totem.RED,10);
        Player p2 = new Player("BETA", Totem.BLUE,5);
        Player p3 = new Player("JAMMA", Totem.BLACK,10);
        Player p4 = new Player("DELTA", Totem.BLACK,7);

        p1.getTribeCard().add(new Shaman(2,CardType.CHARACTER,3, CharacterType.SHAMAN,3));
        p1.getTribeCard().add(new Shaman(2,CardType.CHARACTER,3, CharacterType.SHAMAN,2));
        p2.getTribeCard().add(new Shaman(2,CardType.CHARACTER,3, CharacterType.SHAMAN,2));
        p3.getTribeCard().add(new Shaman(2,CardType.CHARACTER,3, CharacterType.SHAMAN,0));
        p4.getTribeCard().add(new Shaman(2,CardType.CHARACTER,3, CharacterType.SHAMAN,0));

        p1.modifyStarCounter(5);
        p2.modifyStarCounter(2);
        p3.modifyStarCounter(0);
        p4.modifyStarCounter(0);

        ArrayList<Player> players = new ArrayList<>(List.of(p1,p2,p3,p4));
        s.resolveEvent(players);

        assertEquals(3,p1.getPrestigePoints());
        assertEquals(0,p2.getPrestigePoints());
        assertEquals(-2,p3.getPrestigePoints());
        assertEquals(-2,p4.getPrestigePoints());

    }

    @Test
    void NoMalusBuilding(){
    }
}
