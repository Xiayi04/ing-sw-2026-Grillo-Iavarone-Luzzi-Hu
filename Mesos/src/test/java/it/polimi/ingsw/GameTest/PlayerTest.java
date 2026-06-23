package it.polimi.ingsw.GameTest;

import it.polimi.ingsw.Model.Cards.Buildings.BonusFood;
import it.polimi.ingsw.Model.Cards.Buildings.BonusStarBuilding;
import it.polimi.ingsw.Model.Cards.Buildings.Building;
import it.polimi.ingsw.Model.Cards.Buildings.MultiplicationBuilding;
import it.polimi.ingsw.Model.Cards.Characters.Builder;
import it.polimi.ingsw.Model.Cards.Characters.Inventor;
import it.polimi.ingsw.Model.Cards.Characters.Painter;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void PlayerConstructorTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0,null);
        assertEquals("p1",  p1.getName());
        assertEquals(Totem.BLACK, p1.getTotem());
        assertEquals(0,p1.getFood());
        assertEquals(0,p1.getPrestigePoints());
        assertTrue(p1.getTribeCard().isEmpty());
        assertTrue(p1.getBuilding().isEmpty());
        assertEquals(0, p1.getStarCounter());
        assertEquals(0, p1.getStarCounter());
        assertEquals(0, p1.getBuilderDiscount());
        assertNull(p1.getVirtualClient());
    }
    @Test
    public void PlayerFoodModifyTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0,null);
        p1.modifyFood(25);
        p1.modifyFood(25);
        p1.modifyFood(25);
        p1.modifyFood(25);
        p1.modifyFood(-99);
        assertEquals(1, p1.getFood());
        p1.modifyFood(-2);
        p1.modifyPP(-1);
        assertEquals(0, p1.getFood());
        assertEquals(-3, p1.getPrestigePoints());
    }

    @Test
    public void PlayerPrestigePointsModifyTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0,null);
        p1.modifyPP(1000);
        p1.modifyPP(-100);
        p1.modifyPP(-100);
        p1.modifyPP(-100);
        p1.modifyPP(-100);
        p1.modifyPP(-100);
        assertEquals(500, p1.getPrestigePoints());
    }
    @Test
    public void settersAndGettersCounterTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0, null);

        p1.setHunterCounter(1);
        p1.setBuilderCounter(2);
        p1.setPickerCounter(3);
        p1.setPainterCounter(4);
        p1.setInventorCounter(5);
        p1.setShamanCounter(6);

        assertEquals(1, p1.getHunterCounter());
        assertEquals(2, p1.getBuilderCounter());
        assertEquals(3, p1.getPickerCounter());
        assertEquals(4, p1.getPainterCounter());
        assertEquals(5, p1.getInventorCounter());
        assertEquals(6, p1.getShamanCounter());
    }
    @Test
    public  void InventorBonusPlayerTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0,null);
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "BOWL"));
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "BOWL"));
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "BOWL"));
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "BOWL"));
        p1.setInventorCounter(4);
        assertEquals(4, p1.inventorBonus());
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "TREE"));
        p1.setInventorCounter(5);
        assertEquals(10, p1.inventorBonus());
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "BOAT"));
        p1.setInventorCounter(6);
        assertEquals(18, p1.inventorBonus());

    }
    @Test
    public void painterBonusPlayerTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0, null);

        p1.setPainterCounter(0);
        assertEquals(0, p1.painterBonus());

        p1.setPainterCounter(1);
        assertEquals(0, p1.painterBonus());

        p1.setPainterCounter(2);
        assertEquals(10, p1.painterBonus());

        p1.setPainterCounter(5);
        assertEquals(20, p1.painterBonus());
    }

    @Test
    public void BuilderBonusPlayerTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0,null);
