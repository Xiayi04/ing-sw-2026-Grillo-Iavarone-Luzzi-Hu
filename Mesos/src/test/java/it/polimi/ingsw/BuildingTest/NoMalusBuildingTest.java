package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ConcreteBuildingActivation;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Shamanic.ShamanicNoMalusVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Shamanic.ShamanicVisitorInterface;
import it.polimi.ingsw.Model.Cards.Buildings.NoMalusBuilding;
import it.polimi.ingsw.Model.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;
//
import static org.junit.jupiter.api.Assertions.*;

public class NoMalusBuildingTest {
    Player p = new Player("p1", Totem.BLACK, 0, null);
    NoMalusBuilding b = new NoMalusBuilding(1, 1, 1);
    ActivationVisitor v = new ConcreteBuildingActivation();

    @Test
    void constructorNoMalusBuildingTest() {
        NoMalusBuilding building = new NoMalusBuilding(1, 2, 3);

        assertEquals(1, building.getEra());
        assertEquals(2, building.getPrice());
        assertEquals(3, building.getPP());
        assertEquals("NoMalusBuilding", building.getName());
    }

    @Test
    void noMalusActivationTest() {
        NoMalusBuilding building = new NoMalusBuilding(1, 2, 3);
        assertTrue(building.noMalus());
    }

    @Test
    void acceptActivationAndVisitorTest() {
        b.acceptActivation(v, p);


    }

    @Test
    void acceptActivationShouldCallVisitor() {
        NoMalusBuilding building = new NoMalusBuilding(1, 2, 3);
        Player player = new Player("p1", Totem.BLACK, 0, null);

        ActivationVisitor visitor = new ConcreteBuildingActivation();

        assertDoesNotThrow(() -> building.acceptActivation(visitor, player));
    }

    @Test
    void acceptShamanicEventTest() {
        NoMalusBuilding b = new NoMalusBuilding(1, 2, 3);
        Player player = new Player("p1", Totem.BLACK, 0, null);
        ShamanicEvent e = new ShamanicEvent(1, "EVENT", "SHAMANIC_EVENT", 2, 3);
        ShamanicVisitorInterface visitor = new ShamanicNoMalusVisitor();
        assertDoesNotThrow(() -> b.acceptShamanicEvent(visitor, player, e));
    }

    @Test
    void printTest() {
        NoMalusBuilding b = new NoMalusBuilding(1, 1, 1);

        Printer printer = new Printer();

        String[] result = b.print(printer);

        assertNotNull(result);

    }
}
