package it.polimi.ingsw.BuildingTest;

import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Buildings.MultiplicationBuilding;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Factory.ConcreteFactoryEra;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplicationBuildingTest {

    Player p = new Player("io", Totem.BLACK, 2);
    ConcreteFactoryEra factory = new ConcreteFactoryEra(1);
    ArrayList<Character> characters = factory.createCharacterList();

    @Test
    void countPPSetTest(){
        p.getTribeCard().add(characters.get(0)); //hunter
        p.getTribeCard().add(characters.get(1)); //builder
        p.getTribeCard().add(characters.get(9)); //picker
        p.getTribeCard().add(characters.get(13)); //painter
        p.getTribeCard().add(characters.get(18)); //inventor
        p.getTribeCard().add(characters.get(25)); //shaman
        MultiplicationBuilding b = new MultiplicationBuilding(1, 1, 1, Icons.SET, 2);
        p.modifyPP(b.countPP(p));
        assertEquals(2, p.getPrestigePoints());
    }

    @Test
    void countPPIconTest(){
        p.getTribeCard().add(characters.get(1));
        p.getTribeCard().add(characters.get(1));
        p.getTribeCard().add(characters.get(1));
        MultiplicationBuilding b = new MultiplicationBuilding(1, 1, 1, Icons.BUILDER, 2);
        p.modifyPP(b.countPP(p));
        assertEquals(6, p.getPrestigePoints());
    }
}
