package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingActivation;
import it.polimi.ingsw.Buildings.BuildingVisitor.ConcreteBuildingActivation;
import it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitor;
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
    Player p1 = new Player("io", Totem.BLACK, 0,null);
    Player p2 = new Player("tu", Totem.ORANGE, 0,null);
    Player p3 = new Player("x", Totem.WHITE, 0,null);
    ConcreteFactoryEra factory = new ConcreteFactoryEra(1);
    ArrayList<Character> characters = factory.createCharacterList();
    ArrayList<Event> events = factory.createEventList();
    BuildingFactory buildingFactory = new BuildingFactory();
    ArrayList<Building> buildings = new ArrayList();

    @Test
    void discountBuildingTest(){
        players.add(p1);
        players.add(p2);
        p1.modifyFood(10);
        p2.modifyFood(10);
        DiscountBuilding discountBuilding =  new DiscountBuilding(1, 1, 1, -1, 0, Icons.PAINTER, Events.SUSTENANCEEVENT);
        final boolean[] visited = {false};
        ConcreteBuildingActivation visitor = new ConcreteBuildingActivation() {
            @Override
            public void visit(DiscountBuilding discountBuilding, Player p) {
                visited[0] = true;
            }
        };
        discountBuilding.acceptActivation(visitor,p1);
        assertTrue(visited[0]);
    }
    @Test
    void discountBuildingTestFail(){
        players.add(p1);
        players.add(p2);
        p1.modifyFood(10);
        p2.modifyFood(10);
        p1.getBuilding().add(new DiscountBuilding(1, 1, 1, -1, 0, Icons.PAINTER, Events.SUSTENANCEEVENT));
        p1.getTribeCard().add(characters.get(13)); //painter
        p1.getTribeCard().add(characters.get(13));
        p1.getTribeCard().add(characters.get(13));
        p1.getTribeCard().add(characters.get(13));
        p1.getTribeCard().add(characters.get(18)); //inventor
        p1.getTribeCard().add(characters.get(18));
        p2.getTribeCard().add(characters.get(18));
        p2.getTribeCard().add(characters.get(18));
        Event e = events.get(2); //sustenance event
        e.resolveEvent(players);
        assertEquals(4, p1.getFood());
        assertEquals(8, p2.getFood());
    }

    @Test
    public void printCardTest(){
        DiscountBuilding building = new DiscountBuilding(1,7,2,1,1,Icons.HUNTER,Events.SUSTENANCEEVENT);
        assertDoesNotThrow(building::printCard);
    }
}
