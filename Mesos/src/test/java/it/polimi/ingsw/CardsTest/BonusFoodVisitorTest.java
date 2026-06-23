package it.polimi.ingsw.CardsTest;

import it.polimi.ingsw.Model.Cards.Buildings.BonusFood;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SpecialBuildings.BonusFoodVisitor;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

public class BonusFoodVisitorTest {

    @Test
    public void bonusFoodVisitorTest(){
        Player p1 = new Player("p1", Totem.BLACK,0, null);
        p1.modifyFood(10);
        BonusFood bonusFood = new BonusFood(1,3,3);
        bonusFood.giveExtraFood();
        BonusFoodVisitor bonusFoodVisitor = new BonusFoodVisitor();
        bonusFoodVisitor.visit(bonusFood, p1);

        assertEquals(11 , p1.getFood());
    }
}


