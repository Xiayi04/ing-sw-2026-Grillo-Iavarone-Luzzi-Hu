package it.polimi.ingsw;

import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Cards.Events.*;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Factory.ConcreteFactoryEra;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;

import java.util.ArrayList;
import java.util.List;


/**
 * Hello Mattia, sei bellissimo!
 *
 */
public class App {
    public static void main(String[] args) {
        ArrayList<Event> eventi = new ArrayList<Event>();
        SustenanceEvent e1 = new SustenanceEvent(1, CardType.EVENT, EventName.SUSTENANCE_EVENT, 2);
        eventi.add(e1);
        SustenanceEvent e2 = new SustenanceEvent(2, CardType.EVENT, EventName.SUSTENANCE_EVENT, 2);
        eventi.add(e2);
        ShamanicEvent e3 = new ShamanicEvent(1, CardType.EVENT, EventName.SHAMANIC_EVENT, 4, 7);
        eventi.add(e3);
        HuntingEvent e4 = new HuntingEvent(3, CardType.EVENT, EventName.HUNTING_EVENT, 5);
        eventi.add(e4);
        GameManager.resolveEvents(eventi);
        for(Event e: eventi){
            e.printCard();
        }
    }
}