//
        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3,5));
        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3,5));
        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3,5));
        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3,5));

        assertEquals(20, p1.builderBonus());
    }

    @Test
    public void countTribeCardByIconTest(){
        Player p1 = new Player("p1", Totem.BLACK, 0,null);
        p1.setPainterCounter(2);
        p1.setBuilderCounter(2);
        p1.setHunterCounter(0);
        p1.getTribeCard().add( new Builder(1, "CHARACTER", 5,"BUILDER", 3,5));
        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3,5));
        p1.getTribeCard().add(new Painter(1,"CHARACTER", 5, "PAINTER"));
        p1.getTribeCard().add(new Painter(1, "CHARACTER", 5, "PAINTER"));
        assertEquals(2, p1.countTribeCardsByIcon("PAINTER"));
        assertEquals(2, p1.countTribeCardsByIcon("BUILDER"));
        assertEquals(0, p1.countTribeCardsByIcon("HUNTER"));
    }

  /*  @Test
    public void countSetPlayerTest(){
        ConcreteFactoryEra factoryEra = new ConcreteFactoryEra(1);
        Player p1 = new Player("p1", Totem.BLACK, 0,null);
        for(int i=0; i<2;i++){
            p1.getTribeCard().add(factoryEra.createPicker(1,5));
            p1.getTribeCard().add(factoryEra.createShaman(1,5,3));
            p1.getTribeCard().add(factoryEra.createInventor(1,5,"TREE"));
            p1.getTribeCard().add(factoryEra.createHunter(1,5,false));
            p1.getTribeCard().add(factoryEra.createPainter(1, 5));
            p1.getTribeCard().add(new Builder(1, "CHARACTER", 5,"BUILDER", 3,5));
        }
        p1.getTribeCard().add(factoryEra.createPainter(1, 5));
        p1.getTribeCard().add(factoryEra.createHunter(1,5,false));

        assertEquals(2,p1.countSet());

    }*/
  @Test
  public void countSetPlayerTest() {
      Player p1 = new Player("p1", Totem.BLACK, 0, null);

      p1.setInventorCounter(2);
      p1.setBuilderCounter(2);
      p1.setHunterCounter(3);
      p1.setPainterCounter(3);
      p1.setPickerCounter(2);
      p1.setShamanCounter(2);

      assertEquals(2, p1.countSet());
  }

    @Test
    public void buildingBonusShouldSumBuildingPP() {
        Player p1 = new Player("p1", Totem.BLACK, 0, null);

        Building b1 = new BonusFood(1, 3, 3);
        Building b2 = new BonusFood(2, 6, 4);

        p1.getBuilding().add(b1);
        p1.getBuilding().add(b2);

        assertEquals(b1.getPP() + b2.getPP(), p1.buildingBonus());
    }

    @Test
    public void buildingMultipliedBonusShouldReturnZeroWithoutMultiplicationBuildings() {
        Player p1 = new Player("p1", Totem.BLACK, 0, null);

        Building b1 = new BonusFood(1, 3, 3);
        p1.getBuilding().add(b1);

        assertEquals(0, p1.buildingMultipliedBonus());
    }

    @Test
    public void buildingMultipliedBonusShouldUseMultiplicationBuilding() {
        Player p1 = new Player("p1", Totem.BLACK, 0, null);

        MultiplicationBuilding b1 =
                new MultiplicationBuilding(1, 6, 6, "INVENTOR", 2);

        p1.setInventorCounter(3);
        p1.getBuilding().add(b1);

        assertEquals(b1.countPP(p1), p1.buildingMultipliedBonus());
    }

    @Test
    public void finalScoreShouldSumAllBonuses() {
        Player p1 = new Player("p1", Totem.BLACK, 0, null);

        p1.modifyPP(10);

        p1.setPainterCounter(4);
        p1.setInventorCounter(2);
        p1.setBuilderCounter(2);

        p1.getTribeCard().add(new Inventor(1, "CHARACTER", 5, "INVENTOR", "BOWL"));
        p1.getTribeCard().add(new Inventor(1, "CHARACTER", 5, "INVENTOR", "TREE"));

        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3, 5));
        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3, 5));

        Building b1 = new BonusFood(1, 3, 3);
        p1.getBuilding().add(b1);

        int expected =
                p1.getPrestigePoints()
                        + p1.inventorBonus()
                        + p1.painterBonus()
                        + p1.builderBonus()
                        + p1.buildingBonus()
                        + p1.buildingMultipliedBonus();

        assertEquals(expected, p1.finalScore());
    }

    @Test
    void buildingBonusShouldSumPPOfAllBuildings() {
        Player p1 = new Player("p1", Totem.BLACK, 0, null);

        Building b1 = new BonusFood(1, 3, 3);
        Building b2 = new BonusStarBuilding(2, 6, 4);

        p1.getBuilding().add(b1);
        p1.getBuilding().add(b2);

        int expected = b1.getPP() + b2.getPP();

        assertEquals(expected, p1.buildingBonus());
    }
    @Test
    void buildingBonusTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0, null);

        Building b1 = new BonusFood(1, 3, 3);
        Building b2 = new BonusStarBuilding(2, 6, 4);

        p1.getBuilding().add(b1);
        p1.getBuilding().add(b2);

        int result = p1.buildingBonus();

        assertEquals(b1.getPP() + b2.getPP(), result);
    }
    @Test
    void buildingMultipliedBonusCoverageTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0, null);

        Building normalBuilding = new BonusFood(1, 3, 3);

        MultiplicationBuilding multiplicationBuilding =  new MultiplicationBuilding(1, 6, 6, "INVENTOR", 2);

        p1.setInventorCounter(3);

        p1.getBuilding().add(normalBuilding);
        p1.getBuilding().add(multiplicationBuilding);

        int result = p1.buildingMultipliedBonus();

        assertEquals(multiplicationBuilding.countPP(p1), result);
    }
    @Test
    void buildingBonusCoverageTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0, null);

        Building b1 = new BonusFood(1, 3, 3);
        Building b2 = new BonusStarBuilding(2, 6, 4);

        p1.getBuilding().add(b1);
        p1.getBuilding().add(b2);

        int result = p1.buildingBonus();

        assertEquals(b1.getPP() + b2.getPP(), result);
    }


    @Test
    void buildinggMultipliedBonusCoverageTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0, null);

        Building normalBuilding = new BonusFood(1, 3, 3);
        MultiplicationBuilding multiplicationBuilding =
                new MultiplicationBuilding(1, 6, 6, "INVENTOR", 2);

        p1.setInventorCounter(3);

        p1.getBuilding().add(normalBuilding);
        p1.getBuilding().add(multiplicationBuilding);

        int result = p1.buildingMultipliedBonus();

        assertEquals(multiplicationBuilding.countPP(p1), result);
    }




}
