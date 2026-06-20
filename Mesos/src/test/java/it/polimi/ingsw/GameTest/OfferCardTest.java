package it.polimi.ingsw.GameTest;

import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.UI.Printer;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class OfferCardTest {

    @Test
    public void OfferCardConstructorTest() {
    Board board = new Board();
    ArrayList<Player> players= new ArrayList<>();
    for(int i=0;i<5;i++){
        Player p = new Player("aura"+ i, Totem.BLACK,0,null);
        players.add(p);
    }
    ArrayList<OfferCard> path =  board.obtainPath(players);

    assertEquals(7, path.size());
    //first offer card
        assertEquals(0,path.getFirst().getDownArrow());
        assertEquals(0,path.getFirst().getUpArrow());
        assertTrue(path.getFirst().isFood());
    //second offer card
        assertEquals(1,path.get(1).getDownArrow());
        assertEquals( 0,path.get(1).getUpArrow());
        assertFalse(path.get(1).isFood());
    //third offer card
        assertEquals(0,path.get(2).getDownArrow());
        assertEquals( 1,path.get(2).getUpArrow());
        assertFalse(path.get(2).isFood());
    //4th offer card
        assertEquals(2,path.get(3).getDownArrow());
        assertEquals( 0,path.get(3).getUpArrow());
        assertFalse(path.get(1).isFood());
    //5th offer card
        assertEquals(1,path.get(4).getDownArrow());
        assertEquals( 1,path.get(4).getUpArrow());
        assertFalse(path.get(1).isFood());
    //6th offer card
        assertEquals(0,path.get(5).getDownArrow());
        assertEquals( 2,path.get(5).getUpArrow());
        assertFalse(path.get(1).isFood());
    //7th offer card
        assertEquals(1,path.get(6).getDownArrow());
        assertEquals( 2,path.get(6).getUpArrow());
        assertFalse(path.get(1).isFood());
    }

    @Test
    public void OfferCardOccupyAndReleseTest(){
        Board board = new Board();
        ArrayList<Player> players= new ArrayList<>();
        for(int i=0;i<5;i++){
            Player p = new Player("aura"+ i, Totem.BLACK,0,null);
            players.add(p);
        }
        ArrayList<OfferCard> path =  board.obtainPath(players);
        //occupy the first card with the first player
        path.getFirst().setOccupiedBy(players.get(0));
        assertTrue(path.getFirst().isOccupied());
        assertEquals(players.get(0),path.getFirst().getOccupiedBy());
        //relese the card
        path.getFirst().release();
        assertFalse(path.getFirst().isOccupied());

    }

    /**
     * the test checks the correct values of the offercard inizialized
     */
    @Test
    public void createOfferCardRight(){
        OfferCard card2 = new OfferCard(3,1,2,false);
        assertEquals(3,card2.getID());
        assertEquals(1,card2.getUpArrow());
        assertEquals(2, card2.getDownArrow());
        assertFalse(card2.isFood());
        assertFalse(card2.isOccupied());
        assertNull(card2.getOccupiedBy());
    }
    /**
     * the test checks whether the free
     * offercard can be occupied correctly or not.
     */
    @Test
    public void OfferCardOccupiedBy(){
        OfferCard card = new OfferCard(1,2,1,false);
        Player player = new Player("juan", Totem.YELLOW,0,null);

        card.setOccupiedBy(player);
        assertTrue(card.isOccupied());
        assertEquals(player, card.getOccupiedBy());

    }
    /**
     * test used to check if an offerCard is freed correctly
     */
    @Test
    public void releaseAnOccupiedOfferCardTest(){
        OfferCard card = new OfferCard(1, 2, 1, false);
        Player player2 = new Player("THEO", Totem.BLACK, 2, null);

        card.setOccupiedBy(player2);
        card.release();
        assertFalse(card.isOccupied());
        assertNull(card.getOccupiedBy());

    }

    /**
     * It checks that the player's name should
     * not be null, if the card is occupied
     */
    @Test
    void setOccupiedByWithNullParameter() {
        OfferCard card = new OfferCard(1, 2, 1, false);

        assertThrows(IllegalArgumentException.class, () -> {
            card.setOccupiedBy(null);
        });

        assertFalse(card.isOccupied());
        assertNull(card.getOccupiedBy());
    }

    /**
     * it verifies that the method of print is callable
     * it verifies that the result is not null
     */
    @Test
    void printTest() {
        OfferCard card = new OfferCard(1, 2, 1, true);
        Printer printer = new Printer();

        String[] result = card.print(printer);

        assertNotNull(result);
    }


}
