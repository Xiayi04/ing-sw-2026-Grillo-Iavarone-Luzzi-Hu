package it.polimi.ingsw.Game;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Factory.*;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Card> deck;

    public Deck(){
        this.deck = new ArrayList<>();
    }

    /**
     *
     * @param numPlayers
     * The method creates through the factory all the character and event cards divided by era, filters the
     * characters based on the number of players, combines events and characters of the same era by shuffling them,
     * and finally combines the cards of all eras while keeping the eras in order
     * @return deck shuffled correctly for the number of players
     */

    public ArrayList<Card> createDeck(int numPlayers){
        ArrayList<Card> deckEra1 = new ArrayList<>();
        ArrayList<Card> deckEra2 = new ArrayList<>();
        ArrayList<Card> deckEra3 = new ArrayList<>();
        ArrayList<Card> deckEra4 = new ArrayList<>();
        //creazione delle factory
        ConcreteFactoryEra factory1 = new ConcreteFactoryEra(1);
        ConcreteFactoryEra factory2 = new ConcreteFactoryEra(2);
        ConcreteFactoryEra factory3 = new ConcreteFactoryEra(3);
        ConcreteFactoryEra factory4 = new ConcreteFactoryEra(4);
        //mazzo dell'era 1 mischiato
        ArrayList<Character> charactersEra1 = factory1.createCharacterList();
        charactersEra1.removeIf(c -> c.getNumPlayers() > numPlayers);
        ArrayList<Event> eventsEra1 = factory1.createEventList();
        deckEra1.addAll(charactersEra1);
        deckEra1.addAll(eventsEra1);
        Collections.shuffle(deckEra1);
        Collections.shuffle(deckEra1);
        //mazzo dell'era 2 mischiato
        ArrayList<Character> charactersEra2 = factory2.createCharacterList();
        charactersEra2.removeIf(c -> c.getNumPlayers() > numPlayers);
        ArrayList<Event> eventsEra2 = factory2.createEventList();
        deckEra2.addAll(charactersEra2);
        deckEra2.addAll(eventsEra2);
        Collections.shuffle(deckEra2);
        Collections.shuffle(deckEra2);
        //mazzo dell'era 3 mischiato
        ArrayList<Character> charactersEra3 = factory3.createCharacterList();
        charactersEra3.removeIf(c -> c.getNumPlayers() > numPlayers);
        ArrayList<Event> eventsEra3 = factory3.createEventList();
        deckEra3.addAll(charactersEra3);
        deckEra3.addAll(eventsEra3);
        Collections.shuffle(deckEra3);
        Collections.shuffle(deckEra3);
        //mazzo dell'era 4 mischiato
        ArrayList<Event> eventsEra4 = factory4.createEventList();
        deckEra4.addAll(eventsEra4);
        Collections.shuffle(deckEra4);
        //creazione mazzo completo
        deck.addAll(deckEra1);
        deck.addAll(deckEra2);
        deck.addAll(deckEra3);
        deck.addAll(deckEra4);

        return deck;
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
}
