package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Buildings.BuildingVisitor.ConcreteBuildingActivation;
import it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings.AddCardVisitor;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.AddAndCountCharacter;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Database.LeaderBoardDAO;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Game.*;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Network.PlayerScore;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;
/*La classe GameManager coordina il flusso di gioco, i turni e i cambi di era.*/

public class GameManager {
    private int round;
    private int numPlayers;
    public final static Object numPlayersLock = new Object();
    private ArrayList<Player> players;
    public static final Object playersLock = new Object();
    private Board board;
    private Player currentPlayer;
    private List<PendingPick> pickingQueue = new ArrayList<>();
    private Notifier  notifier = null;

    //costruttore
    public GameManager(ArrayList<Player> players, int numPlayers, Board board) {
        this.players = players;
        this.numPlayers = numPlayers;
        this.board = board;
        this.round = 0;
    }

    /**
     * It inizializes all the elements in order to prepare for the start of the game, the round and the
     * era are set to 1, all players and the number of players set by the first player are added
     * @throws RemoteException
     */

    public void startGame() throws RemoteException {
        synchronized (playersLock) {
            System.out.println("Starting game");
            this.round = 1;
            board.setEra(1);
            this.board.getPlayers().addAll(this.players);
            this.board.initializeBoard(numPlayers);
            System.out.println("Game started");
            notifier.gameStartedBroadcast(players,board);
            System.out.println("Start of the Totem positioning phase...");
            positionPhase();

        }
    }


    /**
     * Method for moving to the next round.
     * Resolve the events on the bottom row,
     * then move the cards from the top row to the bottom row,
     * and finally refill the cards from the deck in the top row.
     * @return next Round.
     */
    public void nextRound() {
        this.round++;

        if(this.round > 10){
            endGame();
            return;
        }
        System.out.println(" Start of the Round   " + this.round);

        ArrayList<Event> currentEvents = board.checkEvent();
        if (!currentEvents.isEmpty()) {
            resolveEvents(currentEvents);
        }
        board.shiftUpToDown();
        boolean eraChanged = board.refillCards();
        if(eraChanged){
            notifier.newEraBroadcast(board.getEra());
        }
        positionPhase();
    }

    /**
     * The method to handle the end or a round : I sort events,
     * those with the same name are sorted by era
     * and the sustenance is resolved last.
     * @return endRound;
     * */

    public void endRound() {
        ArrayList<Event> resolveEvents = board.checkEvent();
        if(!resolveEvents.isEmpty()) {
            resolveEvents(resolveEvents);
        }
        if(round > 10 || board.getDeck().isEmpty()){
            endGame();
        }else{
        board.shiftUpToDown();
        boolean eraChanged = board.refillCards();
        if(eraChanged){
            notifier.newEraBroadcast(board.getEra());
        }
        nextRound();
        }
    }

    public void addPlayer(Player player) {
        if (this.players.size() >= 5) {
            throw new IllegalStateException("More than 5 players are not available");
        }
        this.players.add(player);
    }

    /**the resolve events method orders the events based on which one
     *  needs to be resolved first with a comparator in true or false,
     *  i.e. if the event is marked as true it resolves
     *  it last otherwise they are resolved first.
     * @param events
     */


    public static void resolveEvents(ArrayList<Event> events) {
        if (events == null || events.isEmpty()) {
            return;
        }
        events.sort(
                Comparator
                        .comparing((Event e) -> e.getEventName().equalsIgnoreCase("Sustenance"))
                        .thenComparing(Event :: getEventName)
                        .thenComparing(Event::getEra)
        );
    }

    /**
     * the method for managing the end of the game and the various calculations
     * to establish the winner.
     * @return the winner
     */

