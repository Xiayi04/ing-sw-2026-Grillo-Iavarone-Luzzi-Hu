package it.polimi.ingsw.CardsTest;

import it.polimi.ingsw.Model.Cards.Characters.Builder;
import it.polimi.ingsw.Model.Cards.Characters.Hunter;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HunterTest {
    @Test
    void printHunterTest() {
        Hunter h = new Hunter(1, "CHARACTER", 2, "HUNTER", false);

        Printer printer = new Printer() {
            @Override
            public String[] print(Hunter hunter) {
                assertSame(h, hunter);
                return new String[]{" printed"};
            }
        };

        String[] result = h.print(printer);

        assertArrayEquals(new String[]{" printed"}, result);
    }
    @Test
    void ImagePathTest() {
        Hunter hunter = new Hunter (1, "CHARACTER", 2, "HUNTER", false);

        String result = hunter.getImagePath();
        assertEquals("/images/cards/characters/hunter_false.png", result);
    }
}
