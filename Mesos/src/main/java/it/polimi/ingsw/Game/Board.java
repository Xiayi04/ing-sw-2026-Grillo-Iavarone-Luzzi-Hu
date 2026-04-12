package it.polimi.ingsw.Game;
import java.util.ArrayList;
import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Events.Event;

public class Board {
    private int era;
    private ArrayList<Card> upperCardRow ;
    private ArrayList<Card> lowerCardsRow;
    private ArrayList<Building> upperBuildingRow;
    private ArrayList<Building> lowerBuildingRow;
    private ArrayList<OfferCard> path;
    private ArrayList<Player> players; //relazione con Player (2..5)

    public Board(){
        this.era = 1;
        this.upperCardRow = new ArrayList<>();
        this.lowerCardsRow = new ArrayList<>();
        this.upperBuildingRow = new ArrayList<>();
        this.lowerBuildingRow = new ArrayList<>();
        this.path = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    public ArrayList<Event> checkEvent(){
        return new ArrayList<>();

    }

    public void shiftUpToDown(){

    }
    public void refillCards(){

    }
    public void removeCards(Card card){
        upperCardRow.remove(card);
        lowerCardsRow.remove(card);
    }

    public ArrayList<OfferCard> getPath(){
        return path;
    }
    public void moveTotem(Player player){

    }

    public void pickCard(Player player,Card card){ //scelta carta

    }

    //gestione players, stiamo aggiungendo giocatori ma alla fine non sappiamo quanti ce ne sono

    public void addPlayer(Player player){
        if(players.size() >= 5){
            throw new IllegalStateException("You can't add more than 5 players");
        }
        players.add(player);
        player.setBoard(this); //sto dicendo al player in quale board si trova


    }

}
