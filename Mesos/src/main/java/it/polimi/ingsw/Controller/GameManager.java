package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.*;
import it.polimi.ingsw.Cards.Characters.Character;

import java.util.*;
/*La classe GameManager coordina il flusso di gioco, i turni e i cambi di era.*/

public class GameManager {
    private int round;
    private int numPlayers;
    public final static Object numPlayersLock = new Object();
    private ArrayList<Player> players;
    public static final Object playersLock = new Object();
    private Board board;
    private int currentEra;
    private Player currentPlayer;
    private List<PendingPick> pickingQueue = new ArrayList<>();
    private List<Player> positionQueue = new ArrayList<>();

    //costruttore
    public GameManager(ArrayList<Player> players, int numPlayers, Board board) {
        this.players = players;
        this.numPlayers = numPlayers;
        this.board = board;
        this.round = 0;
    }

    //metodi
    public void gameInitializing(ArrayList<Player> players, int numPlayers, Deck deck) {
        this.players = players;
        this.numPlayers = numPlayers;
        this.round = 1;
        deck.createDeck(numPlayers);

    }

    /**
     * The new player order method is used to manage the turns of the next round,
     * it creates a temporary list with the order of the players
     */
    private void newPlayerOrder() {
        ArrayList<Player> tmpOrder = new ArrayList<>();
        ArrayList<OfferCard> path = board.getPath();
        for( OfferCard c : path ) {
            if(c.isOccupied()){
                tmpOrder.add(c.getOccupiedBy());
            }
        }
        this.players = tmpOrder; //aggiorno la lista ufficiale del giocatori
    }

    /**
     * Method for moving to the next round.
     * Resolve the events on the bottom row,
     * then move the cards from the top row to the bottom row,
     * and finally refill the cards from the deck in the top row.
     * @return next Round.
     */
    public void nextRound() {
        ArrayList<Event> currentEvents = board.checkEvent();
        positionQueue.clear();
        if (!currentEvents.isEmpty()) {
            resolveEvents(currentEvents);
        }
        board.shiftUpToDown();
        board.refillCards(new Deck(),players);
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
        resolveEvents(resolveEvents);
        board.shiftUpToDown();
        board.refillCards(new Deck(),players);

        if(board.getDeck().isEmpty() && round == 10)
            endGame();
    }

