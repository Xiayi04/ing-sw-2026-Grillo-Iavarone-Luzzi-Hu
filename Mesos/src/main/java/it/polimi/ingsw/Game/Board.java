package it.polimi.ingsw.Game;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Controller.GameManager;


public class Board  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int era;
    private final ArrayList<Card> upperCardRow;
    private final ArrayList<Card> lowerCardsRow;
    private final ArrayList<Building> upperBuildingRow;
    private final ArrayList<Building> lowerBuildingRow;
    private final ArrayList<OfferCard> path;
    private final ArrayList<Player> players; //relazione con Player (2..5)
    private final ArrayList<Card> deck;//*associazione 1:1 con Board*/
    private TurnOrderCard turnOrderCard;

    public Board() {
        this.era = 1;
        this.upperCardRow = new ArrayList<>();
        this.lowerCardsRow = new ArrayList<>();
        this.upperBuildingRow = new ArrayList<>();
        this.lowerBuildingRow = new ArrayList<>();
        this.path = new ArrayList<>();
        this.players = new ArrayList<>();
        this.deck = new ArrayList<>();

    }

    //metodi getter
    public int getEra() {
        return era;
    }


    public ArrayList<Card> getUpperCardRow() {
        return upperCardRow;
    }


    public ArrayList<Card> getLowerCardsRow() {
        return lowerCardsRow;
    }

    public ArrayList<Building> getUpperBuildingRow() {
        return upperBuildingRow;
    }

    public ArrayList<Building> getLowerBuildingRow() {
        return lowerBuildingRow;
    }

    public ArrayList<OfferCard> getPath() {
        return path;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public TurnOrderCard getTurnOrderCard(){
        return turnOrderCard;
    }

    //metodi
    //scorro la lista per individuare carte evento
    public ArrayList<Event> checkEvent() {
        ArrayList<Event> events = new ArrayList<>();
        for (Card c : lowerCardsRow) {
            if (c instanceof Event e) {
                events.add(e);
            }
        }
        return events;

    }
    public void chooseTurnOrderCard(int numPlayers) {
        this.turnOrderCard = new TurnOrderCard(numPlayers);
    }

    //sposto da sopra a sotto
    public void shiftUpToDown() {
        lowerCardsRow.clear();
        lowerCardsRow.addAll(upperCardRow);
        upperCardRow.clear();
    }

    public void initializeBoard(int NumPlayers){
        int upper = NumPlayers+4;
        int lower = NumPlayers+1;
        int j= 0;
        for(int i = 0; i<lower;  ){
            Card c = deck.removeFirst();
            if (c instanceof Event) {
                upperCardRow.add(c);
                j++;
                continue;
            }
            lowerCardsRow.add(c);
            i++;

        }

        for(;j<upper;j++){
            Card c = deck.removeFirst();
            upperCardRow.add(c);
        }
    }

    //rimozione degli edifici
    public void removeLowerBuildings() {
        lowerBuildingRow.clear();

    }

    public void inizializeDeck(){
        Deck deck = new Deck();
        this.deck.clear();
        this.deck.addAll(deck.createDeck(players.size()));
    }

    //spostamento degli edifici quando cambiano le ere
    public void shiftBuildingUpToDown() {
        lowerBuildingRow.clear();
        lowerBuildingRow.addAll(upperBuildingRow);
        upperBuildingRow.clear();
    }

    public void refillCards(Deck deck, ArrayList<Player> players) {
       /* int cardNeeded = players.size() + 4;
        while (upperCardRow.size() < cardNeeded && !Deck.isEmpty()) {/*per l'ultimo turno
            Card newCard = deck.drawCard();// non capisco pk sia sbagliato
            upperCardRow.add(newCard);
        }*/
    }

    //lo si usa per rimuovere gli edifici
    public void removeCards(Card card) {
        upperCardRow.remove(card);
        lowerCardsRow.remove(card);
        upperBuildingRow.remove(card);
        lowerBuildingRow.remove(card);
    }


    public void moveTotem(Player players, OfferCard offerCard) {
        if (players == null || offerCard == null) {
            throw new IllegalArgumentException("player or offerCard is null");
        }


        if (!offerCard.isOccupied()) {
            throw new IllegalStateException("OfferCard already occupied");
        }

    }
//metodo percorso
    public ArrayList<OfferCard> obtainPath(ArrayList<Player> players) {
        int numPlayers = players.size();
        path.clear();

        path.add(new OfferCard('2', 0, 1, false));
        path.add(new OfferCard('3', 1, 0, false));
        path.add(new OfferCard('5', 1, 1, false));
        path.add(new OfferCard('6', 2, 0, false));
        if (numPlayers >= 3) {
            path.add(new OfferCard('4', 0, 2, false));
            if (numPlayers >= 4)
                path.add(new OfferCard('7', 2, 1, false));
            if (numPlayers >= 5)
                path.add(new OfferCard('1', 0, 0, true));
        }
        Collections.sort(path, (a, b) -> a.getID() - b.getID());
        return path;
    }

    public Card pickCard( boolean upper, boolean building, int i){
        ArrayList<? extends Card> pickCardRow;
        if(upper){
            if (building){
                pickCardRow = upperBuildingRow;
            }else{
                pickCardRow = upperCardRow;
            }
        }else{
            if(building){
                pickCardRow = lowerBuildingRow;
            }else {
                pickCardRow = lowerCardsRow;
            }
        }
        Card pickedCard = pickCardRow.get(i);
        if(pickedCard.getCardType().equals("EVENT")){
            return null;
        }else{
            pickCardRow.remove(i);
            return pickedCard;
        }
    }

}




