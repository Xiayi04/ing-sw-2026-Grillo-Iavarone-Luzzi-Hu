package it.polimi.ingsw.BuildingTest;


import it.polimi.ingsw.Model.Cards.Buildings.BonusStarBuilding;
import it.polimi.ingsw.Visitors.BuildingVisitor.ConcreteBuildingActivation;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class BonusStarBuildingTest {


    @Test
    void printTest() {
        BonusStarBuilding building = new BonusStarBuilding(1, 10, 5);
        Printer printer = new Printer();
        String[] result = building.print(printer);
        assertNotNull(result);
    }
    @Test
    public void acceptActivationTest(){
        BonusStarBuilding b = new BonusStarBuilding(1, 1, 1);
        Player p = new Player("x", Totem.BLACK, 2,null);
        final boolean[] visited = {false};
        ConcreteBuildingActivation visitor = new ConcreteBuildingActivation() {
            @Override
            public void visit(BonusStarBuilding b, Player p) {
                visited[0] = true;
            }
        };
        b.acceptActivation(visitor, p);
        assertTrue(visited[0]);
    }

//    @Test
//    void buildingActivationTest(){
//        Visitor v = new ConcreteBuildingActivation();
//        Player p = new Player("io", Totem.BLACK, 2);
//        BonusStarBuilding b = new BonusStarBuilding(1, 1, 1);
//        b.accept(v, p);
//        assertEquals(3, p.getStarCounter());
//    }
}
