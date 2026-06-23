package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Model.Cards.Buildings.Building;
import it.polimi.ingsw.Visitors.BuildingVisitor.ConcreteBuildingActivation;
import it.polimi.ingsw.Model.Cards.Buildings.DiscountBuilding;
import it.polimi.ingsw.Model.Cards.Buildings.Events;
import it.polimi.ingsw.Model.Cards.Buildings.Icons;
import it.polimi.ingsw.Model.Cards.Characters.Character;
import it.polimi.ingsw.Model.Cards.Events.Event;
import it.polimi.ingsw.Model.Factory.BuildingFactory;
import it.polimi.ingsw.Model.Factory.ConcreteFactoryEra;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.UI.Printer;
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
    public void discountBuildingTest(){
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
    public void discountBuildingTest1(){
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
        Printer printer = new Printer();
        String[] result = building.print(printer);
        assertNotNull(result);
    }
    @Test
    public void getPPBonusTest(){
        players.add(p1);
        players.add(p2);
        p1.modifyFood(2);
        p2.modifyFood(0);
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
        assertEquals(-4,p1.getPrestigePoints());
        assertEquals(-2,p2.getPrestigePoints());
    }
    @Test
    void acceptActivationForHuntingEvent_Test(){
        players.add(p1);
        players.add(p2);
        p1.modifyFood(10);
        p2.modifyFood(10);
        DiscountBuilding discountBuilding =  new DiscountBuilding(1, 1, 1, -1, 0, Icons.PAINTER, Events.HUNTEREVENT);
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
    void discountBuildingForPaintingEvent_Test(){
        players.add(p1);
        players.add(p2);
        p1.modifyFood(10);
        p2.modifyFood(10);
        DiscountBuilding discountBuilding =  new DiscountBuilding(1, 1, 1, -1, 0, Icons.PAINTER, Events.PAINTINGEVENT);
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
    public void imagePathTest(){
        DiscountBuilding discountBuilding =  new DiscountBuilding(1, 1, 1, -1, 0, Icons.INVENTOR, Events.SUSTENANCEEVENT);
        String result = discountBuilding.getImagePath();
        assertEquals("/images/cards/buildings/DiscountBuilding_sustenanceevent_inventor.png",result);
    }
}
