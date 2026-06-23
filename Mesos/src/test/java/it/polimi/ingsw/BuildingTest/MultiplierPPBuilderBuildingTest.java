package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Model.Cards.Buildings.*;
import it.polimi.ingsw.Visitors.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Visitors.BuildingVisitor.EndGame.EndGameVisitorInterface;
import it.polimi.ingsw.Model.Cards.Characters.Builder;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplierPPBuilderBuildingTest {
    MultiplierPPBuilderBuilding building = new MultiplierPPBuilderBuilding(1,6,4,"BUILDER");
    Player p1 = new Player("X", Totem.BLACK,0,null);

    @Test
    public void countPPTest(){
        p1.getTribeCard().add(new Builder(1,"CHARACTER",2,"BUILDER",-2,1));
        int result = building.countPP(p1);
        p1.modifyPP(result);
        assertEquals(2 ,p1.getPrestigePoints());
    }
    @Test
    public void acceptActivationTest() {
        final boolean[] visitorCalled = {false};

        ActivationVisitor visitor = new ActivationVisitor() {
            @Override public void visit(AddCard addCard, Player player) {}
            @Override public void visit(BonusStarBuilding bonusStarBuilding, Player player) {}
            @Override public void visit(BonusFood bonusFood, Player player) {}
            @Override public void visit(BonusPPBuilding bonusPPBuilding, Player player) {}
            @Override public void visit(DiscountBuilding discountBuilding, Player player) {}
            @Override public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player) {}
            @Override public void visit(MultiplicationBuilding multiplicationBuilding, Player player) {}
            @Override
            public void visit(MultiplierPPBuilderBuilding b, Player p) {
                visitorCalled[0] = true;
            }
            @Override public void visit(NoMalusBuilding noMalusBuilding, Player player) {}
            @Override public void visit(SameIconBuilding sameIconBuilding, Player player) {}
            @Override public void visit(SetBonus setBonus, Player player) {}
        };
        building.acceptActivation(visitor, p1);
        assertTrue(visitorCalled[0]);
    }
    @Test
    public void printCardTest(){
        assertDoesNotThrow(() -> {building.printCard();});
    }

    @Test
    public void printTest() {
        Printer printer = new Printer();
        String[] result = building.print(printer);
        assertNotNull(result);
    }

    @Test
    public void acceptEndGameTest() {
        EndGameVisitorInterface visitor = new EndGameVisitorInterface() {
            @Override public int visit(AddCard addCard, Player player) {return 0;}
            @Override public int visit(BonusStarBuilding bonusStarBuilding, Player player) {return 0;}
            @Override public int visit(BonusFood bonusFood, Player player) {return 0;}
            @Override public int visit(BonusPPBuilding bonusPPBuilding, Player player) {return 0;}
            @Override public int visit(DiscountBuilding discountBuilding, Player player) {return 0;}
            @Override public int visit(DoubleBonusBuilding doubleBonusBuilding, Player player) {return 0;}
            @Override public int visit(MultiplicationBuilding multiplicationBuilding, Player player) {return 0;}
            @Override public int visit(MultiplierPPBuilderBuilding b, Player p) {return 10;}
            @Override public int visit(NoMalusBuilding noMalusBuilding, Player player) {return 0;}
            @Override public int visit(SameIconBuilding sameIconBuilding, Player player) {return 0;}
            @Override public int visit(SetBonus setBonus, Player player) {return 0;}
        };
        int result = building.acceptEndGame(visitor, p1);
        assertEquals(10,result);
    }
}

