package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Model.Cards.Buildings.*;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SpecialBuildings.TurnOrderCardFoodBonus;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BonusFoodTest {
    private BonusFood bonusFood;
    private Player player;

    @BeforeEach
    public void setup() {
        bonusFood = new BonusFood(1, 3, 3);
        player = new Player("TestPlayer", null, 0, null);
    }

    @Test
    public void giveExtraFood_ShouldReturnOne_Test() {
        int result = bonusFood.giveExtraFood();
        assertEquals(1, result);
    }
    @Test
    public void printTest() {
        Printer printer = new Printer();
        String[] result = bonusFood.print(printer);
        assertNotNull(result);
    }

    @Test
    public void acceptActivation_ShouldCallVisitor_Test() {
        FakeActivationVisitor visitor = new FakeActivationVisitor();
        bonusFood.acceptActivation(visitor, player);

        assertTrue(visitor.visitCalled);
        assertEquals(bonusFood, visitor.building);
        assertEquals(player, visitor.player);
    }

    @Test
    public void acceptFoodBonus_ShouldCallVisitor_Test() {
        FakeFoodBonusVisitor visitor = new FakeFoodBonusVisitor();

        bonusFood.acceptFoodBonus(visitor, player);

        assertTrue(visitor.visitCalled);
    }

    class FakeActivationVisitor implements ActivationVisitor {
        public boolean visitCalled = false;
        public BonusFood building;
        public Player player;

        @Override public void visit(AddCard addCard, Player player) {}
        @Override public void visit(BonusStarBuilding bonusStarBuilding, Player player) {}
        @Override
        public void visit(BonusFood b, Player p) {
            this.visitCalled = true;
            this.building = b;
            this.player = p;
        }
        @Override public void visit(BonusPPBuilding bonusPPBuilding, Player player) {}
        @Override public void visit(DiscountBuilding discountBuilding, Player player) {}
        @Override public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player) {}
        @Override public void visit(MultiplicationBuilding multiplicationBuilding, Player player) {}
        @Override public void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player) {}
        @Override public void visit(NoMalusBuilding noMalusBuilding, Player player) {}
        @Override public void visit(SameIconBuilding sameIconBuilding, Player player) {}
        @Override public void visit(SetBonus setBonus, Player player) {}
    }

    class FakeFoodBonusVisitor implements TurnOrderCardFoodBonus {
        public boolean visitCalled = false;

        @Override public void visit(AddCard addCard, Player player) {}
        @Override public void visit(BonusStarBuilding bonusStarBuilding, Player player) {}
        @Override public void visit(BonusFood b, Player p) {
            this.visitCalled = true;
        }
        @Override public void visit(BonusPPBuilding bonusPPBuilding, Player player) {}
        @Override public void visit(DiscountBuilding discountBuilding, Player player) {}
        @Override public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player) {}
        @Override public void visit(MultiplicationBuilding multiplicationBuilding, Player player) {}
        @Override public void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player) {}
        @Override public void visit(NoMalusBuilding noMalusBuilding, Player player) {}
        @Override public void visit(SameIconBuilding sameIconBuilding, Player player) {}
        @Override public void visit(SetBonus setBonus, Player player) {}
    }
}