package it.polimi.ingsw.ControllerTest;

import it.polimi.ingsw.Model.Cards.Events.Event;
import it.polimi.ingsw.Controller.Lobby;
import it.polimi.ingsw.Controller.LobbyManager;
import it.polimi.ingsw.Controller.TempPlayer;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Model.Game.Board;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.VirtualClientInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LobbyTest {
    private Lobby lobby;
    private Client Client;
    private FakeServerController fakeServerController;
    @BeforeEach
    void setUp() {
        Lobby.isNumPlayersSet.set(false);
        fakeServerController = new FakeServerController();
        lobby = new Lobby(fakeServerController);
        lobby.numPlayers.set(0);
    }
    @Test
    void addFirstClientTest() {
        Client c1 = new Client();
        lobby.addClient(c1);

        assertEquals(1, lobby.getTempPlayers().size());
        assertTrue(c1.firstPlayerUpdated);
        assertTrue(fakeServerController.initCalled);
    }
    @Test
    void duplicateClientNotAddedTest() {
        Client c1 = new Client();

        lobby.addClient(c1);
        lobby.addClient(c1);

        assertEquals(1, lobby.getTempPlayers().size());
    }
    @Test
    void refuseConnectionWhenFullTest() {
        Lobby.isNumPlayersSet.set(true);
        lobby.numPlayers.set(2);
        Client c1 = new Client();
        Client c2 = new Client();
        Client c3 = new Client();
        lobby.addClient(c1);
        lobby.addClient(c2);
        lobby.addClient(c3);

        assertTrue(c3.refused);
        assertEquals(2, lobby.getTempPlayers().size());
    }
    @Test
    void getAvailableTotemsInitialTest() {
        ArrayList<Totem> available = lobby.getAvailableTotems();

        assertEquals(5, available.size());
        assertTrue(available.contains(Totem.BLACK));
        assertTrue(available.contains(Totem.ORANGE));
        assertTrue(available.contains(Totem.YELLOW));
        assertTrue(available.contains(Totem.WHITE));
        assertTrue(available.contains(Totem.BLUE));
    }
    @Test
    void getAvailableTotemsTest() {
        Client c1 = new Client();
        lobby.addClient(c1);
        lobby.getTempPlayerByClient(c1).setTempPlayerTotem(Totem.BLACK);
        ArrayList<Totem> available = lobby.getAvailableTotems();

        assertFalse(available.contains(Totem.BLACK));
        assertEquals(4, available.size());
        assertTrue(available.contains(Totem.BLUE));
    }
    @Test
    void getTempPlayerByClientFoundTest() {
        Client c1 = new Client();
        lobby.addClient(c1);
        TempPlayer foundPlayer = lobby.getTempPlayerByClient(c1);

        assertNotNull(foundPlayer, "The player exists in the lobby");
        assertEquals(c1, foundPlayer.getClient());
    }

    @Test
    void getTempPlayerByClientNotFoundTest() {
        Client c1 = new Client();
        TempPlayer foundPlayer = lobby.getTempPlayerByClient(c1);

        assertNull(foundPlayer, "The player is not present in the lobby");
    }
    @Test
    void sendAvailableColorsTest() {
        Client c1 = new Client();
        lobby.addClient(c1);
        lobby.getTempPlayerByClient(c1).setTempPlayerTotem(Totem.BLACK);
        lobby.sendAvailableColors(c1);

        assertNotNull(c1.totems, "The list of totems has not been sent");
        assertEquals(4, c1.totems.size());
        assertFalse(c1.totems.contains(Totem.BLACK));
    }
    @Test
    void checkMoreThenEnoughPlayersNotSetTest() {
        Lobby.isNumPlayersSet.set(false);
        Client c1 = new Client();
        Client c2 = new Client();
        lobby.addClient(c1);
        lobby.addClient(c2);
        lobby.checkMoreThenEnoughPlayers();

        assertFalse(c1.refused);
        assertFalse(c2.refused);
    }

    @Test
    void checkMoreThenEnoughPlayersTest() {
        Lobby.isNumPlayersSet.set(true);
        lobby.numPlayers.set(2);
        Client c1 = new Client();
        Client c2 = new Client();
        lobby.addClient(c1);
        lobby.addClient(c2);
        lobby.checkMoreThenEnoughPlayers();

        assertFalse(c1.refused);
        assertFalse(c2.refused);
    }

    @Test
    void checkMoreThenEnoughPlayersExcessTest() {
        Lobby.isNumPlayersSet.set(true);
        lobby.numPlayers.set(3);
        Client c1 = new Client();
        Client c2 = new Client();
        Client c3 = new Client();
        Client c4 = new Client();
        lobby.addClient(c1);
        lobby.addClient(c2);
        lobby.addClient(c3);
        lobby.addClient(c4);
        lobby.checkMoreThenEnoughPlayers();
        assertFalse(c1.refused);
        assertFalse(c2.refused);
        assertFalse(c3.refused);
        assertTrue(c4.refused);
    }
    @Test
    void addUsernameTest() {
        Client c1 = new Client();
        lobby.addUsername("TestUser", c1);

        assertEquals("TestUser", fakeServerController.username);
        assertEquals(c1, fakeServerController.client);
    }

    @Test
    void addTotemTest() {
        Client c1 = new Client();
        Totem t = Totem.BLUE;
        lobby.addTotem(t, c1);

        assertEquals(t, fakeServerController.totem);
        assertEquals(c1, fakeServerController.client);
    }

    @Test
    void setNumPlayersTest() {
        Client c1 = new Client();
        Integer num = 3;
        lobby.setNumPlayers(num, c1);

        assertEquals(num, fakeServerController.numPlayers);
        assertEquals(c1, fakeServerController.client);
    }


    class Client implements VirtualClientInterface {
        boolean refused = false;
        boolean firstPlayerUpdated = false;
        ArrayList<Totem> totems;
        @Override public void skipTurn() {}
        @Override public void showUpdateEra(int era) {}
        @Override public void updateForEvent(Event e) {}
        @Override public void returnTotemToTOC(String username, int index) {}
        @Override public void showChosenNumPlayers(int numPlayers) {}
        @Override public void updateFirstPlayer() {firstPlayerUpdated = true;}
        @Override public void updateStartGame(ArrayList<Player> players, Board board) {}
        @Override public void updateEndGame(String winner, List<PlayerScore> leaderboard) {}
        @Override public void refuseConnection() {refused = true;}
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
        @Override public void updateAvailableTotems(ArrayList<Totem> availableTotems) { this.totems = availableTotems; }
        @Override public void numPlayersError() {}
        @Override public void updateConfirmedUsername(String username) {}
        @Override public void updateConfirmedTotem(Totem totem) {}
        @Override public void ping() {}
        @Override public void updateForcedEndGame() {}
        @Override public void updateLeaderboardFromDB(int PlayerPositionInDB, List<LeaderBoardData> leaderboard) {}
    }
    class FakeServerController implements LobbyManager {
        boolean initCalled = false;
        String username;
        Totem totem;
        Integer numPlayers;
        VirtualClientInterface client;
        public FakeServerController() { super(); }

        @Override public void checkUsername(String username, VirtualClientInterface clientInterface) {
            this.client = clientInterface;
            this.username = username;
        }
        @Override public void checkTotem(Totem totem, VirtualClientInterface client) {
            this.totem = totem;
            this.client = client;
        }
        @Override public void checkSetNumPlayers(int numPlayers, VirtualClientInterface client) {
            this.numPlayers = numPlayers;
            this.client = client;
        }
        @Override public void connectionInitializer(VirtualClientInterface clientInterface) {this.initCalled = true;}
    }
}
