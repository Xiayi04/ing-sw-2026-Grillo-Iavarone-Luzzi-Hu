package it.polimi.ingsw.Game;
import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Events.Event;


public class Board {
    private int era;
    private final ArrayList<Card> upperCardRow;
    private final ArrayList<Card> lowerCardsRow;
    private final ArrayList<Building> upperBuildingRow;
    private final ArrayList<Building> lowerBuildingRow;
    private final ArrayList<OfferCard> path;
    private final ArrayList<Player> players; //relazione con Player (2..5)

    public Board() {
        this.era = 1;
        this.upperCardRow = new ArrayList<>();
        this.lowerCardsRow = new ArrayList<>();
        this.upperBuildingRow = new ArrayList<>();
        this.lowerBuildingRow = new ArrayList<>();
        this.path = new ArrayList<>();
        this.players = new ArrayList<>();
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

    //sposto da sopra a sotto
    public void shiftUpToDown() {
        lowerCardsRow.clear();
        lowerCardsRow.addAll(upperCardRow);
        upperCardRow.clear();
    }

    //rimozione degli edifici
    public void removeLowerBuildings() {
        lowerBuildingRow.clear();

    }

    //spostamento degli edifici quando cambiano le ere
    public void shiftBuildingUpToDown() {
        lowerBuildingRow.clear();
        lowerBuildingRow.addAll(upperBuildingRow);
        upperBuildingRow.clear();
    }

    public void refillCards(Deck deck, ArrayList<Player> players) {
        int cardNeeded = players.size() + 4;
        while (upperCardRow.size() < cardNeeded && !deck.isEmpty()) {/*per l'ultimo turno*/
            Card newCard = deck.drawCard();// non capisco pk sia sbagliato
            upperCardRow.add(newCard);
        }
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


        if (!offerCard.isOccupied(players.getTotem())) {
            throw new IllegalStateException("OfferCard already occupied");
        }

    }

    public ArrayList<OfferCard> obtainPath(ArrayList<Player> players) {
        int numPlayers = players.size();

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


   /* public void pickCard(Player players,Card card){





}*/
}
