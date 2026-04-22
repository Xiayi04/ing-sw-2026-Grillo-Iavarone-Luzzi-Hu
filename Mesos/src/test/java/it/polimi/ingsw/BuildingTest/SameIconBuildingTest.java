package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Buildings.BuildingVisitor.ConcreteBuildingActivation;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Factory.ConcreteFactoryEra;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class SameIconBuildingTest {

    Visitor v = new ConcreteBuildingActivation();
    Player p = new Player("io", Totem.BLACK, 2);
    ConcreteFactoryEra factory = new ConcreteFactoryEra(1);
    SameIconBuilding b = new SameIconBuilding(1, 1, 1);

    @Test
    void acceptTest(){
        ArrayList<Character> characters = factory.createCharacterList();
        p.getTribeCard().add(characters.get(18));
        p.getTribeCard().add(characters.get(18));
        p.getTribeCard().add(characters.get(19));
        p.getTribeCard().add(characters.get(20));
        b.accept(v, p);
        assertEquals(-1,b.getCheckPair().get("leather"));
        assertEquals(1,b.getCheckPair().get("tree"));
        assertEquals(1,b.getCheckPair().get("boat"));
    }

    @Test
    void giveFoodBonusTest(){

    }
}
