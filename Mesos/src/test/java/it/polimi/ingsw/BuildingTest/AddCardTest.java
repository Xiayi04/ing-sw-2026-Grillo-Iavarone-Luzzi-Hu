package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Model.Cards.Buildings.*;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ConcreteBuildingActivation;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SpecialBuildings.AddCardException;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SpecialBuildings.AddCardVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SpecialBuildings.AddCardVisitorInterface;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddCardTest {
    @Test
    public void acceptActivationTest() {
        AddCard b = new AddCard(1, 1, 1);
        Player p = new Player("X", Totem.BLACK,0,null);
        ActivationVisitor visitor = new ConcreteBuildingActivation(){
            @Override
            public void visit(AddCard building, Player player) {
                if (player == null) throw new AddCardException("Player cannot be null");
            }
        };
        assertDoesNotThrow(() -> b.acceptActivation(visitor, p));
        assertThrows(AddCardException.class, () -> {
            b.acceptActivation(visitor, null);
        });
    }

    @Test
    public void printTest(){
        AddCard b = new AddCard(1, 1, 1);
        Printer printer = new Printer();
        String[] result = b.print(printer);
        assertNotNull(result);
    }

    @Test
    public void addArrowTest(){
        AddCard addCard = new AddCard(1, 1, 1);
        int result = addCard.addArrow();
        assertEquals(1, result);
    }
    @Test
    void acceptAddCardTest() {
        AddCard addCard = new AddCard(1, 2, 3);
        Player player = new Player("p1", Totem.BLACK, 0, null);
        AddCardVisitorInterface visitor = new AddCardVisitor();

        int succeded = addCard.acceptAddCard(visitor, player);
        assertEquals(1, succeded);
    }



}
