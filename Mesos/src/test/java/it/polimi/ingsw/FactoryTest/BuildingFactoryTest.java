package it.polimi.ingsw.FactoryTest;
import it.polimi.ingsw.Factory.BuildingFactory;
import it.polimi.ingsw.Model.Cards.Buildings.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingFactoryTest {
//test
    @Test
     void testCreateBonusPPBuilding() {
         BuildingFactory factory = new BuildingFactory();
         Building bb = factory.createBonusPPBuilding(3, 25);

         assertNotNull(bb);
         assertTrue(bb instanceof BonusPPBuilding);
         BonusPPBuilding b = (BonusPPBuilding) bb;
        //test da pasasare
        assertEquals(3, b.getEra());
        assertEquals(25, b.getPrice());
        //test fallito
       // assertNotEquals(3, b.getEra());
       // assertNotEquals(25, b.getPrice());


     }

    /*@Test
    void testCreateMultiplicationBuilding() {
        BuildingFactory factory = new BuildingFactory();
        Building building = factory.createMultiplicationBuilding(9, 6, 6, "PICKER", 2);

        assertNotNull(building);
        assertTrue(building instanceof MultiplicationBuilding);
        MultiplicationBuilding b = (MultiplicationBuilding) building;

        assertEquals(9, b.getEra());
        assertEquals(6, b.getPrice());
        assertEquals(6, b.getPP());
        assertEquals(Icons.PICKER, b.getTypeIcons());
        assertEquals(2, b.getMultiplier());

         //test inverso: test che deve dare errore
       /* assertNotEquals(9,b.getEra());
        assertNotEquals(6, b.getPrice());
        assertNotEquals(Icons.PICKER, b.getTypeIcons());
        assertNotEquals(2, b.getMultiplier());


    }*/
    /*@Test
    void testMultiplierPPBuilderBuilding() {
        BuildingFactory factory = new BuildingFactory();
        Building building = factory.createMultiplierPPBuilderBuilding(1,6,4);
        assertNotNull(building);
        assertTrue(building instanceof MultiplierPPBuilderBuilding);
        MultiplierPPBuilderBuilding b = (MultiplierPPBuilderBuilding) building;

        assertEquals(1, b.getEra());
        assertEquals(6, b.getPrice());
        assertEquals(4, b.getPP());
        assertEquals(Icons.BUILDER, b.getTypeIcons());
        //esempio test errato
       // assertEquals(Icons.PICKER, b.getTypeIcons());//mi aspetto un Icons.BUILDER



    }*/

    @Test
    void testDiscountBuilding() {
        BuildingFactory factory = new BuildingFactory();
        Building bb = factory.createDiscountBuilding(1,7,4,0,1,Icons.PICKER,  Events.SUSTENANCEEVENT);
        assertNotNull(bb);
        assertTrue(bb instanceof DiscountBuilding);
        DiscountBuilding b = (DiscountBuilding) bb;
        assertEquals(1, b.getEra());
        assertEquals(7, b.getPrice());
        assertEquals(4, b.getPP());
        assertEquals(0, b.getFoodBonus());
        assertEquals(1, b.getPpBonus());
        assertEquals(Icons.PICKER, b.getTypeIcons());
        assertEquals(Events.SUSTENANCEEVENT, b.getTypeEvents());

        //esempio test errato
   //    assertNotEquals(Icons.PICKER, b.getTypeIcons());
   //    assertNotEquals(Events.SUSTENANCEEVENT, b.getTypeEvents());


    }
    @Test
    void testNoMalusBuilding() {
        BuildingFactory factory = new BuildingFactory();
        Building bb = factory.createNoMalusBuilding(1,5,2);
        assertNotNull(bb);
        assertTrue(bb instanceof NoMalusBuilding); //assicuro che mi restituisca il tipo giusto
        NoMalusBuilding testbuilding = (NoMalusBuilding) bb;
        assertEquals(1, testbuilding.getEra());
        assertEquals(5, testbuilding.getPrice());
        assertEquals(2, testbuilding.getPP());

        //test controesempio
        assertNotEquals(5, testbuilding.getEra()); //infatti deve essere 1

    }
    @Test
    void DoubleBonusBuilding() {
        BuildingFactory factory = new BuildingFactory();
        Building bb = factory.createDoubleBonusBuilding(2,7,2);
        assertNotNull(bb);
        assertTrue(bb instanceof DoubleBonusBuilding );
        DoubleBonusBuilding b = (DoubleBonusBuilding) bb;
        assertEquals(2, b.getEra());
        assertEquals(7, b.getPrice());
        assertEquals(2, b.getPP());
        //controesempio
        assertNotEquals(5, b.getEra());

    }
    @Test
    void BonusStarsBuilding() {
        BuildingFactory factory = new BuildingFactory();
        Building bb = factory.createBonusStarsBuilding(2,6,4);
        assertNotNull(bb);
        assertTrue(bb instanceof BonusStarBuilding);
        BonusStarBuilding b = (BonusStarBuilding) bb;
        assertEquals(2, b.getEra());
        assertEquals(6, b.getPrice());
        assertEquals(4, b.getPP());
        //controesempio
        assertNotEquals(3, b.getEra());

    }
    @Test
    void AddCard() {
        BuildingFactory factory = new BuildingFactory();
        Building bb = factory.createAddCard(3,9,3);
        assertNotNull(bb);
        assertTrue(bb instanceof AddCard);
        AddCard b = (AddCard) bb;
        assertEquals(3, b.getEra());
        assertEquals(9, b.getPrice());
        assertEquals(3, b.getPP());
        //controesempio
        assertNotEquals(5, b.getPP());

    }
    @Test
    void BonusFoodBuilding(){
        BuildingFactory factory = new BuildingFactory();
        Building bb = factory.createBonusFood(1,3,3);
        assertNotNull(bb);
        assertTrue(bb instanceof BonusFood);
        BonusFood b = (BonusFood) bb;
        assertEquals(1, b.getEra());
        assertEquals(3, b.getPrice());
        assertEquals(3, b.getPP());
        //controesempio

        assertNotEquals(5, b.getEra());


    }
    @Test
    void SetBonusBuilding(){
         BuildingFactory factory = new BuildingFactory();
         Building bb = factory.createSetBonus(1,4,3);
         assertNotNull(bb);
         assertTrue(bb instanceof SetBonus);
         SetBonus b = (SetBonus) bb;
         assertEquals(1, b.getEra());
         assertEquals(4, b.getPrice());
         assertEquals(3, b.getPP());

         //controesempio
         assertNotEquals(9, b.getPP());


    }
    @Test
    void SameIconBuilding() {
        BuildingFactory factory = new BuildingFactory();
        Building bb = factory.createSameIconBuilding(1,3,4);
        assertNotNull(bb);
        assertTrue(bb instanceof SameIconBuilding);
        SameIconBuilding b = (SameIconBuilding) bb;
        assertEquals(1, b.getEra());
        assertEquals(3, b.getPrice());
        assertEquals(4, b.getPP());

        //controesempio
        assertNotEquals(9, b.getPP());

    }


}
