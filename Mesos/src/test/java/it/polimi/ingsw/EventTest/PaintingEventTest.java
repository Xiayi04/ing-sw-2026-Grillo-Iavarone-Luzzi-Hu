package it.polimi.ingsw.EventTest;

import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Cards.Characters.Painter;
import it.polimi.ingsw.Cards.Events.EventName;
import it.polimi.ingsw.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaintingEventTest {

    @Test
    void paintingEventTest() {
        PaintingEvent p = new PaintingEvent(1, CardType.EVENT, EventName.PAINTING_EVENT,3,5,-2);
        Player p1 = new Player("p1", Totem.RED,5);
        Player p2 = new Player("p2", Totem.BLUE,5);


        p1.getTribeCard().add(new Painter(1,CardType.CHARACTER,2, CharacterType.PAINTER));
        p1.getTribeCard().add(new Painter(1,CardType.CHARACTER,2, CharacterType.PAINTER));
        p1.getTribeCard().add(new Painter(2,CardType.CHARACTER,2, CharacterType.PAINTER));
        p2.getTribeCard().add(new Painter(1,CardType.CHARACTER,2, CharacterType.PAINTER));
        p2.getTribeCard().add(new Painter(1,CardType.CHARACTER,2, CharacterType.PAINTER));

        ArrayList<Player> players = new ArrayList<>(List.of(p1,p2));
        p.resolveEvent(players);

        assertEquals(15,p1.getPrestigePoints());
        assertEquals(-2,p2.getPrestigePoints());



    }
}