    public void endGame() {
        System.out.println("--- THE GAME IS OVER  ---");
        System.out.println("Final points count...");

        //Data list creation for the ranking
        List<PlayerScore> leaderboard = new ArrayList<>();
        for (Player p : players) {
            leaderboard.add(new PlayerScore(p.getName(), p.finalScore(), p.getFood()));
        }

        // Raking order ( criterion: points and then food)
        leaderboard.sort((p1, p2) -> {
            int pointsCompare = Integer.compare(p2.points(), p1.points());
            if (pointsCompare != 0){
                return pointsCompare;
            }else {
                return Integer.compare(p2.food(), p1.food());
            }
        });
        //The winner is the first of the updated list
        PlayerScore winner = leaderboard.get(0);

        System.out.println("The winner is: " + winner.username());
        if(this.notifier != null){
            this.notifier.showEndGameBroadcast(getPlayerByName(winner.username()), leaderboard);
        }
        // aggiunge il punteggio di un giocatore uno per volta nel database
        LeaderBoardDAO leaderboardDAO = new LeaderBoardDAO();
        for (Player p : players) {
            try{
                leaderboardDAO.addNewPlayerScore(p.getName(), p.finalScore(), numPlayers);
            }catch (SQLException e){
                System.out.println("Error in the record of the points for the player " + p.getName() + ": " + e.getMessage());
            }
        }
        //aggiorna la classifica di tutte le partite fatte con quel numero di giocatori
        ArrayList<LeaderBoardData> leaderboardData = new ArrayList<>();
        for(Player p : players){
            try{
                notifier.sendLeaderBoard(p,leaderboardDAO.getPositionInLeaderBoard(numPlayers,p.finalScore()),leaderboardData);
            } catch (SQLException e) {
                System.out.println("Error while sending the ranking to the player:" + e.getMessage());
            }

        }

    }

    /**
     * The method positionPhase call the method execute next position
     */

    public void positionPhase(){
        executeNextPosition();
    }

    /**
     * The method execute Next Position is used to manage the flow of turns sequentially,
     * ensuring that one player at a time chooses where to place their totem on the board.
     */

    public void executeNextPosition() {
        synchronized (playersLock) {
            if (board.getTurnOrderCard().getOrder().isEmpty()) {
                System.out.println("Totem positioning phase completed.");
                pickingPhase();
                return;
            }
            this.currentPlayer = board.getTurnOrderCard().getOrder().get(0);

            System.out.println("Player  " + currentPlayer.getName() + "place your totem");
            if(currentPlayer.getVirtualClient() != null){
               notifier.showTurnBroadcast (currentPlayer);
            }
        }
    }

    /**
     * The resolve position method handles the placement of each player on the path.
     * Players choose an available offer card; if a offer card is already taken, they must choose another.
     * Once the choice is confirmed, the totem is removed from the turn order card and placed
     * on the selected offer card , and play proceeds to the next player.
     * @param playerName
     * @param pathIndex
     */
    public synchronized void resolvePosition(String playerName, int pathIndex){
        synchronized (playersLock){
            if(board.getTurnOrderCard().getOrder().isEmpty() || !currentPlayer.getName().equals(playerName)){
                return;
            }
            if(pathIndex < 0 || pathIndex >= board.getPath().size()){
                notifier.invalidTotemPosition(getPlayerByName(playerName));
                return;
            }
            OfferCard chosenCard = board.getPath().get(pathIndex);
            if(chosenCard.isOccupied()){
                System.out.println("The chosen position is occupied, please choose another one.");
                notifier.invalidTotemPosition(getPlayerByName(playerName));
                return;
            }
            Player p = board.getTurnOrderCard().getOrder().removeFirst();
            chosenCard.setOccupiedBy(p);
            System.out.println(p.getName() + " he positioned himself on the card " + pathIndex);
            notifier.movedTotemBroadcast(p ,pathIndex);
            executeNextPosition();
        }
    }

    /**
     * The method setNumPlayers determines how many players the server should
     * wait before declaring the lobby "full" and starting rounds.
     * @param numPlayers
     */

    public void setNumPlayers(int numPlayers) throws RemoteException {
        synchronized (numPlayersLock){
            this.numPlayers = numPlayers;
            System.out.println("The game is made up of" + numPlayers + " players");
            if(players.size() == numPlayers){
                this.board.obtainPath(this.players);
                startGame();
            }
        }

    }

    /**
     *The PendingPhase record is a special type of class where the data inside cannot be changed
     * until the action is complete, allowing the server to remember what to validate when the
     * client submits its final choice.
     * @param player
     * @param isUpper
     */
    public record PendingPick(Player player, boolean isUpper, boolean isEventPick) {}

    /**The Picking Phase method handles the draw phase and is used to assign arrows to each player based on the position
     *  they occupy on the offer card. The positions are analyzed from left to right: if a position is occupied,
     *  the system detects the number of upward-pointing arrows, saves them in a list,
     *  and associates them with the corresponding player. Subsequently, the process is repeated for the downward-pointing arrows.
     *  Additionally, the method checks if the player holds a special event card; if so, an extra arrow is assigned to them,
     *  granting them the right to an additional draw. Once the turn order list is compiled,
     *  the system prompts the first player on the list to make their choice.
     */

