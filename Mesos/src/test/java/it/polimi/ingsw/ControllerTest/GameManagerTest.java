package it.polimi.ingsw.ControllerTest;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GameManagerTest {
    Player player1 = new Player("beppe", Totem.ORANGE,0,null);
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
}
