package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Deck;
import it.polimi.ingsw.Game.Player;

import javax.smartcardio.Card;
import java.util.ArrayList;

//La classe GameManager coordina il flusso di gioco, i turni e i cambi di era.

public class GameManager {
    private int round;
    private int numPlayers;
    private ArrayList<Player> players;
    private Board board;
    private Deck deck;//*associazione 1:1 con Board*/
    private int currentEra;

    //costruttore
    public GameManager(ArrayList<Player> players, int numPlayers, Board board, Deck deck ) {
        this.players = players;
        this.numPlayers = numPlayers;
        this.board = board;
        this.deck = deck;
        this.round = 0; //il gioco ancora non è iniziato
    }

    //metodi
    public void gameInitializing(ArrayList<Player> players, int numPlayers) {
        this.players = players;
        this.numPlayers = numPlayers;
        this.round = 1;  //se il gioco parte subito
    }

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
    }


    public void nextRound(){
        //risoluzione eventi sulle carte che sono sulla fila inferiore
        ArrayList<Event> currentEvents = board.checkEvent();
        if (!currentEvents.isEmpty()){
            resolveEvents(currentEvents);
        }
        board.shiftUpToDown();

        if(deck.isEmpty()){ //Se il mazzo è vuoto cambio era
            eraSwitch();
        }
        board.refillUpperRow(); //metto le carte della nuova era nella file superiore
        this.round++;
    }

    public void checkEraChange(){
        //controllo di aver cambiato era
    }

    /*Ordino eventi, quelli con lo stesso nome vengono ordinati per era
     e il sostentamento viene risolto per ultimo
     */
    public void endRound(){
        ArrayList<Event> resolveEvents = board.checkEvent();
        resolveEvents(resolveEvents);
            board.getLowerCardsRow().clear();//tutte le carte personaggio rimaste sotto vengono rimosse
            board.shiftUpToDown();
            board.refillCards(deck);
        }
        if(deck.isEmpty() && round == 10){
            endGame();
        }

        public void addPlayer(Player player){
        if(this.players.size() >= 5){
            throw new IllegalStateException("You can't add more than 5 players");
        }
        this.players.add(player);

    }


    public void resolveEvents (ArrayList<Event> events){
        if(events == null || events.isEmpty()){
            return;
        }
        events.sort((e1, e2) -> {
            boolean isE1Sustenance = e1.getName().equalsIgnoreCase ("Sustenance");
            boolean isE2Sustenance = e1.getName().equalsIgnoreCase ("Sustenance");

            if (isE1Sustenance && !isE2Sustenance) return 1; //se e1 è sost. e e2 no sost allora e1 viene risolto dopo
            if (!isE1Sustenance && isE2Sustenance) return -1;//se e2 è sost. e e1 no allora e1 viene risolto prima
            return Integer.compare(e1.getEra(), e2.getEra()); // se non sono sost oppure lo sono entrambi, li ordino per era
        });
        for (Event e : events){
            e.applyEffect(this.players);
        }

    }

    public Player endGame(){
        System.out.println("--- THE GAME IS OVER  ---");
        System.out.println("Final points count...");

        resolveEvents(board.checkEvent());

        for (Player p : players) {
            int finale = p.finalScore(); // Il calcolo vero è dentro Player!
            p.setScore(finale);
        }
        System.out.println("IL VINCITORE E': " + vincitore.getName() + "!");
    }

    public void drawCard(Arraylist <card> up , Arraylist<card> down){
            if(deck.isEmpty()){
                System.out.println("The deck is empty");
                return null;
            }
            Card drawnCard = deck.drawCard(); //la carta che è stata pescata dal mazzo viene salvata in drawncard
            if (drawnCard.getEra() > board.getEra()){ //se la carta pescata è di un'era futura, attivo cambio era
                eraSwitch();
            }
            return drawnCard;

    }
    public void buyBuilding(Player player, Building building) {
            int cost = building.getprice();
            if(player.getFood() <= cost){
                player.modifyFood(-cost);
                player.getBuilding().add(building); //aggiungo l'edificio alla lista degli edifici del giocatore
                board.removeCards(building);
                System.out.println("Building " + building.getName() + " has been modified!");
            }else{
                System.out.println("INSUFFICIENT FOOD! (Requested :" + cost +")");
            }


    }

    public void takeCharacter(Player player, Character character){
            player.getTribeCard().add(character);
            board.removeCards(character);
            System.out.println(player.getName() + " he added" + character.getCharacterType());

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board){
        this.board = board;
    }
}

