package it.polimi.ingsw.CardsTest;
import it.polimi.ingsw.Model.Cards.Characters.Painter;
import it.polimi.ingsw.Model.Cards.Events.HuntingEvent;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PainterTest {
    @Test
    void painterPrintTest() {
        Painter p = new Painter(1, "CHARACTER", 2, "PAINTER");

        Printer printer = new Printer() {
            @Override
            public String[] print(Painter painter) {
                assertSame(p, painter);
                return new String[]{"print"};
            }
        };

        String[] result = p.print(printer);

        assertArrayEquals(new String[]{"print"}, result);
    }

    @Test
    void painterImagePathTest() {
        Painter p = new Painter(1, "CHARACTER", 2, "PAINTER");

        String result = p.getImagePath();

        assertEquals("/images/cards/characters/painter.png", result);
    }
    @Test
    void constructorTest(){
        Painter p = new Painter (1, "CHARACTER", 2, "PAINTER");
        assertTrue(p.getIsPickable());
    }
    @Test
    void constructorTest1(){
        HuntingEvent e = new HuntingEvent(1, "EVENT", "HUNTINGEVENT",2 );
        assertFalse(e.getIsPickable());
    }


}
