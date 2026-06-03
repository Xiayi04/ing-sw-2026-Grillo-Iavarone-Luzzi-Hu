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
        SustenanceEvent s = new SustenanceEvent(2 , "EVENT", "SUSTENANCE_EVENT",-2);
        Player p1 = new Player("ALFA", Totem.YELLOW,10,null);
        Player p2 = new Player("BETA", Totem.BLUE,5,null);

        p1.getTribeCard().add(new Shaman(2,"CHARACTER",3, "SHAMAN",3));
        p1.getTribeCard().add(new Inventor(1,"CHARACTER",2,"INVENTOR","boat"));
        p1.getTribeCard().add(new Inventor(1,"CHARACTER",2,"INVENTOR","tree"));
        p2.getTribeCard().add(new Hunter(1,"CHARACTER",2,"HUNTER",true));
        p2.getTribeCard().add(new Painter(1,"CHARACTER",2,"PAINTER "));

        ArrayList<Player> players = new ArrayList<>(List.of(p1,p2));
        s.resolveEvent(players);

        assertEquals(4, p1.getFood());
        assertEquals(1, p2.getFood());

    }


}
