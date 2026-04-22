package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.DiscountBuilding;
import it.polimi.ingsw.Buildings.Events;
import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Factory.BuildingFactory;
import it.polimi.ingsw.Factory.ConcreteFactoryEra;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class DiscountBuildingTest {

    ArrayList<Player> players = new ArrayList<>();
    Player p1 = new Player("io", Totem.BLACK, 2);
    Player p2 = new Player("tu", Totem.ORANGE, 3);
    ConcreteFactoryEra factory = new ConcreteFactoryEra(1);
    ArrayList<Character> characters = factory.createCharacterList();
    ArrayList<Event> events = factory.createEventList();
    BuildingFactory buildingFactory = new BuildingFactory();
    ArrayList<Building> buildings = new ArrayList();


    @Test
    void discountBuildingTest(){
        players.add(p1);
        players.add(p2);
        p1.getBuilding().add(new DiscountBuilding(1, 1, 1, -1, 0, Icons.PAINTER, Events.SUSTENANCEEVENT));
        p1.getTribeCard().add(characters.get(13));
        p1.getTribeCard().add(characters.get(13));
        p1.getTribeCard().add(characters.get(13));
        p1.getTribeCard().add(characters.get(13));
        p1.getTribeCard().add(characters.get(18));
        p1.getTribeCard().add(characters.get(18));
        p2.getTribeCard().add(characters.get(18));
        p2.getTribeCard().add(characters.get(18));
        Event e = events.get(2);
        e.resolveEvent(players);
        assertEquals(0, p1.getFood());
        assertEquals(1, p2.getFood());
    }
}
