package it.polimi.ingsw.EventTest;

import it.polimi.ingsw.Visitors.CharacterVisitor.AddAndCountCharacter;
import it.polimi.ingsw.Visitors.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Model.Cards.Characters.Painter;
import it.polimi.ingsw.Model.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaintingEventTest {
    Player p1 = new Player("xiayi", Totem.ORANGE,0,null);
    Player p2 = new Player("denise", Totem.BLUE,0,null);
    CharacterVisitor visitor = new AddAndCountCharacter();

    @Test
    void paintingEventTest() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        p1.modifyPP(5);
        p2.modifyPP(0);
        Painter painter1= new Painter(1,"CHARACTER",2, "PAINTER");
        painter1.addCard(visitor,p1);
        Painter painter2 = new Painter(1,"CHARACTER",2, "PAINTER");
        painter2.addCard(visitor,p1);
        Painter painter3 = new Painter(1,"CHARACTER",2, "PAINTER");
        painter3.addCard(visitor,p1);

        PaintingEvent p = new PaintingEvent(1, "EVENT", "PAINTING_EVENT",1,1,-2);

        p.resolveEvent(players);

        assertEquals(8,p1.getPrestigePoints());
        assertEquals(-2,p2.getPrestigePoints());
    }

    @Test
    public void printCardTest(){
        PaintingEvent event = new PaintingEvent(1, "EVENT", "PAINTING_EVENT",1,1,-2);
        assertDoesNotThrow(event::printCard);
    }
    @Test
    void paintingEventGettersTest() {
        PaintingEvent event = new PaintingEvent(1, "EVENT", "PAINTING_EVENT", 2, 3, -4);

        assertEquals(2, event.getPaEveNumMinPainters());
        assertEquals(3, event.getPaEveMultiplierPP());
        assertEquals(-4, event.getPaEvePointsLoss());
    }
}
