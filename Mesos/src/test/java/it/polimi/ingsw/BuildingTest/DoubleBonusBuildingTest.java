package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Model.Cards.Buildings.*;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Shamanic.ShamanicVisitorInterface;
import it.polimi.ingsw.Model.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleBonusBuildingTest {
    private DoubleBonusBuilding building;
    private Player player;
    private ShamanicEvent event;

    @BeforeEach
    public void setup() {
        building = new DoubleBonusBuilding(1, 10, 5);
        player = new Player("TestPlayer", null, 0, null);
        event = new ShamanicEvent(1,"EVENT","SHAMANIC_EVENT",5,-2);
    }
    @Test
    public void printTest(){
        Printer printer = new Printer();
        String[] result = building.print(printer);
        assertNotNull(result);
    }
    @Test
    public void giveDouble_ShouldReturnTwo_Test() {
        assertEquals(2, building.giveDouble());
    }
    @Test
    public void acceptActivation_ShouldCallVisitor_Test() {
        FakeActivationVisitor visitor = new FakeActivationVisitor();
        building.acceptActivation(visitor, player);
        assertTrue(visitor.visitCalled);
    }

    @Test
    public void acceptShamanicEvent_ShouldCallVisitor_Test() {
        FakeShamanicVisitor visitor = new FakeShamanicVisitor();
        building.acceptShamanicEvent(visitor, player, event);
        assertTrue(visitor.visitCalled);
    }

    class FakeActivationVisitor implements ActivationVisitor {
        public boolean visitCalled = false;
        @Override public void visit(AddCard addCard, Player player) {}
        @Override public void visit(BonusStarBuilding bonusStarBuilding, Player player) {}
        @Override public void visit(BonusFood bonusFood, Player player) {}
        @Override public void visit(BonusPPBuilding bonusPPBuilding, Player player) {}
        @Override public void visit(DiscountBuilding discountBuilding, Player player) {}
        @Override public void visit(DoubleBonusBuilding b, Player p) { this.visitCalled = true; }
        @Override public void visit(MultiplicationBuilding multiplicationBuilding, Player player) {}
        @Override public void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player) {}
        @Override public void visit(NoMalusBuilding noMalusBuilding, Player player) {}
        @Override public void visit(SameIconBuilding sameIconBuilding, Player player) {}
        @Override public void visit(SetBonus setBonus, Player player) {}

    }
    class FakeShamanicVisitor implements ShamanicVisitorInterface {
        public boolean visitCalled = false;

        @Override public void visit(AddCard addCard, Player player, ShamanicEvent shamanicEvent) {}
        @Override public void visit(BonusStarBuilding bonusStarBuilding, Player player, ShamanicEvent shamanicEvent) {}
        @Override public void visit(BonusFood bonusFood, Player player, ShamanicEvent shamanicEvent) {}
        @Override public void visit(BonusPPBuilding bonusPPBuilding, Player player, ShamanicEvent shamanicEvent) {}
        @Override public void visit(DiscountBuilding discountBuilding, Player player, ShamanicEvent shamanicEvent) {}
        @Override public void visit(DoubleBonusBuilding b, Player p, ShamanicEvent e) { this.visitCalled = true; }
        @Override public void visit(MultiplicationBuilding multiplicationBuilding, Player player, ShamanicEvent shamanicEvent) {}
        @Override public void visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player, ShamanicEvent shamanicEvent) {}
        @Override public void visit(NoMalusBuilding noMalusBuilding, Player player, ShamanicEvent shamanicEvent) {}
        @Override public void visit(SameIconBuilding sameIconBuilding, Player player, ShamanicEvent shamanicEvent){}
        @Override public void visit(SetBonus setBonus, Player player, ShamanicEvent shamanicEvent) {}
    }
}
