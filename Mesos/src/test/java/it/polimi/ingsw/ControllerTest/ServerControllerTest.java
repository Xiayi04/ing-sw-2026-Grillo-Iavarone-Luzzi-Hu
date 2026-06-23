package it.polimi.ingsw.ControllerTest;

import it.polimi.ingsw.Controller.*;
import it.polimi.ingsw.Model.Cards.Events.Event;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Model.Game.Board;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Network.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServerControllerTest {
    private ServerController serverController;
    private GameManagerFake gm;
    private PingManager pingManager;
    private Notifier notifier;
    private Client client1;
    private Client client2;
    private Client client3;
    private Client client4;
    private Client client5;

    @BeforeEach
    public void setup() {
        gm = new GameManagerFake();
        pingManager = new PingManager();
        notifier = new Notifier();
        serverController = new ServerController(gm,notifier);
        client1 = new Client();
        client2 = new Client();
        client3 = new Client();
        client4 = new Client();
        client5 = new Client();
        serverController.getLobby().getTempPlayers().clear();
        serverController.getLobby().IsNumPlayersSet().set(false);
        serverController.getLobby().getNumPlayers().set(0);

    }
    @Test
    void gettersTest() {
        assertEquals(notifier, serverController.getNotifier());
        assertEquals(gm, serverController.getGM());
    }
    @Test
    public  void setterTest(){
        serverController.setNotifier(notifier);
        assertEquals(notifier, serverController.getNotifier());

    }

    @Test
    public void checkUsername_Test(){
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client1));
        serverController.checkUsername("denise", client1);

        assertTrue(client1.usernameConfirmed);
        assertEquals("denise", serverController.getLobby().getTempPlayerByClient(client1).getName());
    }
    @Test
    void checkUsername_DuplicateName_Test() {
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client1));
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client2));
        serverController.checkUsername("xia", client1);
        serverController.checkUsername("xia", client2);

        assertTrue(client2.usernameErrorCalled);
        assertFalse(client1.usernameErrorCalled);
    }

    @Test
    void testCheckUsername_SameUserResubmitsSameName() {
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client1));
        serverController.checkUsername("beppe", client1);

        client1.usernameConfirmed = false;
        serverController.checkUsername("beppe", client1);

        assertFalse(client1.usernameConfirmed);
    }
    @Test
    public void checkTotem_Test(){
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client1));
        serverController.checkTotem(Totem.BLUE, client1);

        assertTrue(client1.totemConfirmed);
        assertEquals(Totem.BLUE, serverController.getLobby().getTempPlayerByClient(client1).getTempPlayerTotem());
    }
    @Test
    public void checkTotemDuplicate_Test() {
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client1));
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client2));

        serverController.checkTotem(Totem.WHITE, client1);
        serverController.checkTotem(Totem.WHITE, client2);

        assertTrue(client1.totemConfirmed);
        assertTrue(client2.totemError);
        assertFalse(client2.totemConfirmed);
    }
    @Test
    public void checkTotemSamePlayerReselectsSame_Test() {

        serverController.getLobby().getTempPlayers().add(new TempPlayer(client1));
        serverController.checkTotem(Totem.BLACK, client1);

        client1.totemConfirmed = false;
        serverController.checkTotem(Totem.BLACK, client1);

        assertFalse(client1.totemConfirmed);
    }
    @Test
    public void checkTotem_RefuseConnectionWhenNoTotemsAvailable_Test() {
        Client client6 = new Client();
        TempPlayer p1 = new TempPlayer(client1);
        p1.setTempPlayerTotem(Totem.BLACK);
        TempPlayer p2 = new TempPlayer(client2);
        p2.setTempPlayerTotem(Totem.BLUE);
        TempPlayer p3 = new TempPlayer(client3);
        p3.setTempPlayerTotem(Totem.WHITE);
        TempPlayer p4 = new TempPlayer(client4);
        p4.setTempPlayerTotem(Totem.YELLOW);
        TempPlayer p5 = new TempPlayer(client5);
        p5.setTempPlayerTotem(Totem.ORANGE);

        serverController.getLobby().getTempPlayers().add(p1);
        serverController.getLobby().getTempPlayers().add(p2);
        serverController.getLobby().getTempPlayers().add(p3);
        serverController.getLobby().getTempPlayers().add(p4);
        serverController.getLobby().getTempPlayers().add(p5);

        serverController.checkTotem(Totem.BLACK, client6);

        assertTrue(client6.refuseConnectionCalled);
    }
    @Test
    public void checkSetNumPlayersSuccess_Test() {
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client1));

        serverController.checkSetNumPlayers(3, client1);

        assertEquals(3, serverController.getLobby().getNumPlayers().get());
        assertTrue(serverController.getLobby().IsNumPlayersSet().get());
        assertTrue(client1.showChosenNumPlayersCalled);
    }

    @Test
    public void checkSetNumPlayers_UnauthorizedUser_Test() {
        TempPlayer p1 = new TempPlayer(client1);
        TempPlayer p2 = new TempPlayer(client2);

        serverController.getLobby().getTempPlayers().add(p1);
        serverController.getLobby().getTempPlayers().add(p2);

        serverController.checkSetNumPlayers(4, client2);

        assertFalse(serverController.getLobby().IsNumPlayersSet().get());
    }

    @Test
    public void CheckSetNumPlayers_InvalidRange_Test() {
        TempPlayer p1 = new TempPlayer(client1);
        serverController.getLobby().getTempPlayers().add(p1);

        serverController.checkSetNumPlayers(6, client1);

        assertTrue(client1.numPlayersErrorCalled);
        assertFalse(serverController.getLobby().IsNumPlayersSet().get());
    }
    @Test
    public void checkStartGameSuccess_Test() {
        serverController.getLobby().IsNumPlayersSet().set(true);
        serverController.getLobby().getNumPlayers().set(3);

        TempPlayer p1 = new TempPlayer(client1);
        TempPlayer p2 = new TempPlayer(client2);
        TempPlayer p3 = new TempPlayer(client3);

        serverController.getLobby().getTempPlayers().add(p1);
        serverController.getLobby().getTempPlayers().add(p2);
        serverController.getLobby().getTempPlayers().add(p3);

        p1.setName("P1");
        p1.setTempPlayerTotem(Totem.BLACK);
        p2.setName("P2");
        p2.setTempPlayerTotem(Totem.BLUE);
        p3.setName("P3");
        p3.setTempPlayerTotem(Totem.ORANGE);

        serverController.checkStartGame();

        assertTrue(gm.startGameCalled, "The game must start when all the conditions are met!");
    }
    @Test
    public void checkStartGameNotSuccess_Test() {
        serverController.checkStartGame();
        assertFalse(gm.startGameCalled);
    }
    @Test
    public void checkStartGame_InvalidRange_Test() {
        serverController.getLobby().IsNumPlayersSet().set(true);
        serverController.getLobby().getNumPlayers().set(3);
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client1));
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client2));

        serverController.checkStartGame();
        assertFalse(gm.startGameCalled);
    }
    @Test
    public void checkStartGamePlayersNotReady_Test() {
        serverController.getLobby().IsNumPlayersSet().set(true);
        serverController.getLobby().getNumPlayers().set(3);
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client1));
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client2));
        serverController.getLobby().getTempPlayers().add(new TempPlayer(client3));

        serverController.checkStartGame();
        assertFalse(gm.startGameCalled);
    }
    @Test
    public void moveTotemRequest_Test() {
        serverController.moveTotemRequest("Player", 3);

        assertTrue(gm.resolvePositionCalled);
        assertEquals("Player", gm.username);
        assertEquals(3, gm.pathIndex);
    }
    @Test
    public void gameInizialized_Test() {
        serverController.getLobby().IsNumPlayersSet().set(true);
        serverController.getLobby().getNumPlayers().set(2);
        ArrayList<TempPlayer> players = new ArrayList<>();
        players.add(new TempPlayer(client1));
        players.add(new TempPlayer(client2));
        serverController.gameInitializer(2,players);

        assertTrue(gm.numPlayersSetCalled);
        assertEquals(2, gm.numPlayers);

        assertTrue(gm.notifierSetCalled);
        assertTrue(gm.startGameCalled);
        assertFalse(gm.getPlayers().isEmpty());

    }
    @Test
    public void genericPick_ShouldCallSkipPick_WhenSkipIsTrue_Test() {
        serverController.genericPick("Player1", false, false, 0, true);

        assertTrue(gm.skipPickCalled);
        assertFalse(gm.resolvePickCalled);
        assertEquals("Player1", gm.username);
    }

    @Test
    public void genericPick_ShouldCallResolvePick_WhenSkipIsFalse_Test() {
        serverController.genericPick("Player1", true, false,3 , false);

        assertFalse(gm.skipPickCalled);
        assertTrue(gm.resolvePickCalled);

        assertEquals("Player1", gm.username);
        assertTrue(gm.isUpper);
        assertFalse(gm.isBuilding);
        assertEquals(3, gm.pathIndex);
    }

    @Test
    public void pushPlayersInGM_Test(){
        serverController.getLobby().IsNumPlayersSet().set(true);
        serverController.getLobby().getNumPlayers().set(2);
        ArrayList<TempPlayer> players = new ArrayList<>();
        TempPlayer p1 = new TempPlayer(client1);
        p1.setName("Player1");
        p1.setTempPlayerTotem(Totem.BLUE);
        TempPlayer p2 = new TempPlayer(client2);
        p2.setName("Player2");
        p2.setTempPlayerTotem(Totem.BLACK);

        players.add(p1);
        players.add(p2);
        serverController.pushPlayersInGM(players);

        assertEquals(2, gm.getPlayers().size());
        assertEquals("Player1", gm.getPlayers().get(0).getName());
    }
    @Test
    public void pushPlayersInGM_DoesNothing_WhenPlayersAlreadyExist_Test() {
        Player player1 = new Player("Player1", Totem.BLACK, 0, null);
        gm.addPlayer(player1);

        ArrayList<TempPlayer> newPlayers = new ArrayList<>();
        TempPlayer p1 = new TempPlayer(client1);
        p1.setName("Player2");
        newPlayers.add(p1);
        serverController.pushPlayersInGM(newPlayers);

        assertEquals(1, gm.getPlayers().size());
        assertEquals("Player1", gm.getPlayers().get(0).getName());
        assertNotEquals("Player2", gm.getPlayers().get(0).getName());
    }
