package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ConcreteBuildingActivation;
import it.polimi.ingsw.Model.Cards.Buildings.SameIconBuilding;
import it.polimi.ingsw.Model.Cards.Buildings.SetBonus;
import it.polimi.ingsw.Factory.ConcreteFactoryEra;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SetBonusTest {
    Player p1 = new Player("io", Totem.BLACK, 2,null);
    ConcreteFactoryEra factory = new ConcreteFactoryEra(1);
    SameIconBuilding b = new SameIconBuilding(1, 1, 1);
    ActivationVisitor v= new ConcreteBuildingActivation();

    @Test
    void constructorTest(){
        SetBonus building = new SetBonus(1, 2, 3);

        assertEquals(0, building.getFullSetCounter());
        assertEquals(1, building.getEra());
        assertEquals(2, building.getPrice());
        assertEquals(3, building.getPP());
        assertEquals("SetBonus", building.getName());
        building. setFullSetCounter(4);
        assertEquals(4,building.getFullSetCounter());
    }

    @Test
    void giveNoFoodWhenSetIsNotCompleted() {
        SetBonus building = new SetBonus(1, 1, 1);
        Player player = new Player("p1", Totem.BLACK, 0, null);
        boolean result = building.giveExtraFoodSet(player);
        assertFalse(result);
        assertEquals(0, player.getFood());
        assertEquals(0, building.getFullSetCounter());
    }
    @Test
    void giveFoodWhenSetIsCompleted() {
        SetBonus building = new SetBonus(1, 1, 1);
        Player player = new Player("p1", Totem.BLACK, 0, null);
        player.setInventorCounter(2);
        player.setBuilderCounter(2);
        player.setHunterCounter(2);
        player.setPainterCounter(2);
        player.setPickerCounter(2);
        player.setShamanCounter(2);
        boolean result = building.giveExtraFoodSet(player);

        assertTrue(result);
        assertEquals(10, player.getFood());
        assertEquals(2, building.getFullSetCounter());
    }
    @Test
    void printTest() {
        SetBonus b = new SetBonus(1, 1, 1);
        Printer printer = new Printer();
        String[] result = b.print(printer);
        assertNotNull(result);

    }
    @Test
    void setterMethodTest() {
        SetBonus building = new SetBonus(1, 1, 1);
        building.setFullSetCounter(3);
        assertEquals(3, building.getFullSetCounter());
    }
    @Test
    void acceptActivationAndVisitorTest(){
        ActivationVisitor v = new ConcreteBuildingActivation();
        Player p = new Player("io", Totem.BLACK, 2, null);
        SetBonus b = new SetBonus(1, 1, 1);
        assertDoesNotThrow(()-> b.acceptActivation(v, p));
    }



}