    public void pickingPhase() {
        ArrayList<OfferCard> path = board.getPath();

        Player playersWithBonus = null;

        synchronized (pickingQueue){
            AddCardVisitor addCardVisitor = new AddCardVisitor();
            for (OfferCard offerCard : path) {
                if (offerCard.isOccupied()) {
                    for (int i = 0; i < offerCard.getUpArrow(); i++) {
                        pickingQueue.add(new PendingPick(offerCard.getOccupiedBy(), true,false));
                    }
                    for (int i = 0; i < offerCard.getDownArrow(); i++) {
                        pickingQueue.add(new PendingPick(offerCard.getOccupiedBy(), false,false));
                    }
                    /*for (int i = 0; i < offerCard.getOccupiedBy().getBuilding().size(); i++) {
                        //AddCardVisitor addCardVisitor = new AddCardVisitor();
                        if (offerCard.getOccupiedBy().getBuilding().get(i).acceptAddCard(addCardVisitor, offerCard.getOccupiedBy()) == 1) {
                            playersWithBonus = offerCard.getOccupiedBy();
                        }
                    }*/
                    Player player = offerCard.getOccupiedBy();
                    for(Building b : player.getBuilding()){
                        if(b.acceptAddCard(addCardVisitor, player)==1){
                            playersWithBonus = player;
                        }
                    }
                }
            }
            if (playersWithBonus != null) {
                pickingQueue.add(new PendingPick(playersWithBonus, true,true));
            }
        }
        executeNextPick();
    }

    /**
     *The Resolve Pick method manages the draw phase for each player on the path, proceeding from left to right based on the arrows previously assigned.
     *  The system allows the player to choose which arrow to use, without being constrained by the order in which they were stored.
     *  If a player successfully acquires a building or draws a card, it is added to their inventory.
     *  Additionally, there is a check to see if the player holds a special card; if so,
     *  they are allowed to draw another card only after all players have completed their turns.
     *  Finally, once a player concludes their draw phase, they are returned to the turn order card
     * @param playerName
     * @param isBuilding
     * @param index
     */
    public void resolvePick(String playerName, boolean isUpperRequested, boolean isBuilding, int index) throws RemoteException {
        Player p = getPlayerByName(playerName);

        if(pickingQueue.isEmpty() || !pickingQueue.get(0).player.equals(p)){
            notifier.invalidCardPick(getPlayerByName(playerName));
            return;
        }
        PendingPick currentAction = pickingQueue.get(0);
        int i = 0;
        if(currentAction.isUpper() != isUpperRequested) {
            boolean flag = false;
            i++;
            for(; i< pickingQueue.size();i++){
                if(pickingQueue.get(i).player.equals(p) && pickingQueue.get(i).isUpper() == isUpperRequested && !pickingQueue.get(i).isEventPick) {
                    flag=true;
                    break;
                }
            }
            if(!flag){
                notifier.invalidCardPick(getPlayerByName(playerName));
                return;
            }
        }

        if(index < 0 || index > board.getPath().size()){
            System.out.println(p.getName() + " can't draw the card ");
            notifier.invalidCardPick(getPlayerByName(playerName));
            return;
        }

        boolean success ;
        if (isBuilding) {
            success = (buyBuilding(p, isUpperRequested, index) != null);
        } else {
            success = takeCharacter(p,isUpperRequested , index);
        }

        if (success) {
            pickingQueue.remove(i);
            try {

                notifier.pickedCardBroadcast(p, isUpperRequested, isBuilding, index);
            } catch (Exception e) {
                System.err.println("Network error");
            }

            if(pickingQueue.size() >= 1){
                if(!pickingQueue.get(0).player.equals(p) || (pickingQueue.get(0).player().equals(p) &&  pickingQueue.get(0).isEventPick)){

                    if(board.bringBackToTOC(p)>=0){
                        notifier.returnTotemOnTurnOrderBroadcast(p, board.bringBackToTOC(p));
                    }else{
                        System.out.println("Error while bring back to TOC");
                    }
                }
            }else{

                if(!board.getTurnOrderCard().getOrder().contains(p)){
                    if(board.bringBackToTOC(p)>=0){
                        notifier.returnTotemOnTurnOrderBroadcast(p, board.bringBackToTOC(p));
                    }else{
                        System.out.println("Error while bring back to TOC");
                    }
                }
            }

            executeNextPick();
        } else {
            System.out.println("Failed action");
        }
    }


    /** The execute next pick method checks if the list is empty:
     *  if so, it means all players have completed their draw, and the game can proceed to the next turn;
     *  otherwise, the system notifies all players that it is the current player's turn to perform their draws.
     */

