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
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
/*La classe GameManager coordina il flusso di gioco, i turni e i cambi di era.*/

public class GameManager {
    private int round;
    private int numPlayers;
    public final static Object numPlayersLock = new Object();
    private final ArrayList<Player> players;
    public static final Object playersLock = new Object();
    private Board board;
    private Player currentPlayer;
    private final List<PendingPick> pickingQueue = new ArrayList<>();
    private Notifier  notifier = null;
    private final AtomicBoolean pickingPhase = new AtomicBoolean(false);
    private final AtomicBoolean positioningPhase = new AtomicBoolean(false);
    private final AtomicBoolean isGameStarted = new AtomicBoolean(false);

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

     */

    public void startGame()  {
        if(isGameStarted.get()){
            return;
        }
        isGameStarted.set(true);

        synchronized (playersLock) {
            System.out.println("Starting game");
            this.round = 1;
            board.setEra(1);
            this.board.getPlayers().addAll(this.players);
            this.board.initializeBoard(numPlayers);
            initializeFood();
            System.out.println("Game started");
            notifier.gameStartedBroadcast(players,board);
            System.out.println("Start of the Totem positioning phase...");
            positionPhase();

        }
    }

    public void initializeFood(){
        board.getTurnOrderCard().getOrder().get(0).modifyFood(2);
        board.getTurnOrderCard().getOrder().get(1).modifyFood(3);
        if(numPlayers>=3){
            board.getTurnOrderCard().getOrder().get(2).modifyFood(3);
            if(numPlayers>=4){
                board.getTurnOrderCard().getOrder().get(3).modifyFood(4);
                if(numPlayers == 5 ){
                    board.getTurnOrderCard().getOrder().get(4).modifyFood(4);
                }
            }
        }
    }


    /**
     * Method for moving to the next round.
     * Resolve the events on the bottom row,
     * then move the cards from the top row to the bottom row,
     * and finally refill the cards from the deck in the top row.
     * next Round.
     */
    public void nextRound() {
        this.round++;
        System.out.println(" Start of the Round " + this.round);

        ArrayList<Event> currentEvents = board.checkEvent();
        if(round==10){
            ArrayList<Event> upperEvents = board.checkUpperEvent();
            currentEvents.addAll(upperEvents);
        }
        if (!currentEvents.isEmpty()) {
            resolveEvents(currentEvents);
        }
        board.shiftUpToDown();
        boolean eraChanged = board.refillCards();
        if(eraChanged){
            notifier.newEraBroadcast(board.getEra());
        }
        notifier.nextRoundBroadcast(board);

        if(this.round > 10){
            positioningPhase.set(false);
            pickingPhase.set(false);
            endGame();
            return;
        }
        positionPhase();
    }

    /**
     * The method to handle the end or a round : I sort events,
     * those with the same name are sorted by era
     * and the sustenance is resolved last.
     *  endRound;
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
     * @param events:
     */
    public void resolveEvents(ArrayList<Event> events) {
        if (events == null || events.isEmpty()) {
            return;
        }
        events.sort(
                Comparator
                        .comparing((Event e) -> e.getEventName().equalsIgnoreCase("Sustenance"))
                        .thenComparing(Event :: getEventName)
                        .thenComparing(Event::getEra)
        );

        for (Event event : events) {
            notifier.resolvingEventBroadcast(event);
            event.resolveEvent(board.getPlayers());

            for(Player player : this.players){
                notifier.foodUpdateBroadcast(player, player.getFood());
                notifier.ppUpdateBroadcast(player, player.getPrestigePoints());
            }
        }
    }

    /**
     * the method for managing the end of the game and the various calculations
     * to establish the winner
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
        PlayerScore winner = leaderboard.getFirst();

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
        ArrayList<LeaderBoardData> leaderboardFromDB = new ArrayList<>();

        try {
            leaderboardFromDB = (ArrayList<LeaderBoardData>) leaderboardDAO.getAllTimeLeaderBoard(numPlayers);
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
            return;
        }

        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException ignored) {}

        for(Player p : players){
            try{
                notifier.sendLeaderBoard(p,leaderboardDAO.getPositionInLeaderBoard(numPlayers,p.finalScore()), leaderboardFromDB);
            } catch (SQLException e) {
                System.out.println("Error while sending the ranking to the player:" + e.getMessage());
            }

        }

    }

    /**
     * The method positionPhase call the method execute next position
     */

    public void positionPhase(){
        positioningPhase.set(true);
        executeNextPosition();
    }

    /**
     * The method execute Next Position is used to manage the flow of turns sequentially,
     * ensuring that one player at a time chooses where to place their totem on the board.
     */

