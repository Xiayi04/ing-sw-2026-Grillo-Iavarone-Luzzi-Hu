package it.polimi.ingsw.Controller;

import java.util.ArrayList;

//La classe GameManager coordina il flusso di gioco, i turni e i cambi di era.

public class GameManager {
    private int round;
    private int numPlayers;
    private ArrayList<Player> players;
    private int numPlayers;
    private Board board;
    private Deck deck;//*associazione 1:1 con Board*/

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
        deck.loadEra(this.currentEra); //il mazzo usa la factory per creare gli oggetti
        board.shiftUpToDown();
        board.refillCards();
        System.out.println("Siamo passati all'Era " + currentEra);
    }

    public void eraSwitch(){
        if(board.getEra() == 1){
            board.setEra(2);
        }else if(board.getEra() == 2){
            board.setEra(3);
        }
        deck.createDeckforEra(board.getEra(), numPlayers);//passo al mazzo il numero dell'era
        board.shiftUpToDown(); //le carte da sopra vanno sotto nel tabellone
        board.refillCards(); //riempio la fila superiore
    }

    public void nextRound(){
        //risoluzione eventi sulle carte che sono sulla fila inferiore
        ArrayList<Event> currentEvents =board.checkEvent();
        if (!currentEvents.isEmpty()){
            resolveEvents(currentEvents);
        }
        board.shiftUpToDown();// per dire che le carte da sopra vanno sotto

        if(deck.isEmpty()){ //Se il mazzo è vuoto cambio era
            eraSwitch();
        }
        board.refillUpperRow(); //metto le carte della nuova era nella file superiore
        this.round++;
    }


    public void checkEraChange(){
        //controllo di aver cambiato era
    }

    public void endRound(){
        board.risolviEventi();
        board.pulisciESposta();
        board.ripristina (numPlayers+4);

        if(deck.isEmpty() && round==10){
            endGame();
        }
    }

    public void resolveEvents (arrayList<Event> events){
        if(events == null || events.isEmpty()){
            return;
        }

    }

    public player endGame(){
        System.out.println("--- IL GIOCO E' FINITO ---");
        System.out.println("Inizio conteggio punti finali...");

        risolviEventiFinali();

        for (Player p : players) {
            int finale = p.calcolaPuntiFinali(); // Il calcolo vero è dentro Player!
            p.setScore(finale);
        }
        proclamaVincitore();
        System.out.println("IL VINCITORE E': " + vincitore.getName() + "!");
    }

    public void drawCard(Arraylist <card> up , Arraylist<card> down){

    }
    public void buyBuilding{

    }

    public void takeCharacter(){

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board){
        this.board = board;
    }
}
