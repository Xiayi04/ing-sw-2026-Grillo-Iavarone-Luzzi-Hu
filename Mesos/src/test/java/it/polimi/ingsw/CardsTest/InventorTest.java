package it.polimi.ingsw.CardsTest;

import it.polimi.ingsw.Model.Cards.Characters.Hunter;
import it.polimi.ingsw.Model.Cards.Characters.Inventor;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventorTest {
    @Test
    void ConstructorIconTest(){
        Inventor inventor = new Inventor(1, "CHARACTER", 2, "INVENTOR", "BOAT");
        assertEquals("boat", inventor.getInventorIcon());
        assertNotNull(inventor.getInventorIcon());

        Inventor tree = new Inventor(1, "CHARACTER", 2, "INVENTOR", "TREE");
        assertEquals("tree", tree.getInventorIcon());
        assertNotNull(inventor.getInventorIcon());

        Inventor hook = new Inventor(1, "CHARACTER", 2, "INVENTOR", "HOOK");
        assertEquals("hook", hook.getInventorIcon());
        assertNotNull(inventor.getInventorIcon());

        Inventor necklace = new Inventor(1, "CHARACTER", 2, "INVENTOR", "NECKLACE");
        assertEquals("necklace", necklace.getInventorIcon());
        assertNotNull(inventor.getInventorIcon());

        Inventor bowl = new Inventor(1, "CHARACTER", 2, "INVENTOR", "BOWL");
        assertEquals("bowl", bowl.getInventorIcon());
        assertNotNull(inventor.getInventorIcon());

        Inventor knot = new Inventor(1, "CHARACTER", 2, "INVENTOR", "KNOT");
        assertEquals("knot", knot.getInventorIcon());
        assertNotNull(inventor.getInventorIcon());

        Inventor doll = new Inventor(1, "CHARACTER", 2, "INVENTOR", "DOLL");
        assertEquals("doll", doll.getInventorIcon());
        assertNotNull(inventor.getInventorIcon());

        Inventor flute = new Inventor(1, "CHARACTER", 2, "INVENTOR", "FLUTE");
        assertEquals("flute", flute.getInventorIcon());
        assertNotNull(inventor.getInventorIcon());

        Inventor leather = new Inventor(1, "CHARACTER", 2, "INVENTOR", "LEATHER");
        assertEquals("leather", leather.getInventorIcon());
        assertNotNull(inventor.getInventorIcon());

        Inventor bread = new Inventor(1, "CHARACTER", 2, "INVENTOR", "BREAD");
        assertEquals("bread", bread.getInventorIcon());
        assertNotNull(inventor.getInventorIcon());

    }
    @Test
    void printTest() {
        Inventor inv = new Inventor(1, "CHARACTER", 2, "INVENTOR", "BOAT");

        Printer printer = new Printer() {
            @Override
            public String[] print(Inventor inventor) {
                assertSame(inv, inventor);
                return new String[]{"inventor printed"};
            }
        };

        String[] result = inv.print(printer);

        assertArrayEquals(new String[]{"inventor printed"}, result);
    }
    @Test
    void ImagePathTest() {
        Inventor inventor = new Inventor (1, "CHARACTER", 2, "INVENTOR", "boat");

        String result = inventor.getImagePath();
        assertEquals("/images/cards/characters/inventor_boat.png", result);
    }






}
