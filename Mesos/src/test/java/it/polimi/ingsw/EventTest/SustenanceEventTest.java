package it.polimi.ingsw.EventTest;

import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Events.EventName;
import it.polimi.ingsw.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SustenanceEventTest {

    @Test
    void payCharacterFoodInTheTribe(){
        SustenanceEvent s = new SustenanceEvent(2, CardType.EVENT, EventName.SUSTENANCE_EVENT,-2);
        Player p1 = new Player("ALFA", Totem.RED,10);
        Player p2 = new Player("BETA", Totem.BLUE,5);

        p1.getTribeCard().add(new Shaman(2,CardType.CHARACTER,3, CharacterType.SHAMAN,3));
        p1.getTribeCard().add(new Inventor(1,CardType.CHARACTER,2,CharacterType.INVENTOR,"boat"));
        p1.getTribeCard().add(new Inventor(1,CardType.CHARACTER,2,CharacterType.INVENTOR,"tree"));
        p2.getTribeCard().add(new Hunter(1,CardType.CHARACTER,2,CharacterType.HUNTER,true));
        p2.getTribeCard().add(new Painter(1,CardType.CHARACTER,2,CharacterType.PAINTER));

        ArrayList<Player> players = new ArrayList<>(List.of(p1,p2));
        s.resolveEvent(players);

        assertEquals(4, p1.getFood());
        assertEquals(1, p2.getFood());

    }


}
