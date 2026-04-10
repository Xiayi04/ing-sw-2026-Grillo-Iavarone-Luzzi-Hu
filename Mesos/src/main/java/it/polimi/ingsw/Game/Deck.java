package it.polimi.ingsw.Game;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Deck gestisce l'insieme di carte disponibili per l'era corrente.
 * Carica i dati da un file JSON e fornisce metodi per pescare e rimescolare.
 */
public class Deck {
    private ArrayList<Card> cards;
    private CardFactory factory;

    public Deck(){
        this.cards = new ArrayList<>();
        this.factory = new CardFactory();
    }
    public void createDeckforEra(int era,int numPlayers) {
        List<Card> allCardsFromFile = loadCardsFromJson(); //carica tutte le carte prese dal file
        for (Card c : allCardsFromFile) { //filtro le carte e tengo solo quelle dell'era che mi servono
            if (c.getEra() == era) {
                this.cards.add(c);
            }
        }
    }

    public void clearOldCards(){
        if(this.cards!= null) {//svuota il mazzo attuale e fa spazio alle nuove carte
            this.cards.clear();
        }
    }

    public void loadEra(int eraNumero){ // per caricare un file specifico
        this.cards.clear(); //svuota il precedente
        String nomeFile= "era"+eraNumero+".json";
        ArrayList<Card> listaDati = JacksonHelper.readJson(nomeFile);
        for (Card d : listaDati) {
            Card newCard = factory.createCard(d);
            if (newCard != null) {
                this.cards.add(newCard);
            }
        }
    }

    public Card drawCard() {
        if (!this.cards.isEmpty()) { //rimuove e restituisce la prima carta in cima al mazzo
            return this.cards.remove(0);//ritorna la carta pescata opppue null se il mazzo è vuoto
        }
        return null;
    }
    public boolean isEmpty() { //verifica se il mazzo è esaurito
        return this.cards.isEmpty();
    }

}
