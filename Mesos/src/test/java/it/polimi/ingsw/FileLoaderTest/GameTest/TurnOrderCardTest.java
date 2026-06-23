package it.polimi.ingsw.FileLoaderTest.GameTest;

import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Model.Game.TurnOrderCard;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TurnOrderCardTest {
    @Test
    void TOCConstructorTest(){
        TurnOrderCard toc = new TurnOrderCard(3);
        assertEquals(3, toc.getNumPlayers());
        assertNotNull(toc.getOrder());
        assertTrue(toc.getOrder().isEmpty());
    }
    @Test
    void makeTwoPlayersFoodTest(){
        TurnOrderCard toc = new TurnOrderCard(2);
        assertEquals(1, toc.getFoodByIndex(0));
        assertEquals(-1, toc.getFoodByIndex(1));
    }
    @Test
    void makeThreePlayersFoodTest(){
        TurnOrderCard toc = new TurnOrderCard(3);
        assertEquals(2, toc.getFoodByIndex(0));
        assertEquals(0, toc.getFoodByIndex(1));
        assertEquals(-1, toc.getFoodByIndex(2));
    }
    @Test
    void makeFourPlayersFoodTest() {
        TurnOrderCard toc = new TurnOrderCard(4);

        assertEquals(2, toc.getFoodByIndex(0));
        assertEquals(1, toc.getFoodByIndex(1));
        assertEquals(0, toc.getFoodByIndex(2));
        assertEquals(-1, toc.getFoodByIndex(3));
    }
    @Test
    void getHeaRemovePlayer(){
            TurnOrderCard toc1 = new TurnOrderCard(2);

            Player p1 = new Player("gemma", Totem.YELLOW, 0, null);
            Player p2 = new Player("luna", Totem.ORANGE, 0, null);

            toc1.getOrder().add(p1);
            toc1.getOrder().add(p2);

            Totem head = toc1.getHead();

            assertEquals(Totem.YELLOW, head);
            assertEquals(1, toc1.getOrder().size());
            assertSame(p2, toc1.getOrder().get(0));

        }
       @Test
       void getHeadShouldThrowIfOrderIsEmpty() {
           TurnOrderCard toc = new TurnOrderCard(2);

           assertThrows(Exception.class, toc::getHead);
       }
       @Test
       void getFoodIndexMinusNegative(){
           TurnOrderCard toc = new TurnOrderCard(2);

           int result = toc.getFoodByIndex(-1);

           assertEquals(-2, result);
       }
    @Test
    void getFoodIndexLargerThanLenght() {
        TurnOrderCard toc = new TurnOrderCard(2);
        int result = toc.getFoodByIndex(2);

        assertEquals(-2, result);
    }
    @Test
    void getFoodCorrectIndex() {
        TurnOrderCard toc = new TurnOrderCard(2);

        assertEquals(1, toc.getFoodByIndex(0));
        assertEquals(-1, toc.getFoodByIndex(1));
    }
    @Test
    void getFoodForFivePlayers() {
        TurnOrderCard toc = new TurnOrderCard(5);

        assertEquals(3, toc.getFoodByIndex(0));
        assertEquals(1, toc.getFoodByIndex(1));
        assertEquals(0, toc.getFoodByIndex(2));
        assertEquals(0, toc.getFoodByIndex(3));
        assertEquals(-1, toc.getFoodByIndex(4));
    }
    @Test
    void printShouldReturnPrinterResult() {
        TurnOrderCard toc = new TurnOrderCard(2);

        Printer printer = new Printer() {
            @Override
            public String[] print(TurnOrderCard turnOrderCard) {
                assertSame(toc, turnOrderCard);
                return new String[]{"test print"};
            }


        };

        String[] result = toc.print(printer);

        assertArrayEquals(new String[]{"test print"}, result);
    }


}














