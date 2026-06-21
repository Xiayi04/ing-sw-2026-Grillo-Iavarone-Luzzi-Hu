package it.polimi.ingsw.EventTest;

import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.AddAndCountCharacter;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Cards.Characters.Painter;
import it.polimi.ingsw.Cards.Events.EventName;
import it.polimi.ingsw.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
}
