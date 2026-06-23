package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BonusPPBuildingTest {

    private BonusPPBuilding bonusPPBuilding;
    private Player player;

    @BeforeEach
    public void setup() {
        bonusPPBuilding = new BonusPPBuilding(1, 10);
        player = new Player("Player1", null, 0, null);
    }

    @Test
    public void printTest(){
        Printer printer = new Printer();
        String[] result = bonusPPBuilding.print(printer);
        assertNotNull(result);
    }
    @Test
    public void acceptActivation_ShouldCallVisitor_Test() {
        FakeActivationVisitor visitor = new FakeActivationVisitor();

        bonusPPBuilding.acceptActivation(visitor, player);

        assertTrue(visitor.visitCalled);
        assertEquals(bonusPPBuilding, visitor.building);
        assertEquals(player, visitor.player);
    }

    class FakeActivationVisitor implements ActivationVisitor {
        public boolean visitCalled = false;
        public BonusPPBuilding building;
        public Player player;

        @Override
        public void visit(AddCard addCard, Player player) {

        }

        @Override
        public void visit(BonusStarBuilding bonusStarBuilding, Player player) {

        }

        @Override
        public void visit(BonusFood bonusFood, Player player) {

        }

        @Override
        public void visit(BonusPPBuilding b, Player p) {
            this.visitCalled = true;
            this.building = b;
            this.player = p;
        }

        @Override
        public void visit(DiscountBuilding discountBuilding, Player player) {

        }

        @Override
        public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player) {

        }

        @Override
        public void visit(MultiplicationBuilding multiplicationBuilding, Player player) {

        }

        @Override
        public void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player) {

        }

        @Override
        public void visit(NoMalusBuilding noMalusBuilding, Player player) {

        }

        @Override
        public void visit(SameIconBuilding sameIconBuilding, Player player) {

        }

        @Override
        public void visit(SetBonus setBonus, Player player) {

        }
    }

}
