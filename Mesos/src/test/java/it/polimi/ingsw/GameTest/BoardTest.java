package it.polimi.ingsw.GameTest;

import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;
    Player player1 = new Player("beppe", Totem.ORANGE,0,null);
    Player player2 = new Player("mattia", Totem.BLACK,0,null);
    Player player3 = new Player("xia", Totem.WHITE,0,null);
    Player player4 = new Player("denise", Totem.BLUE,0,null);
    Player player5 = new Player("juan", Totem.YELLOW,0,null);

    @BeforeEach
    void setUp() {
        board  = new Board();
    }
    @Test
    public void initializeBoardTest() {
        int numPlayers = 4;

        board.getPlayers().add(player1);
        board.getPlayers().add(player2);
        board.getPlayers().add(player3);
        board.getPlayers().add(player4);

        board.initializeBoard(numPlayers);

        assertNotNull(board.getUpperBuildingRow()); //fila edifici non nulla
        assertFalse(board.getUpperBuildingRow().isEmpty()); //fila edifici non vuota

        assertEquals(5, board.getLowerCardsRow().size());
        assertEquals(8,board.getUpperCardRow().size());

        assertNotNull(board.getDeck(), "Il mazzo non dovrebbe essere nullo");//mazzo non nullo
        assertFalse(board.getDeck().isEmpty());//mazzo non vuoto

        assertNotNull(board.getTurnOrderCard());//carte inizializzate
        assertNotNull(board.getPath());
    }

}

