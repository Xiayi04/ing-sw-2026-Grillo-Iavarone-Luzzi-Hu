package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Buildings.BonusStarBuilding;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;

import static junit.framework.Assert.assertEquals;

public class BonusStarBuildingTest {

    @Test
    void buildingActivationTest(){
        Player p = new Player("io", Totem.BLACK, 2);
        BonusStarBuilding b = new BonusStarBuilding(1, 1, 1);
        b.buildingActivation(p);
        assertEquals(3, p.getStarCounter());
    }
}
