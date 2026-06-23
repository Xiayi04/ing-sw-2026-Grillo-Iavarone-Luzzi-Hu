package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ConcreteBuildingActivation;
//import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor.AddedFoodException;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor.ConcreteSetAndIconVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor.SetAndIconVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.SameIconBuilding;
import it.polimi.ingsw.Model.Cards.Characters.Character;
import it.polimi.ingsw.Model.Cards.Characters.Inventor;
import it.polimi.ingsw.Factory.ConcreteFactoryEra;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class SameIconBuildingTest {


    //TestDescriptor.Visitor v = new ConcreteBuildingActivation();
    Player p = new Player("io", Totem.BLACK, 2,null);
    ConcreteFactoryEra factory = new ConcreteFactoryEra(1);
    SameIconBuilding b = new SameIconBuilding(1, 1, 1);
    ActivationVisitor v= new ConcreteBuildingActivation();
    @Test
    void acceptTest(){
        ArrayList<Character> characters = factory.createCharacterList();
        p.getTribeCard().add(characters.get(18));
        p.getTribeCard().add(characters.get(18));
        p.getTribeCard().add(characters.get(19));
        p.getTribeCard().add(characters.get(20));
        //il visitor legge gli inventor del player
        b.acceptActivation(v, p);
        assertEquals(-1,b.getCheckPair().get("leather"));
        assertEquals(1,b.getCheckPair().get("tree"));
        assertEquals(1,b.getCheckPair().get("boat"));
    }

    @Test
    void constructorTest() {
        SameIconBuilding b = new SameIconBuilding(1, 1, 1);

        assertEquals(0, b.getCheckPair().get("boat"));
        assertEquals(0, b.getCheckPair().get("tree"));
        assertEquals(0, b.getCheckPair().get("hook"));
        assertEquals(0, b.getCheckPair().get("necklace"));
        assertEquals(0, b.getCheckPair().get("bowl"));
        assertEquals(0, b.getCheckPair().get("knot"));
        assertEquals(0, b.getCheckPair().get("doll"));
        assertEquals(0, b.getCheckPair().get("flute"));
        assertEquals(0, b.getCheckPair().get("leather"));
        assertEquals(0, b.getCheckPair().get("bread"));
    }

    @Test
    void giveFoodBonusTestNotwell(){
        SameIconBuilding b = new SameIconBuilding(1,1,1);
        Player p = new Player("nana", Totem.BLACK, 0, null);
        boolean result = b.giveFoodBonus(p, "boat");
        assertFalse(result);
        assertEquals(0,p.getFood());
        assertEquals(1,b.getCheckPair().get("boat"));

    }
    @Test
    void giveFoodBonusTestGreat(){
        SameIconBuilding b = new SameIconBuilding(1,1,1);
        Player p = new Player("nana", Totem.BLACK, 0, null);
        b.giveFoodBonus(p, "boat");
        boolean result = b.giveFoodBonus(p, "boat");
        assertTrue(result);
        assertEquals(3,p.getFood());
        assertEquals(-1,b.getCheckPair().get("boat"));

    }
    @Test
    void addInventorIconTest(){
        SameIconBuilding b = new SameIconBuilding(1, 1, 1);
        b.addInventorIconToMap("");
        assertEquals(0, b.getCheckPair().get("boat"));
        assertEquals(0, b.getCheckPair().get("tree"));
        b.addInventorIconToMap("tree");
        assertEquals(1, b.getCheckPair().get("tree"));
        b.addInventorIconToMap("boat");
        assertEquals(1, b.getCheckPair().get("boat"));
        b.addInventorIconToMap("boat");
        assertEquals(-1,b.getCheckPair().get("boat"));
    }
    @Test
    void addInventorIconExceptionTest() {
        SameIconBuilding b = new SameIconBuilding(1, 1, 1);
        assertThrows(RuntimeException.class, () -> b.addInventorIconToMap("invalid"));
    }

    @Test
    void acceptActivationAndVisitorTest() {
        ActivationVisitor v = new ConcreteBuildingActivation();

        Player p = new Player("io", Totem.BLACK, 2, null);
        SameIconBuilding b = new SameIconBuilding(1, 1, 1);

        p.getTribeCard().add(new Inventor(1, "CHARACTER", 5, "INVENTOR", "leather"));
        p.getTribeCard().add(new Inventor(1, "CHARACTER", 5, "INVENTOR", "leather"));
        p.getTribeCard().add(new Inventor(1, "CHARACTER", 5, "INVENTOR", "tree"));
        p.getTribeCard().add(new Inventor(1, "CHARACTER", 5, "INVENTOR", "boat"));

        b.acceptActivation(v, p);

        assertEquals(-1, b.getCheckPair().get("leather"));
        assertEquals(1, b.getCheckPair().get("tree"));
        assertEquals(1, b.getCheckPair().get("boat"));
    }

    @Test
    void acceptSameIconBonusVisitorTest() {
        SameIconBuilding building = new SameIconBuilding(1, 1, 1);
        Player player = new Player("p1", Totem.BLACK, 0, null);
        SetAndIconVisitor visitor = new ConcreteSetAndIconVisitor();

        building.acceptSameIconBonus(visitor, player, "boat");

        assertEquals(1, building.getCheckPair().get("boat"));
        assertEquals(0, player.getFood());

        assertThrows(AddedFoodException.class, () ->
                building.acceptSameIconBonus(visitor, player, "boat")
        );


        assertEquals(-1, building.getCheckPair().get("boat"));
        assertEquals(3, player.getFood());
    }
    @Test
    void printTest() {
        SameIconBuilding b= new SameIconBuilding(1, 1, 1);

        Printer printer = new Printer();

        String[] result = b.print(printer);

        assertNotNull(result);

    }
    @Test
    void giveFoodAtSecondSameIcon() {
        SameIconBuilding building = new SameIconBuilding(1, 1, 1);
        Player player = new Player("p1", Totem.BLACK, 0, null);

        boolean first = building.giveFoodBonus(player, "boat");
        boolean second = building.giveFoodBonus(player, "boat");

        assertFalse(first);
        assertTrue(second);
        assertEquals(-1, building.getCheckPair().get("boat"));
        assertEquals(3, player.getFood());
    }



}
