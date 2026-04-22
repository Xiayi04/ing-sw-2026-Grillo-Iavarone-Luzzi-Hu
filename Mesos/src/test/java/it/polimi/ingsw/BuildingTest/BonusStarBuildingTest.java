package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Buildings.BonusStarBuilding;
import it.polimi.ingsw.Buildings.BuildingVisitor.ConcreteBuildingActivation;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

public class BonusStarBuildingTest {

    @Test
    void buildingActivationTest(){
        Visitor v = new ConcreteBuildingActivation();
        Player p = new Player("io", Totem.BLACK, 2);
        BonusStarBuilding b = new BonusStarBuilding(1, 1, 1);
        b.accept(v, p);
        assertEquals(3, p.getStarCounter());
    }
}
