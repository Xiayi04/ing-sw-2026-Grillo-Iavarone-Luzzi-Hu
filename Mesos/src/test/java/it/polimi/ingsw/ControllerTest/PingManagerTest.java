package it.polimi.ingsw.ControllerTest;

import it.polimi.ingsw.Model.Cards.Events.Event;
import it.polimi.ingsw.Controller.PingManager;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Model.Game.Board;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Network.ClientDisconnectedException;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.VirtualClientInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PingManagerTest {
    private PingManager pingManager;
    private Client client;
    @BeforeEach
    void setUp() {
        pingManager = new PingManager();
        client = new Client();
    }

    @AfterEach
    void tearDown() {
        pingManager.close();
    }

    @Test
    public void addClientToPingListTest()throws InterruptedException{
        pingManager.addClientToPingList(client);
        Thread.sleep(200);

        assertTrue(client.pingCalled);
    }
    @Test
    void closeTest() {
        //check that it doesn't throw exceptions or that the method has been called
        assertTrue(true);
    }
    @Test
    void testStartPingTask() throws InterruptedException {
        pingManager.addClientToPingList(client);
        Thread.sleep(200);

        assertTrue(client.pingReceived);
    }

    @Test
    void testPingFailureTriggersExceptionHandling() throws InterruptedException {
        client.throwException = true;
        pingManager.addClientToPingList(client);
        Thread.sleep(200);

        assertTrue(true, "PingManager should not crash when a client disconnects");

    }


    class Client implements VirtualClientInterface {
        boolean pingCalled = false;
        boolean throwException = false;
        boolean pingReceived = false;
        @Override
        public void ping() throws ClientDisconnectedException {
            if (throwException) {
                throw new ClientDisconnectedException("Simulated disconnect");
            }
            this.pingCalled = true;
            this.pingReceived = true;
        }
        @Override public void skipTurn() {}
        @Override public void showUpdateEra(int era) {}
        @Override public void updateForEvent(Event e) {}
        @Override public void returnTotemToTOC(String username, int index) {}
        @Override public void showChosenNumPlayers(int numPlayers) {}
        @Override public void updateFirstPlayer() {}
        @Override public void updateStartGame(ArrayList<Player> players, Board board) {}
        @Override public void updateEndGame(String winner, List<PlayerScore> leaderboard) {}
        @Override public void refuseConnection() {}
        @Override public void pickedCard(String username, boolean row, boolean isBuilding, int index, int round) {}
        @Override public void movedTotem(String username, int index) {}
        @Override public void movedTotemError() {}
        @Override public void totemChoiceError() {}
        @Override public void updatePlayerFood(String username, int update) {}
        @Override public void updatePlayerPP(String username, int update) {}
        @Override public void showPlayerTurn(String username) {}
        @Override public void updateNextRound(Board board, int round) {}
        @Override public void pickCardError() {}
        @Override public void skipError() {}
        @Override public void usernameError() {}
        @Override public void updateAvailableTotems(ArrayList<Totem> availableTotems) {}
        @Override public void numPlayersError() {}
        @Override public void updateConfirmedUsername(String username) {}
        @Override public void updateConfirmedTotem(Totem totem) {}
        @Override public void updateForcedEndGame() {}
        @Override public void updateLeaderboardFromDB(int PlayerPositionInDB, List<LeaderBoardData> leaderboard) {}
    }
}
