package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Deck;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Cards.Characters.Character;

import java.util.ArrayList;
import java.util.Comparator;

/*La classe GameManager coordina il flusso di gioco, i turni e i cambi di era.*/

public class GameManager {
    private int round;
    private int numPlayers;
    private ArrayList<Player> players;
    private Board board;
    private int currentEra;

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
        board.shiftUpToDown();
        board.refillCards(board.getDeck(), players);
        // manca il fatto che il giocatore che sta alla sinistra del tabellone deve prendere la prima posizione
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
        board.refillCards(board.getDeck(), players);

        if(board.getDeck().isEmpty() && round == 10)
            endGame();
    }

    public void addPlayer(Player player) {
        if (this.players.size() >= 5) {
            throw new IllegalStateException("You can't add more than 5 players");
        }
        this.players.add(player);
    }


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

        ArrayList<Player> tmpWinner = null;
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


    public void pickingPhase(ArrayList<Player> players) {
        /*ArrayList<OfferCard> path = board.getPath();

        for(OfferCard offerCard : path ) {

            if(offerCard.isOccupied()){
                //sarebbe bello avere un metodo getOccupier per ottenere il nome del player
                //che occupa l'offerCard
                //qui dovremo anche avere il modo per mostrare al giocatore le carte che può sceglier
                //
            }//DUBBIO : FORSE NON VA FATTO ANCORA IL GAME MANAGER PERCHè VA IMPLEMENTATO CON LA RETE
        }*/
    }

    /**
     * Method to buy a building from the list of buildings on the board +
     * make sure you have enough food to buy it.
     * @param player
     * @param row
     * @param index
     * @return of the purchased building
     */

    public void buyBuilding(Player player, Boolean row, int index) {
        ArrayList<Building> buildings;
        if (row) {
            buildings = board.getUpperBuildingRow();
        }else{
            buildings = board.getLowerBuildingRow();
        }
        int cost = buildings.get(index).getPrice();
        if(player.getFood() >= cost){
            player.modifyFood(-cost);
            Card pickedBuilding=  board.pickCard(row, true, index);
            player.getBuilding().add((Building) pickedBuilding);
            System.out.println("The building" + ((Building) pickedBuilding).getName() + "was purchased by");
        }else{
            System.out.println("INSUFFICIENT FOOD! (Requested :" + cost +")");
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
            System.err.println(player.getName() + "not added" + pickedCharacter.getCharacterType());
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
}

