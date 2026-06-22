package it.polimi.ingsw.Network;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.RMI.ClientRMI;
import it.polimi.ingsw.Network.Socket.Client.SocketClient;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
// riferimento all'if che manda messaggi al server
// ho bisogno dei metodi chiamati dalla gui
// e quelli che aggiornano la gui

public class ClientController implements AutoCloseable{
    private volatile GraphicInterface view;
    private  ServerConnection serverConnection;
    private  String tmpUsername = null;
    private  Totem tmpTotem = null;
    private  final Object tmpLock = new Object();
    private final Object BoardLock = new Object();
    private  Board currentBoard;
    private String localPlayerName = null;
    private Totem localTotem = null;
    private final AtomicBoolean isRunning = new AtomicBoolean(true);
    private List<LeaderBoardData> leaderboardDB = null;
    private Integer playerPosition = null;
    private final AtomicBoolean isGameFinished = new AtomicBoolean(false);
    private int localNumberRound=0;
    private final AtomicBoolean pickingPhase = new AtomicBoolean(false);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    //connection management
    private final String serverIP;

    public ClientController(String serverIP) {
        this.serverIP = serverIP;
    }

    public void setView(GraphicInterface view){
        this.view = view;
    }

    public void setConnection(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public ServerConnection getConnection(){
        return serverConnection;
    }

    public Totem getLocalTotem(){
        return localTotem;
    }

    public void setServerConnection(boolean isRMI) {
        if(this.serverConnection == null) {
            try {
                if (isRMI) {
                    new Thread(()->{
                        ClientRMI clientRMI = null;
                        try {
                            clientRMI = new ClientRMI(serverIP, 1234, "VirtualServer",this);
                        } catch (RemoteException e) {
                            view.showError("Connection attempt failed");
                            scheduler.schedule(ClientMain::terminateClient, 5,  TimeUnit.SECONDS);
                        }
                        clientRMI.setClientController(this);
                        clientRMI.run();
                        this.serverConnection = clientRMI;
                    }).start();
                } else {
                    SocketClient socketClient = new SocketClient(this, serverIP);
                    this.serverConnection = socketClient;
                }
            } catch (Exception e) {
                view.showError("Connection error.");
            }
        }
        else{
            view.showError("Connection already established");
        }
    }
    public Board getCurrentBoard() {
        return currentBoard;
    }

    public String getLocalPlayerName() {
        return localPlayerName;
    }

    public Player getLocalPlayer() {
        synchronized (BoardLock){
        for (Player p : currentBoard.getPlayers()) {
            if (p.getName().equals(localPlayerName)) {
                return p;
            }
        }
        return null;
        }
    }
    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

  //choice of the number of players
    public void setNumPlayers(int numPlayers) {
        if (numPlayers < 2 || numPlayers > 5) {
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
        synchronized (BoardLock){
            currentBoard = board;
        }
        view.showStartGame();

        Player player =getLocalPlayer();
        if (player!=null){
            view.showMessage("You start the game with "+ player.getFood() + " food");
        }

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
        view.showCurrentPlayer(player);
    }

    public void foodUpdated(String username, int updatedFood){
        Player player = getPlayerByName(username);
        synchronized (player){
            if(player.getFood()== updatedFood)
                return;

            int deltaFood = updatedFood - player.getFood();
            player.modifyFood(deltaFood);
            view.showPlayerFoodUpdate(username, updatedFood);
            if(!player.getName().equals(localPlayerName))
                return;
        }
        view.showMessage("You now have " + updatedFood + " food");
    }
    public void updatePlayerPP(String username, int updatedPPs){
        Player player = getPlayerByName(username);
        synchronized (player){
            if(player.getPrestigePoints()== updatedPPs)
                return;

            int deltaPPs = updatedPPs - player.getPrestigePoints();
            player.modifyPP(deltaPPs);
            view.showPlayerPPUpdate(username, updatedPPs);
            if(!player.getName().equals(localPlayerName))
                return;
        }
        view.showMessage("You now have " + updatedPPs + " Prestige Points");
    }

    public void showUpdateForEvents(Event event) {
        view.showMessage("Resolving " + event.getEventName());
    }
    public void showError(String message) {
        view.showError(message);
    }
    public void showUpdateTurn(Board board, int round) {
        currentBoard = board;
        localNumberRound = round;
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
        synchronized (tmpLock){
            if (localPlayerName != null) {
                throw new RuntimeException("Trying to confirm an already confirmed username");
            }
            tmpUsername = username;
            this.localPlayerName = username;
            view.showMessage("Username confirmed: " + username);
        }
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
        synchronized (tmpLock){
            if (localTotem != null) {
                throw new RuntimeException("Trying to confirm an already confirmed Totem");
            }
            tmpTotem = totem;
            localTotem = totem;
            view.showMessage("Totem chosen is confirmed " + totem);
        }
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
        Totem t;
        String name;
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
                name = localPlayerName;
                t = tmpTotem;
                tmpTotem = null;
            }else  if(localPlayerName==null && localTotem!=null){
                name = tmpUsername;
                t = tmpTotem;
                tmpUsername = null;
            }else {
                t = tmpTotem;
                name = tmpUsername;
                tmpUsername = null;
                tmpTotem = null;
            }
        }
        serverConnection.login(name ,t );
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


    //skipping management
    public void requestSkip(){
        if(serverConnection==null || localPlayerName==null || localTotem==null) {
            view.showError("Function not available at this moment");
            return;
        }
        serverConnection.requestPickCard(localPlayerName,false, false,-1,true);
    }



    //picking management, output and input
    public void requestLocalPickCard(boolean isUpper, boolean isBuilding, int index) {
        if (serverConnection == null || localPlayerName == null ) {
            view.showError("Command not available in this moment");
            return;
        }
        serverConnection.requestPickCard(localPlayerName,isUpper, isBuilding, index,false );
    }

    public void showPickedCard(String playerName,boolean isUpper , boolean isBuilding, int index, int round) {
        if(round<localNumberRound){
            return;
        }
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
                if(!isBuilding){
                    playerWhoPicked.getTribeCard().add((Character) pickedCard);
                }
                else{
                    playerWhoPicked.getBuilding().add((Building) pickedCard);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        view.pickCard(playerName,isUpper, isBuilding, index);
        if(isBuilding){
            view.showPlayerFoodUpdate(playerName, playerWhoPicked.getFood());
        }
    }

    public void showPickedCardError() {
        view.showError("You can't pick this card");
    }
    public void showSkipError(){
        view.showError("You still have some characters to pick");
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
        return;
        }

        if(index<0){
            view.showError("Invalid index");
        return;
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
            boolean present = false;

            for(OfferCard c : currentBoard.getPath()){
                if(c.isOccupied() && c.getOccupiedBy().getName().equals(playerName)){
                    present = true;
                    break;
                }
            }
            if(present){
                throw new RuntimeException("Player "+playerName+" is already in this moment");
            }
            if(!currentBoard.getPath().get(index).isOccupied()){
                currentBoard.getPath().get(index).setOccupiedBy(player);
            }
        }
        view.moveTotem(playerName,index);
        if(currentBoard.getTurnOrderCard().getOrder().isEmpty())
            pickingPhase.set(true);
//        view.showMessage("Player"+ playerName + "moved to: "+ index);
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
            while (!pickingPhase.get()){
                try {
                    Thread.sleep(TimeUnit.MILLISECONDS.toMillis(100));
                } catch (InterruptedException ignored) {
                }
            }

            for (OfferCard offerCard : currentBoard.getPath()) {
                if (offerCard.isOccupied()
                        && offerCard.getOccupiedBy().getName().equals(playerName)) {

                    offerCard.release();
                    break;
                }
            }
        }
        synchronized (currentBoard.getTurnOrderCard()) {
            currentBoard.getTurnOrderCard().getOrder().add(player);
        }
        if(currentBoard.getTurnOrderCard().getOrder().size() == currentBoard.getPlayers().size()){
            pickingPhase.set(false);
        }
        view.showReturnToTOC(playerName);
     //   view.showMessage("Player " + playerName + " returned to Turn Order Card position " + indexTOC);

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
        view.showError("Connection error, the game will close");

        scheduler.schedule(ClientMain::terminateClient,5, TimeUnit.SECONDS );
    }

    public void handleEndGameNormally(String winner, List<PlayerScore> leaderboard){
        if(isGameFinished.get()){
            return;
        }
        isGameFinished.set(true);

        //view.showMessage("Connection closed, the game ended successfully");
        view.showEndGameSuccessfully(winner, leaderboard);

    }
    public void updateLeaderboardFromDB(int playerPositionInDB, List<LeaderBoardData>leaderboardFromDB){
        playerPosition = playerPositionInDB;
        leaderboardDB = leaderboardFromDB;
        scheduler.schedule(() -> view.showLeaderboardFromDB(playerPosition, leaderboardDB), 10, TimeUnit.SECONDS);
        //gestione chiusura sole connessioni
    }
}
