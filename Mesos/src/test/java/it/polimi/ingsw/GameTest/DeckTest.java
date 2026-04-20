package it.polimi.ingsw.GameTest;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Deck;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.ArrayList;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Events.EventName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {
    @Test
    public void createDeckTest5() {
        ArrayList<Card> deck = new ArrayList<Card>();
        Deck d = new Deck();
        deck = d.createDeck(5);

        int counterEra1 = 0;
        int counterEra2 = 0;
        int counterEra3 = 0;
        int counterEra4 = 0;
        for (Card c : deck) {

            switch (c.getEra()) {
                case 1:
                    counterEra1++;
                    break;
                case 2:
                    counterEra2++;
                    break;
                case 3:
                    counterEra3++;
                    break;
                case 4:
                    counterEra4++;
                    break;
            }
        }

        assertEquals(96, deck.size());
        assertEquals(deck.size(), counterEra1+counterEra2+counterEra3+counterEra4 );

        int counterHunter=0;
        int counterInventor=0;
        int counterPicker=0;
        int counterPainter=0;
        int counterShaman=0;
        int counterBuilder=0;
        int counterShEve =0;
        int counterHuEve=0;
        int counterPaEve=0;
        int counterSuEve=0;
        for(Card c : deck) {
            if(c.getCardType().equals("CHARACTER")){
                switch(((Character)c).getCharacterType()){
                    case CharacterType.HUNTER:
                        counterHunter++;
                        break;
                    case CharacterType.INVENTOR:
                        counterInventor++;
                        break;
                    case  CharacterType.PICKER:
                        counterPicker++;
                        break;
                    case  CharacterType.PAINTER:
                        counterPainter++;
                        break;
                    case CharacterType.SHAMAN:
                        counterShaman++;
                        break;
                    case  CharacterType.BUILDER:
                        counterBuilder++;
                        break;
                }
            }else{
                switch( ((Event)c).getEventName()){
                    case "SHAMANIC_EVENT":
                        counterShEve++;
                        break;
                    case  "HUNTING_EVENT":
                        counterHuEve++;
                        break;
                    case "PAINTING_EVENT":
                        counterPaEve++;
                        break;
                    case "SUSTENANCE_EVENT":
                        counterSuEve++;
                        break;
                }
            }
        }
        /* hunters= 15, builders =12, inventors= 20, painters=13, pickers=11, shamans=13
        * eventsCard = 3
        */
        assertEquals(15,counterHunter );
        assertEquals(12,counterBuilder );
        assertEquals(20,counterInventor );
        assertEquals(13,counterShaman );
        assertEquals(13, counterPainter );
        assertEquals(11, counterPicker);
        assertEquals(3, counterShEve );
        assertEquals(3, counterHuEve );
        assertEquals(3, counterPaEve );
        assertEquals(3, counterSuEve );
        assertEquals(96, counterBuilder+
                                        counterHunter+
                                        counterInventor+
                                        counterPainter+
                                        counterShaman+
                                        counterPicker+
                                        counterSuEve+
                                        counterPaEve+
                                        counterHuEve+
                                        counterShEve );
    }
    @Test
    public void createDeckTest4(){
        ArrayList<Card> deck = new ArrayList<Card>();
        Deck d = new Deck();
        deck = d.createDeck(4);

        int counterEra1 = 0;
        int counterEra2 = 0;
        int counterEra3 = 0;
        int counterEra4 = 0;
        for (Card c : deck) {

            switch (c.getEra()) {
                case 1:
                    counterEra1++;
                    break;
                case 2:
                    counterEra2++;
                    break;
                case 3:
                    counterEra3++;
                    break;
                case 4:
                    counterEra4++;
                    break;
            }


        }

        assertEquals(85, deck.size());
        assertEquals(deck.size(), counterEra1+counterEra2+counterEra3+counterEra4 );

        int counterHunter=0;
        int counterInventor=0;
        int counterPicker=0;
        int counterPainter=0;
        int counterShaman=0;
        int counterBuilder=0;
        int counterShEve =0;
        int counterHuEve=0;
        int counterPaEve=0;
        int counterSuEve=0;
        for(Card c : deck) {
            if(c.getCardType().equals("CHARACTER")){
                switch(((Character)c).getCharacterType()){
                    case CharacterType.HUNTER:
                        counterHunter++;
                        break;
                    case CharacterType.INVENTOR:
                        counterInventor++;
                        break;
                    case  CharacterType.PICKER:
                        counterPicker++;
                        break;
                    case  CharacterType.PAINTER:
                        counterPainter++;
                        break;
                    case CharacterType.SHAMAN:
                        counterShaman++;
                        break;
                    case  CharacterType.BUILDER:
                        counterBuilder++;
                        break;
                }
            }else{
                switch( ((Event)c).getEventName()){
                    case "SHAMANIC_EVENT":
                        counterShEve++;
                        break;
                    case  "HUNTING_EVENT":
                        counterHuEve++;
                        break;
                    case "PAINTING_EVENT":
                        counterPaEve++;
                        break;
                    case "SUSTENANCE_EVENT":
                        counterSuEve++;
                        break;
                }
            }
        }

        assertEquals(13,counterHunter );
        assertEquals(10,counterBuilder );
        assertEquals(20,counterInventor );
        assertEquals(10,counterShaman );
        assertEquals(12, counterPainter );
        assertEquals(8, counterPicker);
        assertEquals(3, counterShEve );
        assertEquals(3, counterHuEve );
        assertEquals(3, counterPaEve );
        assertEquals(3, counterSuEve );
        assertEquals(85, counterBuilder+
                counterHunter+
                counterInventor+
                counterPainter+
                counterShaman+
                counterPicker+
                counterSuEve+
                counterPaEve+
                counterHuEve+
                counterShEve );

    }

    @Test
    public void createDeckTest3(){
        ArrayList<Card> deck = new ArrayList<Card>();
        Deck d = new Deck();
        deck = d.createDeck(3);

        int counterEra1 = 0;
        int counterEra2 = 0;
        int counterEra3 = 0;
        int counterEra4 = 0;
        for (Card c : deck) {

            switch (c.getEra()) {
                case 1:
                    counterEra1++;
                    break;
                case 2:
                    counterEra2++;
                    break;
                case 3:
                    counterEra3++;
                    break;
                case 4:
                    counterEra4++;
                    break;
            }
        }

        assertEquals(74, deck.size());
        assertEquals(deck.size(), counterEra1+counterEra2+counterEra3+counterEra4 );

        int counterHunter=0;
        int counterInventor=0;
        int counterPicker=0;
        int counterPainter=0;
        int counterShaman=0;
        int counterBuilder=0;
        int counterShEve =0;
        int counterHuEve=0;
        int counterPaEve=0;
        int counterSuEve=0;
        for(Card c : deck) {
            if(c.getCardType().equals("CHARACTER")){
                switch(((Character)c).getCharacterType()){
                    case CharacterType.HUNTER:
                        counterHunter++;
                        break;
                    case CharacterType.INVENTOR:
                        counterInventor++;
                        break;
                    case  CharacterType.PICKER:
                        counterPicker++;
                        break;
                    case  CharacterType.PAINTER:
                        counterPainter++;
                        break;
                    case CharacterType.SHAMAN:
                        counterShaman++;
                        break;
                    case  CharacterType.BUILDER:
                        counterBuilder++;
                        break;
                }
            }else{
                switch( ((Event)c).getEventName()){
                    case "SHAMANIC_EVENT":
                        counterShEve++;
                        break;
                    case  "HUNTING_EVENT":
                        counterHuEve++;
                        break;
                    case "PAINTING_EVENT":
                        counterPaEve++;
                        break;
                    case "SUSTENANCE_EVENT":
                        counterSuEve++;
                        break;
                }
            }
        }

        assertEquals(12,counterHunter );
        assertEquals(10,counterBuilder );
        assertEquals(15,counterInventor );
        assertEquals(8,counterShaman );
        assertEquals(11, counterPainter );
        assertEquals(6, counterPicker);
        assertEquals(3, counterShEve );
        assertEquals(3, counterHuEve );
        assertEquals(3, counterPaEve );
        assertEquals(3, counterSuEve );
        assertEquals(74, counterBuilder+
                counterHunter+
                counterInventor+
                counterPainter+
                counterShaman+
                counterPicker+
                counterSuEve+
                counterPaEve+
                counterHuEve+
                counterShEve );

    }

    @Test
    public void createDeckTest2(){
        ArrayList<Card> deck = new ArrayList<Card>();
        Deck d = new Deck();
        deck = d.createDeck(2);

        int counterEra1 = 0;
        int counterEra2 = 0;
        int counterEra3 = 0;
        int counterEra4 = 0;
        for (Card c : deck) {

            switch (c.getEra()) {
                case 1:
                    counterEra1++;
                    break;
                case 2:
                    counterEra2++;
                    break;
                case 3:
                    counterEra3++;
                    break;
                case 4:
                    counterEra4++;
                    break;
            }
        }

        assertEquals(63, deck.size());
        assertEquals(deck.size(), counterEra1+counterEra2+counterEra3+counterEra4 );

        int counterHunter=0;
        int counterInventor=0;
        int counterPicker=0;
        int counterPainter=0;
        int counterShaman=0;
        int counterBuilder=0;
        int counterShEve =0;
        int counterHuEve=0;
        int counterPaEve=0;
        int counterSuEve=0;
        for(Card c : deck) {
            if(c.getCardType().equals("CHARACTER")){
                switch(((Character)c).getCharacterType()){
                    case CharacterType.HUNTER:
                        counterHunter++;
                        break;
                    case CharacterType.INVENTOR:
                        counterInventor++;
                        break;
                    case  CharacterType.PICKER:
                        counterPicker++;
                        break;
                    case  CharacterType.PAINTER:
                        counterPainter++;
                        break;
                    case CharacterType.SHAMAN:
                        counterShaman++;
                        break;
                    case  CharacterType.BUILDER:
                        counterBuilder++;
                        break;
                }
            }else{
                switch( ((Event)c).getEventName()){
                    case "SHAMANIC_EVENT":
                        counterShEve++;
                        break;
                    case  "HUNTING_EVENT":
                        counterHuEve++;
                        break;
                    case "PAINTING_EVENT":
                        counterPaEve++;
                        break;
                    case "SUSTENANCE_EVENT":
                        counterSuEve++;
                        break;
                }
            }
        }

        assertEquals(9,counterHunter );
        assertEquals(9,counterBuilder );
        assertEquals(13,counterInventor );
        assertEquals(7,counterShaman );
        assertEquals(9, counterPainter );
        assertEquals(4, counterPicker);
        assertEquals(3, counterShEve );
        assertEquals(3, counterHuEve );
        assertEquals(3, counterPaEve );
        assertEquals(3, counterSuEve );
        assertEquals(63, counterBuilder+
                counterHunter+
                counterInventor+
                counterPainter+
                counterShaman+
                counterPicker+
                counterSuEve+
                counterPaEve+
                counterHuEve+
                counterShEve );

    }

}