    public void executeNextPosition() {
        synchronized (board.getTurnOrderCard().getOrder()) {
            if (board.getTurnOrderCard().getOrder().isEmpty()) {
                positioningPhase.set(false);
                System.out.println("Totem positioning phase completed.");
                pickingPhase();
                return;
            }
            this.currentPlayer = board.getTurnOrderCard().getOrder().getFirst();

            System.out.println("Player  " + currentPlayer.getName() + " move your totem");
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
     * @param playerName:
     * @param pathIndex:
     */
    public synchronized void resolvePosition(String playerName, int pathIndex){
        if(!positioningPhase.get()){
            notifier.invalidTotemPosition(getPlayerByName(playerName));
            return;
        }
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
            System.out.println(p.getName() + " moved on the card " + pathIndex);
            notifier.movedTotemBroadcast(p ,pathIndex);
            executeNextPosition();
        }
    }

    /**
     * The method setNumPlayers determines how many players the server should
     * wait before declaring the lobby "full" and starting rounds.
     * @param numPlayers:
     */

    public void setNumPlayers(int numPlayers){
        synchronized (numPlayersLock){
            this.numPlayers = numPlayers;
            System.out.println("This game has " + numPlayers + " players.");
        }


    }

    /**
     *The PendingPhase record is a special type of class where the data inside cannot be changed
     * until the action is complete, allowing the server to remember what to validate when the
     * client submits its final choice.
     * @param player
     * @param isUpper
     */
    public record PendingPick(Player player, boolean isUpper, boolean isEventPick, boolean isSkippable) {}

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
                        pickingQueue.add(new PendingPick(offerCard.getOccupiedBy(), true,false,false ));
                    }
                    for (int i = 0; i < offerCard.getDownArrow(); i++) {
                        pickingQueue.add(new PendingPick(offerCard.getOccupiedBy(), false,false,false));
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
                pickingQueue.add(new PendingPick(playersWithBonus, true,true,false));
            }
        }
        pickingPhase.set(true);
        executeNextPick();
    }

    /**
     *The Resolve Pick method manages the draw phase for each player on the path, proceeding from left to right based on the arrows previously assigned.
     *  The system allows the player to choose which arrow to use, it's not requested that the player has to follow the order in which the arrows were stored.
     *  If a player successfully acquires a building or draws a card, it is added to their inventory.
     *  Additionally, there is a check to see if the player holds a special card; if so,
     *  they are allowed to draw another card only after all players have completed their turns.
     *  Finally, once a player concludes their draw phase, they are returned to the turn order card
     * @param playerName:
     * @param isBuilding:
     * @param index:
     */
    public void resolvePick(String playerName, boolean isUpperRequested, boolean isBuilding, int index) {
        Player p = getPlayerByName(playerName);
        if(!pickingPhase.get()){
            notifier.invalidCardPick(p);
            return;
        }

        if(pickingQueue.isEmpty() || !pickingQueue.getFirst().player.equals(p)){
            notifier.invalidCardPick(getPlayerByName(playerName));
            return;
        }
        PendingPick currentAction = pickingQueue.getFirst();
        int i = 0;
        if(currentAction.isUpper() != isUpperRequested) {
            boolean flag = false;
            i++;
            for(; i< pickingQueue.size();i++){
                if(pickingQueue.get(i).player().equals(p) && pickingQueue.get(i).isUpper() == isUpperRequested && !pickingQueue.get(i).isEventPick()) {
                    flag=true;
                    break;
                }
            }
            if(!flag){
                notifier.invalidCardPick(getPlayerByName(playerName));
                return;
            }
        }

        int lenght;
        if(isUpperRequested){
            if(isBuilding){
                lenght = board.getUpperBuildingRow().size();
            }
            else {
                lenght = board.getUpperCardRow().size();
            }
        }
        else {
            if(isBuilding){
                lenght = board.getLowerBuildingRow().size();
            }
            else {
                lenght = board.getLowerCardsRow().size();
            }
        }

        if(index < 0 || index >= lenght) {
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
            checkAndReturnToTOC(p);
            try {

                notifier.pickedCardBroadcast(p, isUpperRequested, isBuilding, index);
            } catch (Exception e) {
                System.err.println("Network error");
            }

            executeNextPick();
        } else {
            System.out.println("Failed action");
        }
    }


    /**
     * the method checks if the direction of the currentPick has card to pick, it controls the
     * list of Buildings and of the Characters, if both lists are available it returns true.
     * If the list of characters is not available to pick, but the buildings list is available, the parameter
     * canSkip is set to true, and the current pick is updated with the new value of canSkip.
     */
    public boolean isDirectionPickable() {
        PendingPick currentPick = pickingQueue.get(0);
        ArrayList<Card> isCharactersListAvailable = new ArrayList<>();
        ArrayList<Card> isBuildingListEmpty = new ArrayList<>();
        boolean isUpper = currentPick.isUpper();


        if (isUpper) {
            isCharactersListAvailable = board.getUpperCardRow();
            isBuildingListEmpty = board.getUpperBuildingRow();
        }else{
            isCharactersListAvailable = board.getLowerCardsRow();
            isBuildingListEmpty = board.getLowerBuildingRow();
        }
        boolean hasCharacters = availableCharacters(isCharactersListAvailable);
        boolean hasBuildings = availableBuilding(isBuildingListEmpty );
       //when it's all empty
        if (!hasCharacters && !hasBuildings) {
            removeAllPendingPick(isUpper);
            return false;
        }
        //else
        boolean canSkip = !hasCharacters && hasBuildings;

        PendingPick updatedPick = new PendingPick(currentPick.player(), currentPick.isUpper(), currentPick.isEventPick(), canSkip);

        pickingQueue.set(0, updatedPick);

        return true;

    }

    /**
     * the method allows to remove all pick which the same directions
     * @param isUpper:
     */
    public void removeAllPendingPick(boolean isUpper) {
        if (isUpper) {
            pickingQueue.removeIf(pick -> pick.isUpper() );
        } else {
            pickingQueue.removeIf(pick -> !pick.isUpper() );
        }
    }

    /**
     * the method cheks if the list
     * @param cards
     * contains character, it checks that the list is not empty, and it exists at least one character card
     * if so it returns a boolean.
     *
     */

    private boolean availableCharacters(ArrayList<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            return false;
        }

        for (Card card : cards) {
            if (card != null && !card.getCardType().equals("EVENT")) {
                return true;
            }
        }

        return false;
    }

    /**
     * the method checks if the list
     * @param buildings
     * is empty or not
     * @return :
     */
    private boolean availableBuilding(ArrayList<Card> buildings) {
        boolean buildingsAvailable = buildings != null && !buildings.isEmpty();
        return buildingsAvailable;
    }


    /** the method allows the player
     *  @param playerName ,
     *  who owns the value isSkippable = true, the possibility
     *  to skip the pick. After all the checks, the currentPick is removed
     * If the currentPlayer finished his turn, the queue is empty or the next pick is a special pick, then
     * the player comes back to TOC.
     */
    public void skipPick(String playerName)  {
        Player p = getPlayerByName(playerName);

        synchronized (pickingQueue) {

            if (pickingQueue.isEmpty() || !pickingQueue.get(0).player().equals(p)) {
                notifier.invalidCardPick(p);
                return;
            }

            PendingPick currentPick = pickingQueue.get(0);

            if (!currentPick.isSkippable()) {
                notifier.invalidCardPick(p);
                return;
            }

            pickingQueue.remove(0);
            checkAndReturnToTOC(p);

            if (pickingQueue.isEmpty() || !pickingQueue.get(0).player().equals(p)  || pickingQueue.get(0).isEventPick()) {

                if (!board.getTurnOrderCard().getOrder().contains(p)) {
                    int indexTOC = board.bringBackToTOC(p);

                    if (indexTOC >= 0) {
                        notifier.returnTotemOnTurnOrderBroadcast(p, indexTOC);
                    } else {
                        System.out.println("Error while bring back to TOC");
                    }
                }
            }
        }

        executeNextPick();
    }
    private void checkAndReturnToTOC(Player p) {
        synchronized (pickingQueue) {
            // Verifica se il giocatore ha ancora dei turni in coda
            boolean hasMoreActions = pickingQueue.stream()
                    .anyMatch(pick -> pick.player().equals(p)&& !pick.isEventPick());

            if (!hasMoreActions) {
                if (!board.getTurnOrderCard().getOrder().contains(p)) {
                    int indexTOC = board.bringBackToTOC(p);
                    if (indexTOC >= 0) {
                        notifier.returnTotemOnTurnOrderBroadcast(p, indexTOC);
                        board.giveFoodForTOC(p, indexTOC);
                        notifier.foodUpdateBroadcast(p, p.getFood());
                        notifier.ppUpdateBroadcast(p, p.getPrestigePoints());
                    }
                }
            }
        }
    }


    /** The execute next pick method checks if the list is empty:
     *  if so, it means all players have completed their draw, and the game can proceed to the next turn;
     *  otherwise, it checks if the list in which the pick points out still has pickable cards, if so
     *  all players are notified that it is the current player's turn to perform their draws.
     */

    public void executeNextPick(){
         synchronized (pickingQueue) {
             while (!pickingQueue.isEmpty()) {
                 if (isDirectionPickable()) {
                     notifier.showTurnBroadcast(pickingQueue.get(0).player());
                     return;
                 }
             }
             System.out.println("All players have drawn");
             pickingPhase.set(false);
             nextRound();
         }
    }



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

            System.out.println("The building " + pickedBuilding.getName() + " was purchased by " + player);
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

    public boolean takeCharacter(Player player, boolean isUpper,int index) {
        Character pickedCharacter = (Character) board.pickCard(isUpper,false,index);
        if(pickedCharacter != null){
            //Using visitor to add the Character to the player's list
            //and automatically increase the counter of the specific character
            CharacterVisitor visitor = new AddAndCountCharacter();
            if (pickedCharacter.addCard(visitor,player)) {
                notifier.foodUpdateBroadcast(player, player.getFood());
            }
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

