package it.polimi.ingsw.GameTest;

import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.Builder;
import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Cards.Characters.Inventor;
import it.polimi.ingsw.Cards.Characters.Painter;
import it.polimi.ingsw.Factory.ConcreteFactoryEra;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.Cards.Characters.InventorIcon.TREE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals(0, p1.getFood());
        assertEquals(-1, p1.getPrestigePoints());
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
    public  void InventorBonusPlayerTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0,null);
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "BOWL"));
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "BOWL"));
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "BOWL"));
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "BOWL"));
        assertEquals(4, p1.inventorBonus());
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "TREE"));
        assertEquals(10, p1.inventorBonus());
        p1.getTribeCard().add(new Inventor(1, "CHARACTER",5, "INVENTOR",  "BOAT"));
        assertEquals(18, p1.inventorBonus());

    }

    @Test
    public void BuilderBonusPlayerTest() {
        Player p1 = new Player("p1", Totem.BLACK, 0,null);

        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3,5));
        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3,5));
        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3,5));
        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3,5));

        assertEquals(20, p1.builderBonus());
    }

    @Test
    public void countTribeCardByIcon(){
        Player p1 = new Player("p1", Totem.BLACK, 0,null);

        p1.getTribeCard().add( new Builder(1, "CHARACTER", 5,"BUILDER", 3,5));
        p1.getTribeCard().add(new Builder(1, "CHARACTER", 5, "BUILDER", 3,5));
        p1.getTribeCard().add(new Painter(1,"CHARACTER", 5, "PAINTER"));
        p1.getTribeCard().add(new Painter(1, "CHARACTER", 5, "PAINTER"));
        assertEquals(2, p1.countTribeCardsByIcon("PAINTER"));
        assertEquals(2, p1.countTribeCardsByIcon("BUILDER"));
        assertEquals(0, p1.countTribeCardsByIcon("HUNTER"));
    }

    @Test
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

    }


}
