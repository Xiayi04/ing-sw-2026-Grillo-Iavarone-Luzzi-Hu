package it.polimi.ingsw.CardsTest;

import it.polimi.ingsw.Model.Cards.Buildings.AddCard;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.DiscountBuilding;
import it.polimi.ingsw.Model.Cards.Buildings.Events;
import it.polimi.ingsw.Model.Cards.Buildings.Icons;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.AddAndCountCharacter;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Model.Cards.Characters.Hunter;
import it.polimi.ingsw.Model.Cards.Characters.Inventor;
import it.polimi.ingsw.Model.Cards.Characters.Painter;
import it.polimi.ingsw.Model.Cards.Characters.Picker;
import it.polimi.ingsw.Model.Cards.Events.Event;
import it.polimi.ingsw.Model.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Model.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Model.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountVisitorTest {
    @Test
    public void DiscountVisitorForPainterTest() {
        Player player = new Player("p1", Totem.BLACK, 0, null);
        Player player2 = new Player("p2", Totem.YELLOW, 0, null);
        player.modifyFood(10);
        player.modifyPP(5);
        player2.modifyFood(5);
        player2.modifyPP(5);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        CharacterVisitor visitor = new AddAndCountCharacter();
        Painter p = new Painter(1,"CHARACTER",2,"PAINTER");
        Painter p2 = new Painter(1,"CHARACTER",2,"PAINTER");
        Painter p3 = new Painter(1,"CHARACTER",2,"PAINTER");

        p.addCard(visitor,player);
        p2.addCard(visitor,player);
        p3.addCard(visitor,player);

        PaintingEvent event= new PaintingEvent(1,"EVENT","PAINTING",1,1,-2);

        DiscountBuilding building = new DiscountBuilding(1, 7, 2, 1, 1, Icons.PAINTER, Events.PAINTINGEVENT);
        player.getBuilding().add(building);
        event.resolveEvent(players);

        assertEquals(13, player.getFood());
        assertEquals(8, player.getPrestigePoints());
        assertEquals(5,player2.getFood());
        assertEquals(3,player2.getPrestigePoints());
    }

    @Test
    public void DiscountVisitorForHuntingTest() {
        Player player = new Player("p1", Totem.BLACK, 0, null);
        Player player2 = new Player("p2", Totem.YELLOW, 0, null);
        player.modifyFood(10);
        player.modifyPP(5);
        player2.modifyFood(5);
        player2.modifyPP(5);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        CharacterVisitor visitor = new AddAndCountCharacter();
        Hunter h = new Hunter(1, "CHARACTER", 2, "HUNTER",false);
        Hunter h1 = new Hunter(1, "CHARACTER", 2, "HUNTER",false);
        h.addCard(visitor, player);
        h1.addCard(visitor, player2);

        HuntingEvent event =  new HuntingEvent(1, "EVENT", "HUNTING",1);
        DiscountBuilding building = new DiscountBuilding(1, 7, 2, 1, 1, Icons.HUNTER, Events.HUNTEREVENT);
        player.getBuilding().add(building);
        event.resolveEvent(players);

        assertEquals(12, player.getFood());
        assertEquals(7, player.getPrestigePoints());
        assertEquals(6,player2.getFood());
        assertEquals(6,player2.getPrestigePoints());

    }

    @Test
    public void DiscountVisitorForSustenanceTest() {
        Player player = new Player("p1", Totem.BLACK, 0, null);
        Player player2 = new Player("p2", Totem.YELLOW, 0, null);
        player.modifyFood(2);
        player.modifyPP(5);
        player2.modifyFood(5);
        player2.modifyPP(5);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        CharacterVisitor visitor = new AddAndCountCharacter();
        Painter p = new Painter(1,"CHARACTER",2,"PAINTER");
        Painter p2 = new Painter(1,"CHARACTER",2,"PAINTER");
        Painter p3 = new Painter(1,"CHARACTER",2,"PAINTER");
        Inventor i = new Inventor(1, "CHARACTER",2,"INVENTOR","boat");

        p.addCard(visitor,player);
        p2.addCard(visitor,player);
        p3.addCard(visitor,player);
        i.addCard(visitor,player2);

        SustenanceEvent event = new SustenanceEvent(1, "EVENT", "SUSTENANCE",-1);
        DiscountBuilding building = new DiscountBuilding(1, 7, 2, 1, 1, Icons.INVENTOR, Events.SUSTENANCEEVENT);
        player2.getBuilding().add(building);

        event.resolveEvent(players);

        assertEquals(0, player.getFood());
        assertEquals(4, player.getPrestigePoints());
        assertEquals(5,player2.getFood());
        assertEquals(5,player2.getPrestigePoints());
    }
}

