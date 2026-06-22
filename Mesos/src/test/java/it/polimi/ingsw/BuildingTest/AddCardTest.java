package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Buildings.BuildingVisitor.ConcreteBuildingActivation;
import it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings.AddCardException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddCardTest {
    @Test
    public void acceptActivationTest() {
        AddCard b = new AddCard(1, 1, 1);
        ActivationVisitor visitor = new ConcreteBuildingActivation();
        assertThrows(AddCardException.class, () -> {
            b.acceptActivation(visitor, null);
        });
    }

    @Test
    public void printTest(){
        AddCard b = new AddCard(1, 1, 1);
        assertDoesNotThrow(b::printCard);
    }
}