    public void addPlayer(Player player) {
        if (this.players.size() >= 5) {
            throw new IllegalStateException("You can't add more than 5 players");
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

        int maxScore = 0;
        for (Player p : players) {
            int finale = p.finalScore();
            if (finale > maxScore) {
                maxScore = finale;
            }
        }

        ArrayList<Player> tmpWinner = new ArrayList<>();
        for (Player p : players) {
            if (p.finalScore() == maxScore) {
                tmpWinner.add(p);
            }
        }

        Player winner = null;

        if (tmpWinner.size() == 1) {
            winner = tmpWinner.getFirst();
        } else {
            int maxFood = 0;
            for (Player p : tmpWinner) {
                if (p.getFood() > maxFood) {
                    maxFood = p.getFood();
                    winner = p;
                }
            }
        }

        System.out.println("The winner is: " + winner.getName() + "with"+ winner.finalScore()+ "points!");
    }

    /**
     * The method positionPhase represents the list of players
     * who must position themselves
     */

    public void positionPhase(){
        positionQueue.clear();
        positionQueue.addAll(this.players);
        executeNextPosition();
    }

    /**
     * The method execute Next Position is used to manage the flow of turns sequentially,
     * ensuring that one player at a time chooses where to place their totem on the board.
     */

    public void executeNextPosition() {
        synchronized (playersLock) {
            if (positionQueue.isEmpty()) {
                pickingPhase(this.players);
                return;
            }
            this.currentPlayer = positionQueue.get(0);
            System.out.println("Player  " + currentPlayer.getName() + "place your totem");
            notifyAll();
        }
    }

    /**
     * metodo per il positionamento effettivo
     * @param playerName
     * @param pathIndex
     */
    public void resolvePosition(String playerName, int pathIndex){
        synchronized (this.players){
            if(positionQueue.isEmpty()){
                return;
            }
            OfferCard chosenCard = board.getPath().get(pathIndex);
            if(chosenCard.isOccupied()){
                System.out.println("The position you have chosen is occupied, please choose another one.");
                executeNextPosition();
                return;
            }
            Player p = positionQueue.get(0);
            chosenCard.setOccupiedBy(p);
            System.out.println(p.getName() + " he positioned himself on the card " + pathIndex);
            newPlayerOrder();
            notifyAll();
            positionQueue.remove(0);
            executeNextPosition();
        }
    }

    /**
     * The method setNumPlayers determines how many players the server should
     * wait before declaring the lobby "full" and starting rounds.
     * @param numPlayers
     */

    public void setNumPlayers(int numPlayers) {
        synchronized (numPlayersLock){
            this.numPlayers = numPlayers;
            this.board.obtainPath(this.players);
            System.out.println("The game is made up of" + numPlayers + " players");
            notifyAll();
        }

    }

    /**
     *The PendingPhase record is a special type of class where the data inside cannot be changed
     * until the action is complete, allowing the server to remember what to validate when the
     * client submits its final choice.
     * @param player
     * @param isUpper
     */
    public record PendingPick(Player player, boolean isUpper) {}

    /** The execute Next Pick method advances the turns in the draw phase.
     * If the list is empty, this means that all players have completed the draw phase
     * for that round; otherwise, it looks at the first element of the list,
     * takes the communication interface associated with the specific player
     * and finally waits for the player to respond.
     */

     private void executeNextPick(){
        if (pickingQueue.isEmpty()) { // se è vuota vuol dire che tutti i giocatori si sono posizionati allora si passsa al prossimo turno
            nextRound();
            return;
        }
        this.currentPlayer = pickingQueue.get(0).player();
        System.out.println("Tocca a " + currentPlayer.getName());
    }

    /**The Picking Phase method is the drawing phase, it is used to establish the exact order in which players
     *  will choose cards from the board. The offerCards are swiped from left to right. If the position is occupied,
     *  it reads how many upward-pointing arrows it has, saves them in the list and associates
     *  them with the player occupying that position on the list.
     *  Then it goes back and does the same check for the downward-pointing arrows.
     *  Once the turn list has been compiled,
     *  it tells the first player on the list to make his choice.
     * @param players
     */

    public void pickingPhase(ArrayList<Player> players) {
        ArrayList<OfferCard> path = board.getPath();
        pickingQueue.clear();

        for(OfferCard offerCard : path ) {
            if(offerCard.isOccupied()){
                for(int i = 0; i < offerCard.getUpArrow(); i++){
                    pickingQueue.add(new PendingPick(offerCard.getOccupiedBy(), true));
                }
                for(int i = 0; i < offerCard.getDownArrow(); i++){
                    pickingQueue.add(new PendingPick(offerCard.getOccupiedBy(), false));
                }
            }
        }

        executeNextPick();
    }

    /**
     * The resolve Pick method receives the response from the player
     * and actually updates the game state.
     * @param playerName
     * @param isBuilding
     * @param index
     */
    public void resolvePick(String playerName, boolean isBuilding, int index) {
        synchronized (playersLock) {
            PendingPick currentAction = pickingQueue.get(0);
            Player p = getPlayerByName(playerName);

            boolean success = false;
            if (isBuilding) {
                success = (buyBuilding(p, currentAction.isUpper(), index) != null);
            } else {
                takeCharacter(p, currentAction.isUpper(), index);
                success = true;
            }
            if (success) {
                pickingQueue.remove(0);
                executeNextPick();
            } else {
                System.out.println("Failed action");
            }
        }
    }

    /**
     * The method MoveTotem  move a player's totem to a position on the offer card
     * @param playerName
     * @param pathIndex
     */


    public synchronized void moveTotem(String playerName, int pathIndex) {
        //verifica che sia il turno del giocatore effettivo
        if(positionQueue.isEmpty() || !positionQueue.get(0).getName().equals(playerName)){
            System.out.println(" Error: It's not your turn ");
            return;
        }
        //verifica se la poszione è valida e libera
        if( pathIndex<0 || pathIndex >= board.getPath().size()){
            System.out.println("Invalid path index");
            return;
        }

        OfferCard chosenCard = board.getPath().get(pathIndex);
        if(chosenCard.isOccupied()){
            System.out.println("Position" + pathIndex + "it's already busy");
            return;
        }
        //modiifica del Model
         Player p = positionQueue.get(0);
        chosenCard.setOccupiedBy(p);
        System.out.println(p.getName() + " he positioned himself on the card " + pathIndex);
        positionQueue.remove(0);
        executeNextPosition();

    }

    /**
     * Method to buy a building from the list of buildings on the board +
     * make sure you have enough food to buy it.
     * @param player
     * @param rowUpper
     * @param index
     * @return of the purchased building
     */

    public Building buyBuilding(Player player, Boolean rowUpper, int index) {
        ArrayList<Building> buildings;
        if (rowUpper) {
            buildings = board.getUpperBuildingRow();
        }else{
            buildings = board.getLowerBuildingRow();
        }
        if(index < 0 || index >= buildings.size()|| buildings.get(index) == null){
            System.out.println("Error: Error: building not available in index" + index);
            return null;
        }

        int cost = buildings.get(index).getPrice();
        if(player.getFood() >= cost){
            player.modifyFood(-cost);
            Building pickedBuilding=  (Building) board.pickCard(rowUpper, true, index);
            player.getBuilding().add(pickedBuilding);
            System.out.println("The building" + pickedBuilding.getName() + "was purchased by");
            return pickedBuilding;
        }else{
            System.out.println("INSUFFICIENT FOOD! (Requested :" + cost +")");
            System.out.println("Choose another building or move on");
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

    public void takeCharacter(Player player, boolean isUpper,int index){
        Character pickedCharacter = (Character) board.pickCard(isUpper,false,index);
        if(pickedCharacter != null){
            player.getTribeCard().add(pickedCharacter);
            System.out.println(player.getName() + "added" + pickedCharacter.getCharacterType());
        }else{
            System.err.println("Character not found");
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

    public int getRound() {
        return round;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getCurrentEra() {
        return currentEra;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public String[] getAvailableTotems(){
        ArrayList<Totem> takenTotems = new ArrayList<>();
        Totem[] allTotems = new Totem[]{Totem.RED, Totem.BLACK, Totem.BLUE, Totem.ORANGE, Totem.YELLOW};
        ArrayList<Totem> totems = (ArrayList<Totem>) Arrays.asList(allTotems);
        synchronized (playersLock) {
            for (Player player : players) {
                takenTotems.add(player.getTotem());
            }
        }
        totems.removeAll(takenTotems);
        String[] totemNames = new String[takenTotems.size()];
        for(int i = 0; i < takenTotems.size(); i++) {
            totemNames[i] = totems.get(i).toString();
        }
        return totemNames;
    }

}