    private void executeNextPick(){
         synchronized (pickingQueue) {
             if (pickingQueue.isEmpty()) {
                 System.out.println("All players have drawn");// se è vuota vuol dire che tutti i giocatori hanno pescato allora si passsa al prossimo turno
                 nextRound();
                 return;
             }
             notifier.showTurnBroadcast(pickingQueue.get(0).player);
         }
    }


    /**
     * The method MoveTotem  move a player's totem to a position on the offer card
     * @param playerName
     * @param pathIndex
     */


    /*public synchronized void moveTotem(String playerName, int pathIndex) {
        Player p = getPlayerByName(playerName);
        //verifica che sia il turno del giocatore effettivo
        if(positionQueue.isEmpty() || !positionQueue.get(0).getName().equals(playerName)){
            System.out.println(" Error: It's not your turn ");
            return;
        }
        //verifica se la posizione è valida e libera
        if( pathIndex<0 || pathIndex >= board.getPath().size()){
            System.out.println("Invalid path index");
            notifier.invalidTotemPosition(p);
            return;
        }

        OfferCard chosenCard = board.getPath().get(pathIndex);
        if(chosenCard.isOccupied()){
            System.out.println("Position" + pathIndex + "it's already busy");
            notifier.invalidTotemPosition(p);
            return;
        }
        //modiifica del Model
         Player player = positionQueue.get(0);
        chosenCard.setOccupiedBy(player);
        System.out.println(player.getName() + " he positioned himself on the card " + pathIndex);
        positionQueue.remove(0);
        executeNextPosition();

    }*/

    /**
     * Purchases a building from the specified row on the board.
     * Checks if the player has sufficient food resources to cover the cost,
     * while applying any available discounts from special cards or effects.
     *
     * @param player   The player performing the purchase.
     * @param rowUpper Boolean indicating if the building is in the upper row (true) or lower row (false).
     * @param index    The position of the building in the chosen row.
     * @return The purchased Building object if successful, or null if the purchase fails
     *         due to invalid index or insufficient food.
     */

    public Building buyBuilding(Player player, boolean rowUpper, int index) {
        ArrayList<Card> buildings;
        if (rowUpper) {
            buildings = board.getUpperBuildingRow();
        }else{
            buildings = board.getLowerBuildingRow();
        }
        if(index < 0 || index >= buildings.size()|| buildings.get(index) == null){
            System.out.println("Error: building not available in index" + index);
            notifier.invalidBuildingPurchase(player);
            return null;
        }

        Building selectedBuilding = (Building) buildings.get(index);
        int cost = selectedBuilding.getPrice();
        int discount = player.getBuilderDiscount();
        int finalCost= Math.max(0,cost - discount);


        if(player.getFood() >= finalCost){
            player.modifyFood(-finalCost);
            Building pickedBuilding=  (Building) board.pickCard(rowUpper, true, index);
            player.getBuilding().add(pickedBuilding);
            //Some buildings need to be activated when picked up from the board
            ActivationVisitor visitor = new ConcreteBuildingActivation();
            pickedBuilding.acceptActivation(visitor, player);

            System.out.println("The building" + pickedBuilding.getName() + "was purchased by");
            return pickedBuilding;
        }else{
            System.out.println("INSUFFICIENT FOOD! (Requested :" + finalCost +")");
            System.out.println("Current food:  " + player.getFood());
            notifier.invalidCardPick(player);
            return null;
        }

    }

    /**
     * method to take a character from the board +
     * check if that character is there
     * @param player
     * @param isUpper
     * @param index
     * @return the character taken
     */

    public boolean takeCharacter(Player player, boolean isUpper,int index) throws RemoteException {
        Character pickedCharacter = (Character) board.pickCard(isUpper,false,index);
        if(pickedCharacter != null){
            //Using visitor to add the Character to the player's list
            //and automatically increase the counter of the specific character
            CharacterVisitor visitor = new AddAndCountCharacter();
            pickedCharacter.addCard(visitor,player);
            System.out.println(player.getName() + "added" + pickedCharacter.getCharacterType());
            //broadcasting to everyone that the player picked successfully the wanted card
            return true;
        }else{
            notifier.invalidCardPick(player);
            return false;
        }
    }


    /**
     * The getPlayerByName method searches the list of players for the one with exactly
     * this name to understand who is actually doing something.
     * @param name
     * @return getPlayerByName
     */
    public Player getPlayerByName(String name) {
        return players.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void setNotifier(Notifier notifier){
        this.notifier = notifier;
    }

    public int getRound() {
        return round;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }


}

