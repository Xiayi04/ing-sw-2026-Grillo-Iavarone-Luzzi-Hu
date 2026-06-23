package it.polimi.ingsw.CardsTest;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.Cards.Characters.Hunter;
import it.polimi.ingsw.Model.Cards.Characters.Painter;
import it.polimi.ingsw.Model.Cards.Characters.Picker;
import it.polimi.ingsw.Model.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PickerTest {
    @Test
    //PER VEDERE SE LO SCONTO EFFETTIVAMENTE SI APPLICHI
    void getDiscountTest(){
        Player p = new Player("Nina", Totem.YELLOW, 0, null);
        Player p1 = new Player("X", Totem.BLACK, 0, null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p);
        players.add(p1);
        p.modifyFood(1);
        Picker picker = new Picker(1, "CHARACTER", 2, "PICKER");
        Hunter h1 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        Hunter h2 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        Hunter h3 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        Hunter h4 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        Hunter h5 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        Hunter h6 = new Hunter(1,"CHARACTER",3,"HUNTER",true);
        Hunter h8 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        Hunter h9 = new Hunter(1,"CHARACTER",3,"HUNTER",false);
        Hunter h10 = new Hunter(1,"CHARACTER",3,"HUNTER",true);
        p.getTribeCard().add(picker);
        p.getTribeCard().add(h1);
        p.getTribeCard().add(h2);
        p.getTribeCard().add(h3);
   /*     p.getTribeCard().add(h4);
        p.getTribeCard().add(h5);
        p.getTribeCard().add(h6);

        p.getTribeCard().add(h8);
        p.getTribeCard().add(h9);
        p.getTribeCard().add(h10);*/
        p.setPickerCounter(1);


      //  assertEquals(10, p.getTribeCard().size());
        assertEquals(-3, picker.getDiscount());
        SustenanceEvent s = new SustenanceEvent(2 , "EVENT", "SUSTENANCE_EVENT",-2);
        s.resolveEvent(players);
        assertEquals(0, p.getFood());
    //    assertEquals(3, p.getFood());

    }
    @Test
    void getDiscountShouldReturnMinusThree() {
        Picker picker = new Picker(1, "CHARACTER", 2, "PICKER");

        assertEquals(-3, picker.getDiscount());
    }
    @Test
    void PickerPrintTest() {
        Picker p = new Picker(1, "CHARACTER", 2, "PAINTER");

        Printer printer = new Printer() {
            @Override
            public String[] print(Picker picker) {
                assertSame(p, picker);
                return new String[]{"print"};
            }
        };

        String[] result = p.print(printer);

        assertArrayEquals(new String[]{"print"}, result);
    }

    @Test
    void painterImagePathTest() {
        Painter p = new Painter(1, "CHARACTER", 2, "PICKER");

        String result = p.getImagePath();

        assertEquals("/images/cards/characters/picker.png", result);
    }
}
