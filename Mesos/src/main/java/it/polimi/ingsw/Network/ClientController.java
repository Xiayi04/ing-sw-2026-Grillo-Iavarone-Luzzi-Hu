package it.polimi.ingsw.Network;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.RMI.ClientRMI;
import it.polimi.ingsw.Network.Socket.Client.SocketClient;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
// riferimento all'if che manda messaggi al server
// ho bisogno dei metodi chiamati dalla gui
// e quelli che aggiornano la gui

public class ClientController implements AutoCloseable{
    private  GraphicInterface view;
    private  ServerConnection serverConnection;
    private  String tmpUsername = null;
    private  Totem tmpTotem = null;
    private  final Object tmpLock = new Object();
    private final Object BoardLock = new Object();
    private  Board currentBoard;
    private String localPlayerName = null;
    private Totem localTotem = null;
    String rowName ;
    String cardType;
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    public void setView(GraphicInterface view){
        this.view = view;
    }

    public void setConnection(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public void setServerConnection(boolean isRMI) {
        try {
            if (isRMI) {
                ClientRMI clientRMI = new ClientRMI("localhost", 1234, "VirtualServer",this);
                clientRMI.setClientController(this);
                clientRMI.run();

                this.serverConnection = clientRMI;
            } else {
                SocketClient socketClient = new SocketClient(this);
                this.serverConnection = socketClient;
            }
        } catch (Exception e) {
            view.showError("Connection error.");
            e.printStackTrace();
        }
    }
    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void setLocalPlayerName(String localPlayerName) {
        this.localPlayerName = localPlayerName;
    }

    public Player getLocalPlayer() {
        for(Player p : currentBoard.getPlayers()){
            if(p.getName().equals(localPlayerName)){
                return p;
            }
        }
        return null;
    }
    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

  //choice of the number of players
    public void setNumPlayers(int numPlayers) {
        while (numPlayers < 2 || numPlayers > 5) {
            view.showError("Number of Players is wrong,try again ");
            view.askNumToPlayer();
        }
        serverConnection.requestSetNumPlayers(numPlayers);
    }
    public void showNumPlayers(int numPlayers){
        view.showMessage("Number of Players "+ numPlayers);
    }
    public void numPlayersChosenError() {
        view.showError("players' num not correct");
    }



    //notifica dal server
    public void showGameStarted(ArrayList<Player> players, Board board) {
        view.showMessage("The game started.");
    }
    public void showUpdateFirstPlayer() {
        view.askNumToPlayer();
    }
    public void showAvailableTotems(ArrayList<Totem> availableTotems) {
        view.showAvailableTotems(availableTotems);
    }


    public void showLocalUpdateEra() {
        view.showMessage("era updated");
    }
    public void showPlayerTurn(String player) {
        view.showMessage("It's your turn.");
    }

    public void foodUpdated(String username, int foodUpdated){
        view.showMessage(username + "'s food + "+foodUpdated);
    }
    public void updatePlayerPP(String username, int PPUpdated){
        view.showMessage(username + "'s PP updated: " + PPUpdated);
    }

    public void showUpdateForEvents() {
        view.showMessage("Round ended, event resolve phase");
    }
    public void showError(String message) {
        view.showError(message);
    }
    public void showUpdateTurn(Board board) {
        currentBoard = board;
        view.showNextRound();
    }

    public void showEndGame() {
        view.showMessage("Game ended.");
    }

    //Login management

    public void setTmpUsername(String username) {
        if(serverConnection==null  || username.isBlank()){
            view.showError("You can't set a username at this moment, please try again");
            return;
        }

        if(localPlayerName!=null){
            view.showError("You already chose a valid username");
            return;
        }
            synchronized (tmpLock){
                if (username.isBlank()) {
                    view.showError("Username cannot be empty");
                }
                tmpUsername = username;
                checkForLogin();
            }
    }
    public void showConfirmUsername(String username){
        if(localPlayerName!=null){
            throw new RuntimeException("Trying to confirm an already confirmed username");
        }
        this.localPlayerName = username;
        view.showMessage("Username confirmed: "+ username);
    }
    public void showUsernameError() {
        view.showError("Username choice is wrong");
    }

    public void setTmpTotem(Totem totem) {
        if(serverConnection==null){
            view.showError("You can't choose a totem at this moment, please try again later");
            return;
        }

        if(localTotem != null){
            view.showError("You already choose a totem");
            return;
        }

            synchronized (tmpLock){
                if (totem == null) {
                    view.showError("Totem is null");
                }
                tmpTotem = totem;
                checkForLogin();
            }

    }
    public void showConfirmTotem(Totem totem){
        if(localTotem!=null){
            throw new RuntimeException("Trying to confirm an already confirmed Totem");
        }
        localTotem = totem;
        view.showMessage("Totem chosen is confirmed"+ totem);
    }
    public void showTotemChoiceError() {
        view.showError("Totem chosen not available");
    }

    public void requestLocalAvailableTotems(){
        if(serverConnection==null){
            view.showError("The connection isn't established, please try again later");
            return;
        }

        if(localTotem!=null){
            view.showError("You already chose a valid Totem");
            return;
        }

        serverConnection.requestAvailableTotems();
    }



    public void checkForLogin(){
        synchronized (tmpLock){
            if(serverConnection==null){
                view.showError("Server Connection not established");
                return;
            }

            if(tmpTotem == null || tmpUsername==null){
                return;
            }
            //case: in the first login the username was confirmed but not the totem
            if(localTotem==null && localPlayerName!=null){
                serverConnection.login(localPlayerName, tmpTotem);
                tmpTotem = null;
                return;
            }
            //case: int the first login the totem was confirmed but not the totem
            if(localPlayerName==null && localTotem!=null){
                serverConnection.login(tmpUsername,localTotem);
                tmpUsername = null;
                return;
            }

            serverConnection.login(tmpUsername, tmpTotem);
            tmpUsername = null;
            tmpTotem = null;
        }
    }



    /*public void addPlayerToLocalBoard(String newPlayer) {
        synchronized (currentBoard.getPlayers()) {
            if(currentBoard.getPlayers().contains(newPlayer)){
                throw new RuntimeException("Player already exists");
            }
            currentBoard.getPlayers().add(newPlayer);
        }
    }*/

    public void confirmNumPlayers(int num) {
        view.showMessage("Number of players: "+num);
    }


    //picking management, output and input
    public void requestLocalPickCard(boolean isUpper, boolean isBuilding, int index) {
        if (serverConnection==null || localPlayerName!=null ) {
            view.showError("Command not available in this moment");
            return;
        }
        serverConnection.requestPickCard(localPlayerName,isUpper, isBuilding, index);
    }

    public void showPickedCard(String playerName,boolean isUpper , boolean isBuilding, int index) {
        Card pickedCard;
        synchronized (BoardLock){
            pickedCard = currentBoard.pickCard(isUpper, isBuilding, index);
        }
        Player playerWhoPicked = getPlayerByName(playerName);
        if(playerWhoPicked==null){
            throw new RuntimeException("Player "+playerName+" not found");
        }
        synchronized (playerWhoPicked.getTribeCard()) {
            try {
                playerWhoPicked.getTribeCard().add((Character) pickedCard);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        view.pickCard(playerName,isUpper, isBuilding, index);
    }

    public void showPickedCardError() {
        view.showError("You can't pick this card");
    }


    public Player getPlayerByName(String playerName) {
        synchronized (currentBoard.getPlayers()) {
            for (Player player : currentBoard.getPlayers()) {
                if (playerName.equals(player.getName())) {
                    return player;
                }
            }
        }
        return null;
    }

    //totem->offerCard
    public void requestLocalMoveTotem(int index){
        if(serverConnection==null || localPlayerName==null){
            view.showError("Command not available in this moment");
        }

        if(index<0){
            view.showError("Invalid index");
        }
        serverConnection.requestMoveTotem(localPlayerName,index);
    }

    public void showTotemMoved(String playerName, int index){
        Player player = getPlayerByName(playerName);
        if(player==null || index < 0){
            throw new RuntimeException("Player "+playerName+" not found");
        }

        synchronized (currentBoard.getTurnOrderCard()){

            if(currentBoard.getTurnOrderCard() != null  ){

                if(!currentBoard.getTurnOrderCard().getOrder().getFirst().getName().equals(playerName)){
                    throw new RuntimeException("Player "+playerName+" is already in this moment");
                }
            }
            player = currentBoard.getTurnOrderCard().getOrder().removeFirst();
        }
        synchronized (currentBoard.getPath()){
            boolean notPresent = currentBoard.getPath().stream()
                    .filter(OfferCard::isOccupied)
                    .map(OfferCard::getOccupiedBy)
                    .findAny()
                    .isEmpty();
            if(notPresent){
                throw new RuntimeException("Player "+playerName+" is already in this moment");
            }
            if(!currentBoard.getPath().get(index).isOccupied()){
                currentBoard.getPath().get(index).setOccupiedBy(player);
            }
        }
        view.moveTotem(playerName,index);
        view.showMessage("Player"+ playerName + "moved to: "+ index);
    }

    public void showTotemMovedError() {
        view.showError("You can't move this totem");
    }
    public void leave() {
        serverConnection.leave();
        ClientMain.terminateClient();
    }
    public void showLocalReturnToTOC(String playerName, int indexTOC) {
        Player player = getPlayerByName(playerName);

        if (player == null) {
            view.showError("Player " + playerName + " not found.");
            return;
        }
        synchronized (currentBoard.getPath()) {
            for (OfferCard offerCard : currentBoard.getPath()) {
                if (offerCard.isOccupied()
                        && offerCard.getOccupiedBy().getName().equals(playerName)) {

                    offerCard.setOccupiedBy(null);
                    break;
                }
            }
        }
        synchronized (currentBoard.getTurnOrderCard()) {
            currentBoard.getTurnOrderCard().getOrder().add(player);
        }

        view.showMessage("Player " + playerName + " returned to Turn Order Card position " + indexTOC);
    }
    //Closing Procedure

    /**
     * Called when the user wants to quit, when the game is finished or when the server
     * decides to close the game before its natural end.
     * It closes the connection and the view.
     */
    @Override
    public synchronized void close() {
        if(!isRunning.get()){
            return;
        }
        isRunning.set(false);
        serverConnection.close();
        view.close();
    }
    public void refuseOfConnection(){
            view.showError("Connection refused: too many players.");
            ClientMain.terminateClient();
    }
    public void handleForcedEndGame(){
        view.showError("Connection refused: someone left");
        ClientMain.terminateClient();
    }
    public void handleEndGameNormally(String winner, List<PlayerScore> leaderboard){
        view.showMessage("Connection closed, the game ended successfully");
        view.showEndGameSuccessfully(winner, leaderboard);
        ClientMain.terminateClient();
    }
}
//