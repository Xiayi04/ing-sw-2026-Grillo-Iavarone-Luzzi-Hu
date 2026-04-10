package it.polimi.ingsw;

import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Factory.ConcreteFactoryEra1;

import java.util.List;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        ConcreteFactoryEra1 fabbrica1= new ConcreteFactoryEra1(3);
        List<Character> charactersEra1 = fabbrica1.createCharacterListProva();
        /*List<Event> eventsEra1 = fabbrica1.createEventList();
        for(Character c : charactersEra1){
            c.printCard();
        }
        for(Event e : eventsEra1){
            e.printCard();
        }*/
    }
}