//    @Test
//    public void closeConnectionTest(){
//        PingManagerFake fakePing = new PingManagerFake();
//        NotifierFake fakeNotifier = new NotifierFake();
//        serverController.closeConnections(d);
//
//        assertTrue(fakePing.closeCalled);
//        assertTrue(fakeNotifier.farewellCalled);
//        assertEquals(client1, fakeNotifier.clientReceived);
//    }



    class Client implements VirtualClientInterface {
        boolean usernameConfirmed = false;
        boolean usernameErrorCalled = false;
        public boolean totemConfirmed = false;
        public boolean totemError = false;
        public boolean refuseConnectionCalled = false;
        public boolean showChosenNumPlayersCalled = false;
        public boolean numPlayersErrorCalled = false;

        @Override public void usernameError() {
            this.usernameErrorCalled = true;
        }
        @Override public void updateConfirmedUsername(String username) {
            this.usernameConfirmed = true;
        }
        @Override public void updateConfirmedTotem(Totem totem) {
            this.totemConfirmed = true;
        }
        @Override public void totemChoiceError() {
            this.totemError = true;
        }
        @Override public void refuseConnection() {
            this.refuseConnectionCalled = true;
        }
        @Override public void showChosenNumPlayers(int numPlayers) {
            this.showChosenNumPlayersCalled = true;
        }
        @Override public void numPlayersError() {
            this.numPlayersErrorCalled = true;
        }
        @Override public void ping() throws ClientDisconnectedException {}
        @Override public void skipTurn() {}
        @Override public void showUpdateEra(int era) {}
        @Override public void updateForEvent(Event e) {}
        @Override public void returnTotemToTOC(String username, int index) {}
        @Override public void updateFirstPlayer() {}
        @Override public void updateStartGame(ArrayList<Player> players, Board board) {}
        @Override public void updateEndGame(String winner, List<PlayerScore> leaderboard) {}
        @Override public void pickedCard(String username, boolean row, boolean isBuilding, int index, int round) {}
        @Override public void movedTotem(String username, int index) {}
        @Override public void movedTotemError() {}
        @Override public void updatePlayerFood(String username, int update) {}
        @Override public void updatePlayerPP(String username, int update) {}
        @Override public void showPlayerTurn(String username) {}
        @Override public void updateNextRound(Board board, int round) {}
        @Override public void pickCardError() {}
        @Override public void skipError() {}
        @Override public void updateAvailableTotems(ArrayList<Totem> availableTotems) {}
        @Override public void updateForcedEndGame() {}
        @Override public void updateLeaderboardFromDB(int PlayerPositionInDB, List<LeaderBoardData> leaderboard) {}
    }

    class GameManagerFake extends GameManager {
        public boolean startGameCalled = false;
        public String username;
        public int pathIndex;
        boolean resolvePositionCalled = false;
        public boolean skipPickCalled = false;
        boolean resolvePickCalled = false;
        public boolean isUpper;
        public boolean isBuilding;
        public boolean numPlayersSetCalled = false;
        public boolean notifierSetCalled = false;
        private ArrayList<Player> players = new ArrayList<>();
        public int numPlayers;

        public GameManagerFake() { super(new ArrayList<>() ,5,null); }
        @Override public void startGame() { this.startGameCalled = true; }
        @Override public void resolvePosition ( String username, int pathIndex ) {
            this.username = username;
            this.pathIndex = pathIndex;
            this.resolvePositionCalled = true;
        }
        @Override
        public void skipPick(String username) {
            this.skipPickCalled = true;
            this.username = username;
        }
        @Override
        public void resolvePick(String username, boolean isUpper, boolean isBuilding, int index) {
            this.resolvePickCalled = true;
            this.username = username;
            this.isUpper = isUpper;
            this.isBuilding = isBuilding;
            this.pathIndex = index;
        }
        @Override public void setNumPlayers(int n) {
            this.numPlayers = n;
            this.numPlayersSetCalled = true;
        }

        @Override public void setNotifier(Notifier n) {;
            this.notifierSetCalled = true;
        }
        @Override public ArrayList <Player> getPlayers() { return players; }
        @Override public void addPlayer(Player p) { this.players.add(p);}
    }

//    class PingManagerFake extends PingManager {
//        public boolean closeCalled = false;
//        @Override
//        public void close() {
//            this.closeCalled = true;
//        }
//    }
//
//    class NotifierFake extends Notifier {
//        public boolean farewellCalled = false;
//        public VirtualClientInterface clientReceived;
//        @Override
//        public void sendFarewell(VirtualClientInterface client) {
//            this.farewellCalled = true;
//            this.clientReceived = client;
//        }
//    }
}
