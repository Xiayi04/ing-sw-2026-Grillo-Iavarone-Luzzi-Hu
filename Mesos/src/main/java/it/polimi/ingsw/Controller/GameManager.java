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
     /*
    public void eraSwitch(){
        this.currentEra++;
        if(this.currentEra > 4){
            endGame();
            return;
        }
        deck.loadEra(this.currentEra); //caricamento del nuovo mazzo tramite la factory
        if (this.currentEra == 3){
            board.removeLowerBuildings();
        }
        if(this.currentEra == 2 || this.currentEra == 3){
            board.shiftBuildingUpToDown();
        }
        board.refillCards(deck,players);
        System.out.println("Siamo passati all'Era " + currentEra);
    }*/

    /*metodo per passare al round successivo */
    public void nextRound() {
        /*//risoluzione eventi sulle carte che sono sulla fila inferiore
        ArrayList<Event> currentEvents = board.checkEvent();
        if (!currentEvents.isEmpty()) {
            resolveEvents(currentEvents);
        }
        board.shiftUpToDown();
        board.refillCards(deck, players); //metto le carte della nuova era nella file superiore
        this.round++;*/
    }

    /*public void checkEraChange(){
        //controllo di aver cambiato era
    }*/

    /*Metodo per gestire la fine di un round: Ordino eventi, quelli con lo stesso nome vengono ordinati per era
     e il sostentamento viene risolto per ultimo
     */
    public void endRound() {
        /*ArrayList<Event> resolveEvents = board.checkEvent();
        resolveEvents(resolveEvents);
        board.shiftUpToDown();
        board.refillCards(deck, players);

        if(deck.isEmpty() && round == 10)
            endGame();*/
    }

    /*aggiungo i giocatori */
    public void addPlayer(Player player) {
        if (this.players.size() >= 5) {
            throw new IllegalStateException("You can't add more than 5 players");
        }
        this.players.add(player);
    }


    /*public void resolveEvents(ArrayList<Event> events) {
        if (events == null || events.isEmpty()) {
            return;
        }
        events.sort((e1, e2) -> {
            boolean isE1Sustenance = e1.getEventName().equalsIgnoreCase("Sustenance");
            boolean isE2Sustenance = e2.getEventName().equalsIgnoreCase("Sustenance");

           // if (isE1Sustenance && !isE2Sustenance) return 1; //se e1 è sost. e e2 no sost allora e1 viene risolto dopo
           // if (!isE1Sustenance && isE2Sustenance) return -1;//se e2 è sost. e e1 no allora e1 viene risolto prima
            return Integer.compare(e1.getEra(), e2.getEra()); // se non sono sost oppure lo sono entrambi, li ordino per era
        });
        for (Event e : events) {
            e.resolveEvent(players);
        }
        return;
    }*/

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


    public void buyBuilding(Player player, Boolean row, int index) {
        ArrayList<Building> building = new ArrayList<>();
        int cost = building.get(index).getPrice();
            if(player.getFood() >= cost){
                player.modifyFood(-cost);
                player.getBuilding().add(building.get(index)); //aggiungo l'edificio alla lista degli edifici del giocatore
                System.out.println("Building " + building.get(index).getName() + " has been modified!");
                Card pickedBuilding=  board.pickCard(row, true, index);
                player.getBuilding().add((Building) pickedBuilding);
            }else{
                System.out.println("INSUFFICIENT FOOD! (Requested :" + cost +")");
            }

    }

    public void takeCharacter(Player player, Character character){//da cambiare
            player.getTribeCard().add(character);
            board.removeCards(character);
            System.out.println(player.getName() + "added" + character.getCharacterType());

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

