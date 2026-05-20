package it.polimi.ingsw.Game;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Factory.BuildingFactory;


public class Board  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int era;
    private final ArrayList<Card> upperCardRow;
    private final ArrayList<Card> lowerCardsRow;
    private final ArrayList<Card> upperBuildingRow;
    private final ArrayList<Card> lowerBuildingRow;
    private final ArrayList<OfferCard> path;
    private final ArrayList<Player> players; //relazione con Player (2..5)
    private final ArrayList<Card> deck;//*associazione 1:1 con Board*/
    private TurnOrderCard turnOrderCard;
    private final ArrayList<Building> buildingsEra1;
    private final ArrayList<Building> buildingsEra2;
    private final ArrayList<Building> buildingsEra3;

    public Board() {
        this.era = 1;
        this.upperCardRow = new ArrayList<>();
        this.lowerCardsRow = new ArrayList<>();
        this.upperBuildingRow = new ArrayList<>();
        this.lowerBuildingRow = new ArrayList<>();
        this.path = new ArrayList<>();
        this.players = new ArrayList<>();
        this.deck = new ArrayList<>();
        this.buildingsEra1 = new ArrayList<>();
        this.buildingsEra2 = new ArrayList<>();
        this.buildingsEra3 = new ArrayList<>();

    }

    public void setEra(int era) {
        this.era = era;
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

    public ArrayList<Card> getUpperBuildingRow() {
        return upperBuildingRow;
    }

    public ArrayList<Card> getLowerBuildingRow() {
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

    public ArrayList<Building> getBuildingsEra1(){
        return buildingsEra1;
    }

    public ArrayList<Building> getBuildingsEra2() {
        return buildingsEra2;
    }

    public ArrayList<Building> getBuildingsEra3() {
        return buildingsEra3;
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
        initializeDeck();
        buildingPerPlayers(NumPlayers);
        obtainPath(players);
        upperBuildingRow.addAll(buildingsEra1);
        initializeTurnOrderCard();
        setCardOnBoard();


    }

    public void setCardOnBoard(){
        int upper = players.size()+4;
        int lower = players.size()+1;
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

    public void initializeTurnOrderCard(){
        ArrayList<Player> players = this.players;
        Collections.shuffle(players);
        turnOrderCard = new TurnOrderCard(players.size());
        turnOrderCard.getOrder().addAll(players);
    }

    //rimozione degli edifici
    public void removeLowerBuildings() {
        lowerBuildingRow.clear();

    }

    public void initializeDeck(){
        Deck deck = new Deck();
        this.deck.clear();
        this.deck.addAll(deck.createDeck(players.size()));
    }

    public void refillUpperBuildingByEra(){
        upperBuildingRow.clear();
        if(era==2){
            upperBuildingRow.addAll(buildingsEra2);
        }else if (era ==3){
            upperBuildingRow.addAll(buildingsEra3);
        }
    }

   public boolean refillCards() {
       boolean eraChanged = false;

       int cardNeeded = players.size() + 4;

       while (upperCardRow.size() < cardNeeded && !getDeck().isEmpty()) {
           Card newCard = getDeck().removeFirst();

           if (newCard.getEra() != era) {
               shiftBuildingUpToDown();
               era = newCard.getEra();
               refillUpperBuildingByEra();
               eraChanged = true;
           }

           upperCardRow.add(newCard);
       }

       return eraChanged;
   }

    //lo si usa per rimuovere gli edifici
    public void removeCards(Card card) {
        upperCardRow.remove(card);
        lowerCardsRow.remove(card);
        upperBuildingRow.remove(card);
        lowerBuildingRow.remove(card);
    }
    //spostamento degli edifici quando cambiano le ere
    public void shiftBuildingUpToDown() {
        lowerBuildingRow.clear();
        lowerBuildingRow.addAll(upperBuildingRow);
        upperBuildingRow.clear();
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

        path.add(new OfferCard(2, 0, 1, false));
        path.add(new OfferCard(3, 1, 0, false));
        path.add(new OfferCard(5, 1, 1, false));
        path.add(new OfferCard(6, 2, 0, false));
        if (numPlayers >= 3) {
            path.add(new OfferCard(4, 0, 2, false));
            if (numPlayers >= 4)
                path.add(new OfferCard(7, 2, 1, false));
            if (numPlayers >= 5)
                path.add(new OfferCard(1, 0, 0, true));
        }
        Collections.sort(path, (a, b) -> a.getID() - b.getID());
        return path;
    }

    public Card pickCard( boolean upper, boolean building, int i){
        ArrayList<Card> pickCardRow;
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

    /**
     * the method checks that the deck still has cards, if so it removes the first card of the deck and returns it
     * @return the first card of the deck
     */
    public Card drawCard() {
        if (!deck.isEmpty()) {
            Card c = deck.getFirst();
            deck.removeFirst();
            return c;
        }
        else throw new IllegalArgumentException("il deck è finito");
    }

    /**
     * @param numPlayers it is the number of players present in the game
     * The method creates all the buildings in the game through the factory in 3 different lists, mixes them, and
     * based on the number of players selects the right number of buildings to use in the game
     */
    public void buildingPerPlayers(int numPlayers){

        BuildingFactory buildingFactory = new BuildingFactory();
        ArrayList<Building> buildings = buildingFactory.createBuildingList();
        ArrayList<Building> correctBuilding = new ArrayList<>();
        ArrayList<Building> totalBuildingEra1 = new ArrayList<>();
        ArrayList<Building> totalBuildingEra2 = new ArrayList<>();
        ArrayList<Building> totalBuildingEra3 = new ArrayList<>();
        totalBuildingEra1.addAll(buildings.subList(0,6));
        Collections.shuffle(totalBuildingEra1);
        totalBuildingEra2.addAll(buildings.subList(6,13));
        Collections.shuffle(totalBuildingEra2);
        totalBuildingEra3.addAll(buildings.subList(13,21));
        Collections.shuffle(totalBuildingEra3);
        if(numPlayers==2) {
            buildingsEra1.add(totalBuildingEra1.getFirst());
            buildingsEra2.addAll(totalBuildingEra2.subList(0,2));
            buildingsEra3.addAll(totalBuildingEra3.subList(0,3));
        } else if (numPlayers==3) {
            buildingsEra1.addAll(totalBuildingEra1.subList(0,2));
            buildingsEra2.addAll(totalBuildingEra2.subList(0,2));
            buildingsEra3.addAll(totalBuildingEra3.subList(0,4));
        } else if (numPlayers==4) {
            buildingsEra1.addAll(totalBuildingEra1.subList(0,2));
            buildingsEra2.addAll(totalBuildingEra2.subList(0,3));
            buildingsEra3.addAll(totalBuildingEra3.subList(0,4));
        } else {
            buildingsEra1.addAll(totalBuildingEra1.subList(0,2));
            buildingsEra2.addAll(totalBuildingEra2.subList(0,3));
            buildingsEra3.addAll(totalBuildingEra3.subList(0,5));
        }
    }
}




