package it.polimi.ingsw.CardsTest;

import it.polimi.ingsw.Model.Cards.Characters.Shaman;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShamanTest {










    @Test
    void shamanPrintTest() {
        Shaman s = new Shaman(1, "CHARACTER", 2, "SHAMAN", 3);

        Printer printer = new Printer() {
            @Override
            public String[] print(Shaman shaman) {
                assertSame(s, shaman);
                return new String[]{"ok"};
            }
        };

        String[] result = s.print(printer);

        assertArrayEquals(new String[]{"ok"}, result);
    }

    @Test
    void shamanImagePathTest() {
        Shaman s = new Shaman(1, "CHARACTER", 2, "SHAMAN", 3);

        String result = s.getImagePath();

        assertEquals("/images/cards/characters/shaman_3star.png", result);
    }
}
