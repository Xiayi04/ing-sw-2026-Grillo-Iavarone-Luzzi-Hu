package it.polimi.ingsw.ControllerTest;

import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Controller.TempPlayer;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientDisconnectedException;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.VirtualClientInterface;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TempPlayerTest {
    @Test
    void testTempPlayerFunctionality() {
        Client client = new Client();
        TempPlayer player = new TempPlayer(client);

        assertEquals(client, player.getClient());
        assertNull(player.getName());
        assertNull(player.getTempPlayerTotem());

        player.setName("Player1");
        player.setTempPlayerTotem(Totem.BLUE);

        assertEquals("Player1", player.getName());
        assertEquals(Totem.BLUE, player.getTempPlayerTotem());
    }

    class Client implements VirtualClientInterface {

        @Override public void ping() throws ClientDisconnectedException {}
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


