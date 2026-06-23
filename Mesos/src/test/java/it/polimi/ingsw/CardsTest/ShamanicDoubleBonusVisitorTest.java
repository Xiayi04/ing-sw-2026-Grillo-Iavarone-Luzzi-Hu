package it.polimi.ingsw.CardsTest;

import it.polimi.ingsw.Model.Factory.BuildingFactory;
import it.polimi.ingsw.Model.Cards.Buildings.Building;
import it.polimi.ingsw.Model.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShamanicDoubleBonusVisitorTest {
    @Test
    public void VisitModifiesPlayerPPCorrectlyTest() {
        Player player = new Player("p1", Totem.BLACK, 0, null);
        Player player2 = new Player("p2", Totem.WHITE, 0, null);
        player.modifyPP(10);
        player2.modifyPP(10);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player);
        players.add(player2);
        player.modifyStarCounter(3);

        player2.modifyStarCounter(2);

        ShamanicEvent event = new ShamanicEvent(1,"EVENT","SHAMANIC_EVENT",-3,5);

        BuildingFactory factory = new BuildingFactory();
        ArrayList<Building> buildings = factory.createBuildingList();
        player.getBuilding().addAll(buildings);
        event.resolveEvent(players);

        assertEquals(20, player.getPrestigePoints());
        assertEquals(7, player2.getPrestigePoints());
    }
}
