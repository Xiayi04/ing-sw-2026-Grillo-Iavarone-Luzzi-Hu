package it.polimi.ingsw.CardsTest;

import it.polimi.ingsw.Model.Cards.Buildings.AddCard;
import it.polimi.ingsw.Visitors.BuildingVisitor.SpecialBuildings.AddCardVisitor;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCardVisitorTest {
    @Test
    public void addCardTest() {
        AddCardVisitor visitor = new AddCardVisitor();
        AddCard addCard = new AddCard(1,9,3);
        Player player = new Player("player1", Totem.BLACK, 0, null);
        int result = visitor.visit(addCard, player);

        assertEquals(1, result);

    }
}
