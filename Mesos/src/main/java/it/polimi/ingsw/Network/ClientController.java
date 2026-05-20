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
// riferimento all'if che manda messaggi al server
// ho bisogno dei metodi chiamati dalla gui
// e quelli che aggiornano la gui

public class ClientController {
    private  GraphicInterface view;
    private  ServerConnection serverConnection;
    private  String tmpUsername = null;
    private  Totem tmpTotem = null;
    private  final Object tmpLock = new Object();
    private final Object BoardLock = new Object();
    private  Board currentBoard;
    private String localPlayerName;
    private Totem localTotem;
    String rowName ;
    String cardType;

    public void setView(GraphicInterface view){
        this.view = view;
    }

    public void setConnection(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public void setServerConnection(boolean isRMI) {
        try {
            if (isRMI) {
                ClientRMI clientRMI = new ClientRMI("localhost", 1099, "VirtualServer",this);
                clientRMI.setClientController(this);
                clientRMI.run();

                this.serverConnection = clientRMI;
            } else {
                SocketClient socketClient = new SocketClient(this);
                this.serverConnection = socketClient;
            }
        } catch (Exception e) {
            view.showErrorMessage("Connection error.");
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
            numPlayers = view.askNumToPlayer();
        }
        serverConnection.setNumPlayers(numPlayers);
    }
    public void showNumPlayers(int numPlayers){
        view.showMessage("Number of Players "+ numPlayers);
    }
    public void numPlayersChosenError() {
        view.showError("players' num not correct");
    }



    //notifica dal server
    public void showGameStarted() {
        view.showMessage("The game started.");
    }
    public void showUpdateFirstPlayer() {
        view.showMessage("You are the first player.");
    }
    public void showAvailableTotems(ArrayList<Totem> availableTotems) {
        view.showAvailableTotems(availableTotems);
    }


    public void showLocalUpdateEra() {
        view.showMessage("era updated");
    }
    public void showPlayerTurn(Player player) {
        view.showMessage("It's your turn.");
    }
    public void showLocalReturnToTOC() {
        view.showMessage("totem returned to turn order card");
    }

    public void foodUpdated(Player player, int foodUpdated){
        view.showMessage(player + "'s food + "+foodUpdated);
    }
    public void updatePlayerPP(Player player, int PPUpdated){
        view.showMessage(player + "'s PP updated: " + PPUpdated);
    }

    public void showUpdateForEvents() {
        view.showMessage("Round ended, event resolve phase");
    }
    public void showErrorMessage(String message) {
        view.showError(message);
    }
    public void showUpdateNextTurn() {
        view.showMessage("New turn started");
    }

    public void showEndGame() {
        view.showMessage("Game ended.");
    }

    //Login management

    public void setTmpUsername(String username) {
        if(serverConnection==null  || username.isBlank()){
            view.showErrorMessage("You can't set a username at this moment, please try again");
            return;
        }
            synchronized (tmpLock){
                if (username == null || username.isBlank()) {
                    view.showError("Username cannot be empty or repetitive");
                }
                tmpUsername = username;
                checkForLogin();
            }
    }
    public void showConfirmUsername(String username){
        this.localPlayerName = username;
        view.showMessage("Username confirmed: "+ username);
    }
    public void showUsernameError() {
        view.showError("Username choice is wrong");
    }

    public void setTmpTotem(Totem totem) {
        if(serverConnection==null){
            view.showErrorMessage("You can't choose a totem at this moment, please try again later");
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
        view.showMessage("Totem chosen is confirmed"+ totem);

    }
    public void showTotemChoiceError() {
        view.showError("Totem chosen not available");
    }



    public void checkForLogin(){
        synchronized (tmpLock){
            if(tmpTotem == null || tmpUsername==null){
                return;
            }
            serverConnection.login(tmpUsername, tmpTotem);
            tmpUsername = null;
            tmpTotem = null;
        }
    }






    public void addPlayerToLocalBoard(Player newPlayer) {
        synchronized (currentBoard.getPlayers()) {
            if(currentBoard.getPlayers().contains(newPlayer)){
                throw new RuntimeException("Player already exists");
            }
            currentBoard.getPlayers().add(newPlayer);
        }
    }

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
        serverConnection.setTotemPosition(localPlayerName,index);
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
    }

}
