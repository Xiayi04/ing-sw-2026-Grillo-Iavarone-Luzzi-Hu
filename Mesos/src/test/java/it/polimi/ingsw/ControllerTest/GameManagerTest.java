package it.polimi.ingsw.ControllerTest;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GameManagerTest {
    Player player1 = new Player("beppe", Totem.ORANGE,10,null);
    Player player2 = new Player("mattia", Totem.BLACK,0,null);
    Player player3 = new Player("xia", Totem.WHITE,0,null);
    Player player4 = new Player("denise", Totem.BLUE,0,null);
    Player player5 = new Player("juan", Totem.YELLOW,0,null);


    @Test
    public void gameManagerTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        GameManager gm = new GameManager(players, players.size(), new Board());
        gm.startGame();
        assertEquals(players.size(),gm.getBoard().getPlayers().size());

        gm.positionPhase();

        gm.resolvePosition(player1.getName(), 1);
        gm.resolvePosition(player2.getName(), 1);
        gm.resolvePosition(player3.getName(), 1);
        gm.resolvePosition(player4.getName(), 1);
        gm.resolvePosition(player5.getName(), 1);

        gm.resolvePosition(player1.getName(), 2);
        gm.resolvePosition(player2.getName(), 2);
        gm.resolvePosition(player3.getName(), 2);
        gm.resolvePosition(player4.getName(), 2);
        gm.resolvePosition(player5.getName(), 2);

        gm.resolvePosition(player1.getName(), 3);
        gm.resolvePosition(player2.getName(), 3);
        gm.resolvePosition(player3.getName(), 3);
        gm.resolvePosition(player4.getName(), 3);
        gm.resolvePosition(player5.getName(), 3);

        gm.resolvePosition(player1.getName(), 4);
        gm.resolvePosition(player2.getName(), 4);
        gm.resolvePosition(player3.getName(), 4);
        gm.resolvePosition(player4.getName(), 4);
        gm.resolvePosition(player5.getName(), 4);

        gm.resolvePosition(player1.getName(), 5);
        gm.resolvePosition(player2.getName(), 5);
        gm.resolvePosition(player3.getName(), 5);
        gm.resolvePosition(player4.getName(), 5);
        gm.resolvePosition(player5.getName(), 5);

        gm.resolvePick(player1.getName(),true,false,1);
        gm.resolvePick(player2.getName(),true,false,1);
        gm.resolvePick(player3.getName(),true,false,1);
        gm.resolvePick(player4.getName(),true,false,1);
        gm.resolvePick(player5.getName(),true,false,1);


    }

    @Test
    public void BuyBuildingTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        Board board = new Board();

        Building b = new MultiplicationBuilding(1,6,6,"INVENTOR",2);
        Building b1 = new MultiplicationBuilding(1,8,8,"HUNTER",3);
        Building b2 = new BonusStarBuilding(2,6,4);
        Building b3 = new BonusFood(1,3,3);
        Building b4 = new DoubleBonusBuilding(2,7,0);

        board.getUpperBuildingRow().add(b);//indice 0 (sopra)
        board.getUpperBuildingRow().add(b1);//indice 1(sopra)
        board.getLowerBuildingRow().add(b2);//indice 0 (sotto)
        board.getUpperBuildingRow().add(b3);//indice 2 (sopra)
        board.getLowerBuildingRow().add(b4);//Indice 1 (sotto)

        GameManager gm = new GameManager(players, players.size(), board);
        gm.buyBuilding(player1, true, 0); // 0 è l'indice dove abbiamo aggiunto b

        // Verifica che l'edificio sia ora nella lista del giocatore
        assertTrue(player1.getBuilding().contains(b));
        // Verifica che l'edificio sia rimosso dalla board
        assertFalse(board.getUpperBuildingRow().contains(b));
        // Verifica che il cibo sia stato scalato
        assertEquals(4, player1.getFood());

    }
    @Test
    public void PurchaseBuildingWithDiscountTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        Character c = new Builder(1,"CHARACTER",2,"BUILDER",2,0);
        Building b = new MultiplicationBuilding(1,6,6,"INVENTOR",2);
        Board board = new Board();
        board.getUpperCardRow().add(c);
        board.getLowerBuildingRow().add(b);

        GameManager gm = new GameManager(players,players.size(),board);
        gm.takeCharacter(player1,true,0);
        gm.buyBuilding(player1,false,0);

        assertTrue(player1.getTribeCard().contains(c));
        assertFalse(board.getUpperCardRow().contains(c));
        assertEquals(6,player1.getFood());
        assertTrue(player1.getBuilding().contains(b));
        assertFalse(board.getLowerBuildingRow().contains(b));

    }

    @Test
    public void PurchaseBuildingFailureTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player2);

        Building b = new BonusFood(1,3,3);
        Board board = new Board();
        board.getUpperBuildingRow().add(b);

        GameManager gm = new GameManager(players,players.size(),board);
        gm.buyBuilding(player2,true,0);

        assertFalse(player2.getBuilding().contains(b));
        assertTrue(board.getUpperBuildingRow().contains(b));
        assertEquals(0, player2.getFood());

    }

    @Test
    public void TakeCharacterTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        Character h = new Hunter(1,"CHARACTER",2,"HUNTER",true);
        Character h1 = new Hunter(1,"CHARACTER",2,"HUNTER",false);
        Character i = new Inventor(1,"CHARACTER",2,"INVENTOR","boat");
        Character s= new Shaman(2,"CHARACTER",2,"SHAMAN",1);
        Character p = new Picker(2,"CHARACTER",2,"PICKER");

        Board board = new Board();
        board.getUpperCardRow().add(h);
        board.getUpperCardRow().add(i);
        board.getUpperCardRow().add(s);
        board.getLowerCardsRow().add(h1);
        board.getLowerCardsRow().add(p);

        GameManager gm = new GameManager(players,players.size(),board);
        gm.takeCharacter(player1,true,0);

        assertTrue(player1.getTribeCard().contains(h));
        assertFalse(board.getUpperCardRow().contains(h));
    }
}
