package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.*;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Network.VirtualClient;

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
        this.round = 0; //il gioco ancora non è iniziato
    }

    //metodi
    public void gameInitializing(ArrayList<Player> players, int numPlayers, Deck deck) {
        this.players = players;
        this.numPlayers = numPlayers;
        this.round = 1;  //se il gioco parte subito
        deck.createDeck(numPlayers);

    }

    /**
     * crea una nuova lista players ordinataa da sinistra a destra
     * in base alla posizione dei gioctori sul tabellone
     */
    private void reorderPlayersByPosition() {
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
        if (!currentEvents.isEmpty()) {
            resolveEvents(currentEvents);
        }
        reorderPlayersByPosition(); //calcolo nuovo ordine dei giocatori
        board.shiftUpToDown();
        //board.refillCards(board.getDeck(), players);
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
       // board.refillCards(board.getDeck(), players);

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
                Comparator //ordina i casi true e false , se è sostentamento è true quindi lo risolve dopo altrimenti vengono risolti prima
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

    public void endGame() { //il metodo ora restituisce void da Player
        System.out.println("--- THE GAME IS OVER  ---");
        System.out.println("Final points count...");

        int maxScore = 0;
        for (Player p : players) {
            int finale = p.finalScore(); // Il calcolo vero è dentro Player!
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
     * Lista giocatori che devono posizionarsi
     */

    public void positionPhase(){
        positionQueue.clear();
        positionQueue.addAll(this.players);
        executeNextPosition();
    }

    /**
     * metodo per chiamare il primo giocatore della lista per
     * scegliere la posizone sul tracciato
     */
    public void executeNextPosition(){
        if (positionQueue.isEmpty()) {
            pickingPhase(this.players);
            return;
        }
        this.currentPlayer = positionQueue.get(0);
        this.currentPlayer.getProxy().askToPlaceTotem();
        System.out.println(" Giocatore "+ currentPlayer.getName() + "poszioma il tuo totem");
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
                System.out.println("La posizione scelta è occupata, scegline un'altra");
                executeNextPosition();
                return;
            }
            Player p = positionQueue.get(0);
            chosenCard.setOccupiedBy(p);
            System.out.println(p.getName() + "si è posizionato sulla tessera " + pathIndex);
            positionQueue.remove(0);
            executeNextPosition();
        }
    }

    /**Contenitore in cui ci sono i nome dei giocatori che dicono
     * al server chi deve giocare e cosa deve fare
     * @param player
     * @param isUpper
     */
    public record PendingPick(Player player, boolean isUpper) {} // contenitore per contenere solo i dati
    //invece di fare questo  public class PendingPick {
    //    private final Player player;
    //    private final boolean isUpper;
    //
    //    public PendingPick(Player player, boolean isUpper) {
    //        this.player = player;
    //        this.isUpper = isUpper;
    //    }
    //
    //    public Player getPlayer() { return player; }
    //    public boolean isUpper() { return isUpper; }
    //    // + i metodi equals, hashCode e toString... un sacco di codice!
    //} si usa record

    /** The execute Next Pick method advances the turns in the draw phase.
     * If the list is empty, this means that all players have completed the draw phase
     * for that round; otherwise, it looks at the first element of the list,
     * takes the communication interface associated with the specific player
     * and communicates the moves to the player,
     * and finally waits for the player to respond.
     */

     private void executeNextPick(){
        if (pickingQueue.isEmpty()) {
            nextRound();
            return;
        }
        PendingPick next = pickingQueue.get(0);
        this.currentPlayer = next.player();
        this.currentPlayer.getProxy().asktToPickCard(next.isUpper());
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
            }
        }
        for (OfferCard offerCard : path ) {
            if(offerCard.isOccupied()){
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
        synchronized (this.players) {
            PendingPick currentAction = pickingQueue.get(0); //recupero azione in cima alla lista
            Player p = getPlayerByName(playerName);
            if (isBuilding) {
                buyBuilding(p, currentAction.isUpper(), index);
            } else {
                takeCharacter(p, currentAction.isUpper(), index);
            }
            pickingQueue.remove(0); //Rimuovo l'azione dalla lista perchè è completata
            executeNextPick();
        }
    }


    public void moveTotem(String playerName, Totem totem) {
        ArrayList<Player> players = new ArrayList<>();
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
        int cost = buildings.get(index).getPrice();
        if(player.getFood() >= cost){
            player.modifyFood(-cost);
            Building pickedBuilding=  (Building) board.pickCard(rowUpper, true, index);
            player.getBuilding().add((Building) pickedBuilding);
            System.out.println("The building" + ((Building) pickedBuilding).getName() + "was purchased by");
            return pickedBuilding;
        }else{
            System.out.println("INSUFFICIENT FOOD! (Requested :" + cost +")");
            System.out.println("Scegli un personaggio oppure passa");
            pickingQueue.remove(0);
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
            System.err.println("Personaggio non trovato");
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

