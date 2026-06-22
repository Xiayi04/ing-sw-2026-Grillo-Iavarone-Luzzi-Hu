package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Buildings.BuildingVisitor.ConcreteBuildingActivation;
import it.polimi.ingsw.Buildings.BuildingVisitor.EndGame.EndGameVisitorInterface;
import it.polimi.ingsw.Cards.Characters.Builder;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.AddAndCountCharacter;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Cards.Characters.Hunter;
import it.polimi.ingsw.Factory.ConcreteFactoryEra;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplicationBuildingTest {

    Player p = new Player("io", Totem.BLACK, 0,null);
    ConcreteFactoryEra factory = new ConcreteFactoryEra(1);
    ArrayList<Character> characters = factory.createCharacterList();

    @Test
    void testCountPP_WithSets() {
        Player stubPlayer = new Player("player1", Totem.BLACK, 0, null) {
            @Override
            public int countSet() {
                return 1;
            }
        };
        MultiplicationBuilding b = new MultiplicationBuilding(1, 5, 6, "SET", 6);
        int result = b.countPP(stubPlayer);
        assertEquals(6, result);
    }

    @Test
    void testCountPP_WithIcons() {
        Player p1 = new Player("player1", Totem.BLACK, 0, null);
        p1.getTribeCard().add(new Hunter(1,"CHARACTER",3,"HUNTER",false));
        MultiplicationBuilding b = new MultiplicationBuilding(1, 5, 6, "HUNTER", 6);
        int result = b.countPP(p1);
        assertEquals(0, result);
    }

    @Test
    void countPPIconTest(){
        p.getTribeCard().add(characters.get(1));
        p.getTribeCard().add(characters.get(1));
        p.getTribeCard().add(characters.get(1));
        MultiplicationBuilding b = new MultiplicationBuilding(1, 6, 3, "BUILDER", 4);
        p.modifyPP(6);
        assertEquals(6, p.getPrestigePoints());
    }

    @Test
    public void printTest(){
        MultiplicationBuilding b = new MultiplicationBuilding(1, 5, 6, "SET", 6);
        assertDoesNotThrow(b::printCard);
    }
    @Test
    public void acceptActivation_CallsVisitor_Test() {
        MultiplicationBuilding b = new MultiplicationBuilding(1, 5, 6, "SET", 6);
        Player p1 = new Player("player1", Totem.BLACK, 0, null);
        ActivationVisitorFake visitorFake = new ActivationVisitorFake();
        b.acceptActivation(visitorFake, p1);

        assertTrue(visitorFake.visitCalled);
        assertEquals(b, visitorFake.building);
        assertEquals(p1, visitorFake.player);
    }

    @Test
    public void acceptEndGame_ReturnsVisitorValue_Test() {
        MultiplicationBuilding b = new MultiplicationBuilding(1, 5, 6, "SET", 6);
        Player player = new Player("player1", Totem.BLACK, 0, null);
        EndGameVisitorFake visitorFake = new EndGameVisitorFake();
        int result = b.acceptEndGame(visitorFake, player);

        assertTrue(visitorFake.visitCalled);
        assertEquals(5, result);
    }

    class ActivationVisitorFake implements ActivationVisitor {
        public boolean visitCalled = false;
        public Building building;
        public Player player;

        @Override public void visit(AddCard addCard, Player player) {}

        @Override
        public void visit(BonusStarBuilding bonusStarBuilding, Player player) {

        }

        @Override
        public void visit(BonusFood bonusFood, Player player) {

        }

        @Override
        public void visit(BonusPPBuilding bonusPPBuilding, Player player) {

        }

        @Override
        public void visit(DiscountBuilding discountBuilding, Player player) {

        }

        @Override
        public void visit(DoubleBonusBuilding doubleBonusBuilding, Player player) {

        }

        @Override
        public void visit(MultiplicationBuilding b, Player p) {
            this.visitCalled = true;
            this.building = b;
            this.player = p;
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

    class EndGameVisitorFake implements EndGameVisitorInterface {
        public boolean visitCalled = false;
        public int returnValue = 5;

        @Override
        public int visit(AddCard addCard, Player player) {
            return 0;
        }

        @Override
        public int visit(BonusStarBuilding bonusStarBuilding, Player player) {
            return 0;
        }

        @Override
        public int visit(BonusFood bonusFood, Player player) {
            return 0;
        }

        @Override
        public int visit(BonusPPBuilding bonusPPBuilding, Player player) {
            return 0;
        }

        @Override
        public int visit(DiscountBuilding discountBuilding, Player player) {
            return 0;
        }

        @Override
        public int visit(DoubleBonusBuilding doubleBonusBuilding, Player player) {
            return 0;
        }

        @Override
        public int visit(MultiplicationBuilding b, Player p) {
            this.visitCalled = true;
            return returnValue;
        }

        @Override
        public int visit(MultiplierPPBuilderBuilding multiplierPPBuilderBuilding, Player player) {
            return 0;
        }

        @Override
        public int visit(NoMalusBuilding noMalusBuilding, Player player) {
            return 0;
        }

        @Override
        public int visit(SameIconBuilding sameIconBuilding, Player player) {
            return 0;
        }

        @Override
        public int visit(SetBonus setBonus, Player player) {
            return 0;
        }
    }
}
