package it.polimi.ingsw.Network.RMI;
import it.polimi.ingsw.Model.Cards.Events.Event;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Model.Game.Board;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.ClientMain;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.ServerConnection;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class ClientRMI extends UnicastRemoteObject implements
        RemoteClientInterface,ServerConnection, Runnable, AutoCloseable {

    private ClientController clientController;
    private VirtualServer server;
    private final String ipAddress;
    private final int port;
    private final String serverName;
    private String username;

    public ClientRMI(String ipAddress, int port, String serverName, ClientController clientcontroller) throws RemoteException {
        super();
        this.ipAddress = ipAddressChoice(ipAddress);
        this.port = port;
        this.serverName = serverName;
        this.clientController = clientcontroller;
        clientController.setConnection(this);
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    private String ipAddressChoice(String ipAddress) {
        if (ipAddress == null || ipAddress.isBlank()) {
            return "localhost";
        }
        return ipAddress;
    }

    @Override
    public void showGameStarted(ArrayList<Player> players, Board board) throws RemoteException {
        clientController.showGameStarted(players, board);
    }

    @Override
    public void showConfirmUsername(String username) throws RemoteException {
        clientController.showConfirmUsername(username);
    }

    @Override
    public void showConfirmTotem(Totem totem) throws RemoteException {
        clientController.showConfirmTotem(totem);
    }

    @Override
    public void showTotemChoiceError(String message) throws RemoteException {
        clientController.showTotemChoiceError();
    }

    @Override
    public void showUsernameError(String message) {
        clientController.showUsernameError();
    }

    @Override
    public void numPlayersChosenError(String message) {
        clientController.numPlayersChosenError();
    }

    @Override
    public void showUpdateFirstPlayer() throws RemoteException {
        clientController.showUpdateFirstPlayer();
    }

    @Override
    public void showPickCardError(String message) throws RemoteException {
        clientController.showPickedCardError();
    }

    @Override
    public void showSkipError(String message) throws RemoteException{
        clientController.showSkipError();
    }

    @Override
    public void showPlayerTurn(String player) throws RemoteException {
        clientController.showPlayerTurn(player);
    }

    @Override
    public void showPickedCard(String playerWhoPicked, boolean row, boolean isBuilding, int index, int round) throws RemoteException {
        clientController.showPickedCard(playerWhoPicked, row, isBuilding, index, round);
    }

    @Override
    public void showTotemMoved(String player, int path) throws RemoteException {
        clientController.showTotemMoved(player, path);
    }

    @Override
    public void showTotemMovedError(String error) throws RemoteException {
        clientController.showTotemMovedError();
    }

    @Override
    public void showLocalUpdateEra(int era) throws RemoteException {
        clientController.showLocalUpdateEra();
    }

    @Override
    public void showLocalReturnToTOC(String player, int index) throws RemoteException {
        clientController.showLocalReturnToTOC(player, index);
    }

    @Override
    public void showUpdateForEvent(Event e) throws RemoteException {
        clientController.showUpdateForEvents(e);
    }

    @Override
    public void updateNextTurn(Board board, int round) throws RemoteException {
        clientController.showUpdateTurn(board, round);
    }

    @Override
    public void showAvailableTotems(ArrayList<Totem> availableTotems) throws RemoteException {
        clientController.showAvailableTotems(availableTotems);
    }

    @Override
    public void showUpdatePlayerFood(String player, int foodUpdated) throws RemoteException {
        clientController.foodUpdated(player, foodUpdated);
    }

    @Override
    public void showUpdatePlayerPP(String player, int ppUpdated) throws RemoteException {
        clientController.updatePlayerPP(player, ppUpdated);
    }



    @Override
    public void ping(){
        try {
            server.ping();
        } catch (Exception e) {
            clientController.showError("Connection error, terminating game");
            ClientMain.terminateClient();
        }
    }

    @Override
    public void showChosenNumPlayers(int numPlayers) throws RemoteException {
        clientController.showNumPlayers(numPlayers);
    }

    //implementation server connection
    @Override
    public void login(String username, Totem chosenTotem) {
        if (server == null) {
            clientController.showError("RMI server not connected yet.");
            return;
        }
        this.username = username;
        new Thread(()->{
            try {
                server.login(username, chosenTotem, this);
            } catch (RemoteException e) {
                ClientMain.terminateClient();
            }
        }).start();


    }

    @Override
    public void requestSetNumPlayers(int numPlayer) {
        try {
            server.requestSetNumPlayersManagement(numPlayer, this);
        } catch (RemoteException e) {
            clientController.showError("RMI error while setting number of players.");
            ClientMain.terminateClient();
        }
    }

    @Override
    public void requestPickCard(String localPlayerName, boolean isUpper, boolean isBuilding, int index, boolean skip) {
        try {
            server.requestPickCardManagement(username, isUpper, isBuilding, index,skip);
        } catch (RemoteException e) {
            ClientMain.terminateClient();
        }
    }

    @Override
    public void requestMoveTotem(String username, int chosenPosition) {
        try {
            server.requestMoveTotemManagement(username, chosenPosition);
        } catch (RemoteException e) {
            clientController.showError("RMI error while setting totem position.");
            ClientMain.terminateClient();
        }
    }

    @Override
    public void requestAvailableTotems() {
        try {
            server.requestAvailableTotemsManagement(this);
        } catch (RemoteException e) {
            clientController.showError("RMI error while asking Totem.");
            ClientMain.terminateClient();
        }
    }

    @Override
    public void leave() {
        try {
            server.leave(this);
        } catch (RemoteException e) {
            clientController.showError("RMI error while leaving the game.");
            ClientMain.terminateClient();
        }
    }
    @Override
    public void run() {
        try {

            Registry registry = LocateRegistry.getRegistry(ipAddress, 1234);
            server = (VirtualServer) registry.lookup( "---MESOS_SERVER---");

            server.connect(this);
            //System.out.println("Connected to RMI server.");


        } catch (Exception e) {
            System.out.println("Cannot connect to RMI server.");
            ClientMain.terminateClient();
        }

    }
    @Override
    public void close() {
        try {
            UnicastRemoteObject.unexportObject(this, true);
        } catch (Exception e) {
            System.out.println("Client RMI already closed");
        }
    }
    @Override
    public void showEndGameSuccessfully(String winner, List<PlayerScore> leaderboard) throws RemoteException {
        clientController.handleEndGameNormally(winner, leaderboard);
    }
    @Override
    public void showForcedEndGame() throws RemoteException {
        clientController.handleForcedEndGame();
        }
    @Override
    public void refuseConnection()throws RemoteException{
        clientController.refuseOfConnection();
    }
    @Override
    public void showLeaderboardFromDB(int playerPositionInDB, List<LeaderBoardData> leaderboard) throws RemoteException {
        clientController.updateLeaderboardFromDB(playerPositionInDB, leaderboard);
    }
}




