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
                ClientRMI clientRMI = new ClientRMI("localhost", 1099, "VirtualServer");
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

    public void setNumPlayers(int numPlayers) {
        while (numPlayers < 2 || numPlayers > 5) {
            view.showError("Number of Players is wrong,try again ");
            numPlayers = view.askNumToPlayer();
        }
        serverConnection.setNumPlayers(numPlayers);
    }

    public void chooseTotemPosition(ArrayList<OfferCard> path) {

        if (path == null || path.isEmpty()) {
            view.showError("Offer card path not available.");
            return;
        }

        int chosenPosition = view.askPosition(path);

        while (chosenPosition < 0 || chosenPosition >= path.size()) {
            view.showError("Position not valid.");
            chosenPosition = view.askPosition(path);
        }

        serverConnection.setTotemPosition(localPlayerName, chosenPosition);
    }

    public void PickCard(boolean isUpper, boolean isBuilding, int index) {

        if (isUpper) {
            if (!(index >= 0 && index < currentBoard.getUpperCardRow().size())) {
                view.showError("Card index not valid.");

            } else {
                if (!(index >= 0 && index < currentBoard.getLowerCardsRow().size())) {
                    view.showError("Card index not valid.");
                    return;
                }
            }
            serverConnection.requestPickCard(localPlayerName, isUpper, isBuilding, index);
        }
    }

    public void leave() {
        serverConnection.leave();
    }

    //aggiornano la gui


    public void showStartGame() {
        view.showMessage("The game started.");
    }

    public void showPlayerTurn(Player player) {
        view.showMessage("It's your turn.");
    }

    public void addedPlayer(Player player) {
        view.showMessage("New player added: "+player);
    }

    public void showPickedCard(Player playerWhoPicked, boolean row, boolean isBuilding, int index){
        rowName = row? "upperRow" : "lowerRow";
        cardType = isBuilding ? "building" : "character";
        view.showMessage(playerWhoPicked + "has taken" + cardType + "from" + rowName);
    }



    public void movedTotem(Player player, int path){
        view.showMessage("Player"+ player + "moved to: "+ path);
    }
    public void foodUpdated(Player player, int foodUpdated){
        view.showMessage(player + "'s food + "+foodUpdated);
    }
    public void updatePlayerPP(Player player, int PPUpdated){
        view.showMessage(player + "'s PP updated: " + PPUpdated);
    }

    public void updateBoardStatus(Board updatedBoard) {
        this.currentBoard = updatedBoard;
        view.updateBoardStatus(currentBoard);
    }
    public void showErrorMessage(String message) {
        view.showError(message);
    }


    public void showEndGame() {
        view.showMessage("Game ended.");
    }
    
    //Login management

    public void setTmpUsername(String username) {
        if(serverConnection==null){
            view.showErrorMessage("You can't set a username at this moment, please try again");
            return;
        }
            synchronized (tmpLock){
                if (username == null || username.isBlank()) {

                }
                tmpUsername = username;
                checkForLogin();
            }
    }

    public void setTmpTotem(Totem totem) {
        if(serverConnection==null){
            view.showErrorMessage("You can't choose a totem at this moment, please try again later");
            return;
        }

            synchronized (tmpLock){
                if (tmpTotem == null) {

                }
                tmpTotem = totem;
                checkForLogin();
            }

    }

    public void checkForLogin(){
        synchronized (tmpLock){
            if(tmpTotem == null && tmpUsername==null){
                return;
            }
            serverConnection.login(tmpUsername, tmpTotem);
            tmpUsername = null;
            tmpTotem = null;
        }
    }

    public void confirmTotem(Totem totem) {
        view.showMessage("Totem confirmed: "+totem);
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

    public void askNumPlayers() {
        view.showMessage("*Insert the number of players that will join this game using-> players:'num of players'");
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
    }

    public void showTotemMovedError() {
        view.showError("You can't move this totem");
    }

}
