package it.polimi.ingsw.Network.RMI;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.ServerConnection;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class ClientRMI extends UnicastRemoteObject implements
        RemoteClientInterface,ServerConnection, Runnable {

    private ClientController clientController;
    private VirtualServer server;
    private final String localHost;
    private final int port;
    private final String serverName;
    private String username;
//

    public ClientRMI(String localHost, int port, String serverName,ClientController clientcontroller) throws RemoteException {
        super();
        this.localHost = localHost;
        this.port = port;
        this.serverName = serverName;
        this.clientController= clientcontroller;
        clientController.setConnection(this);
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void showGameStarted(ArrayList<Player> players, Board board) throws RemoteException {
        clientController.showGameStarted(players, board);
    }
    @Override
    public void showConfirmUsername(String username) throws RemoteException{
        clientController.showConfirmUsername(username);
    }
    @Override
    public void showConfirmTotem(Totem totem) throws RemoteException{
        clientController.showConfirmTotem(totem);
    }
    @Override
    public void showTotemChoiceError(String message) throws RemoteException{
        clientController.showTotemChoiceError();
    }
    @Override
    public void showUsernameError(String message){
        clientController.showUsernameError();
    }
    @Override
    public void  numPlayersChosenError(String message){
        clientController.numPlayersChosenError();
    }
    @Override
    public void showUpdateFirstPlayer(String player) throws RemoteException{
        clientController.showUpdateFirstPlayer();
    }
   @Override
   public void showPickCardError(String message) throws RemoteException{
        clientController.showPickedCardError();
   }

    @Override
    public void showPlayerTurn(String player) throws RemoteException {
        clientController.showPlayerTurn(player);
    }

    @Override
    public void showPickedCard(String playerWhoPicked, boolean row, boolean isBuilding, int index)
            throws RemoteException {
        clientController.showPickedCard(playerWhoPicked, row, isBuilding, index);
    }
    @Override
    public void showTotemMoved(String player, int path)throws RemoteException{
        clientController.showTotemMoved(player, path);
    }

    @Override
    public void showTotemMovedError(String error) throws RemoteException {
        clientController.showTotemMovedError();
    }
    @Override
    public void showLocalUpdateEra(int era) throws RemoteException{
        clientController.showLocalUpdateEra();
    }
    @Override
    public void showLocalReturnToTOC(String player,int index) throws RemoteException{
        clientController.showLocalReturnToTOC(player, index);
    }
    @Override
    public void showUpdateForEvent(Event e) throws RemoteException{
        clientController.showUpdateForEvents();
    }
    @Override
    public void updateNextTurn(Board board) throws RemoteException{
        clientController.showUpdateTurn(board);
    }
    @Override
    public void showAvailableTotems(ArrayList<Totem> availableTotems) throws  RemoteException{
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
    public void showEndGame(String winner, List<PlayerScore> leaderboard) throws RemoteException {
        clientController.showEndGame();
    }
    @Override
    public void showChosenNumPlayers(int numPlayers) throws RemoteException{
        clientController.showNumPlayers(numPlayers);
    }

    //implementazione serverconnection
    @Override
    public void login(String username, Totem chosenTotem) {
        try {
            this.username = username;
            server.login(username, chosenTotem, this);
        } catch (RemoteException e) {
            clientController.showErrorMessage("RMI error during login.");
        }
    }

    @Override
    public void requestSetNumPlayers(int numPlayer) {
        try {
            server.requestSetNumPlayersManagement(numPlayer, this);
        } catch (RemoteException e) {
            clientController.showErrorMessage("RMI error while setting number of players.");
        }
    }
    @Override
    public void requestPickCard(String localPlayerName, boolean isUpper, boolean isBuilding, int index) {
        try {
            server.requestPickCardManagement(username, isUpper, isBuilding, index);
        } catch (RemoteException e) {
            clientController.showPickedCardError();
        }
    }

    @Override
    public void requestMoveTotem(String username, int chosenPosition) {
        try {
            server.requestMoveTotemManagement(username, chosenPosition);
        } catch (RemoteException e) {
            clientController.showErrorMessage("RMI error while setting totem position.");
        }
    }
    @Override
    public void requestAvailableTotems(String username, ArrayList<Totem> availabletotems){
        try{
            server.requestAvailableTotemsManagement(username, availabletotems);
        }catch(RemoteException e){
            clientController.showErrorMessage("RMI error while asking Totem.");
        }
    }








    @Override
    public void leave() {
        try {
            server.leave();
        } catch (RemoteException e) {
            clientController.showErrorMessage("RMI error while leaving the game.");
        }
    }

    @Override
    public void run() {
        try {
            Registry registry = LocateRegistry.getRegistry(localHost, port);
            server = (VirtualServer) registry.lookup(serverName);

            server.connect(this);
            System.out.println("Connected to RMI server.");

        } catch (Exception e) {
            System.out.println("Cannot connect to RMI server.");
            e.printStackTrace();
        }
    }


}


