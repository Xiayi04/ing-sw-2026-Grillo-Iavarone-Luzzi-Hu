package it.polimi.ingsw.FileLoaderTest.GameTest;

import it.polimi.ingsw.Model.Cards.Buildings.BonusFood;
import it.polimi.ingsw.Model.Cards.Buildings.BonusStarBuilding;
import it.polimi.ingsw.Model.Cards.Buildings.Building;
import it.polimi.ingsw.Model.Cards.Buildings.MultiplicationBuilding;
import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.Cards.Characters.Builder;
import it.polimi.ingsw.Model.Cards.Characters.Painter;
import it.polimi.ingsw.Model.Cards.Events.Event;
import it.polimi.ingsw.Model.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Model.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Model.Game.Board;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;
    Player player1 = new Player("beppe", Totem.ORANGE, 0, null);
    Player player2 = new Player("mattia", Totem.BLACK, 0, null);
    Player player3 = new Player("xia", Totem.WHITE, 0, null);
    Player player4 = new Player("denise", Totem.BLUE, 0, null);
    Player player5 = new Player("juan", Totem.YELLOW, 0, null);

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    public void initializeBoardTest() {
        int numPlayers = 4;

        board.getPlayers().add(player1);
        board.getPlayers().add(player2);
        board.getPlayers().add(player3);
        board.getPlayers().add(player4);

        board.initializeBoard(numPlayers);

        assertNotNull(board.getUpperBuildingRow()); //fila edifici non nulla
        assertFalse(board.getUpperBuildingRow().isEmpty()); //fila edifici non vuota

        assertEquals(5, board.getLowerCardsRow().size());
        assertEquals(8, board.getUpperCardRow().size());

        assertNotNull(board.getDeck());//mazzo non nullo
        assertFalse(board.getDeck().isEmpty());//mazzo non vuoto

        assertNotNull(board.getTurnOrderCard());//carte inizializzate
        assertNotNull(board.getPath());
    }

    Building b = new MultiplicationBuilding(1, 6, 6, "INVENTOR", 2);
    Building b1 = new MultiplicationBuilding(1, 8, 8, "HUNTER", 3);
    Building b2 = new BonusStarBuilding(2, 6, 4);
    Building b3 = new BonusFood(1, 3, 3);

    @Test
    void InizializeBoardTest() {
        Board board = new Board();
        assertEquals(1, board.getEra());

        assertNotNull(board.getUpperBuildingRow());
        assertTrue(board.getUpperBuildingRow().isEmpty());

        assertNotNull(board.getUpperCardRow());
        assertTrue(board.getUpperCardRow().isEmpty());

        assertNotNull(board.getLowerCardsRow());
        assertTrue(board.getLowerCardsRow().isEmpty());

        assertNotNull(board.getLowerBuildingRow());
        assertTrue(board.getLowerBuildingRow().isEmpty());

        assertNotNull(board.getPath());
        assertTrue(board.getPath().isEmpty());

        assertNotNull(board.getPlayers());
        assertTrue(board.getPlayers().isEmpty());

        assertNotNull(board.getDeck());
        assertTrue(board.getDeck().isEmpty());

        assertNotNull(board.getBuildingsEra1());
        assertTrue(board.getBuildingsEra1().isEmpty());

        assertNotNull(board.getBuildingsEra2());
        assertTrue(board.getBuildingsEra2().isEmpty());

        assertNotNull(board.getBuildingsEra3());
        assertTrue(board.getBuildingsEra3().isEmpty());
        assertNull(board.getTurnOrderCard());
    }
    @Test
    void pickCardUpperBuilding(){
        Board board= new Board();
        Card building = new BonusFood(1,3,3);

        board.getUpperBuildingRow().add(building);
        Card result = board.pickCard(true,true,0);

        assertSame(building, result);
        assertTrue(board.getUpperBuildingRow().isEmpty());

    }
    @Test
    void pickCardDownBuilding(){
        Board board= new Board();
        Card building = new BonusFood(1,3,3);

        board.getLowerBuildingRow().add(building);
        Card result = board.pickCard(false,true,0);

        assertSame(building, result);
        assertTrue(board.getLowerBuildingRow().isEmpty());

    }
    @Test
    void pickCardUpperCardRowTest(){
        Board board= new Board();
        Card c1 = new Painter(1,"CHARACTER",2,"PAINTER");

        board.getUpperCardRow().add(c1);
        Card result = board.pickCard(true,false,0);

        assertSame(c1, result);
        assertTrue(board.getUpperCardRow().isEmpty());

    }
    @Test
    void pickCardLowerCardRowTest(){
        Board board= new Board();
        Card c1 = new Painter(1,"CHARACTER",2,"PAINTER");

        board.getLowerCardsRow().add(c1);
        Card result = board.pickCard(false,false,0);

        assertSame(c1, result);
        assertTrue(board.getLowerBuildingRow().isEmpty());

    }

    @Test
    void checkEvent() {
        Board board1 = new Board();

        Event event1 = new ShamanicEvent(1, "EVENT", "PaEvePointsLoss", 2, 3);
        Event event2 = new HuntingEvent(1, "EVENT", "PaEveNumMinPainters", 2);

        board1.getLowerCardsRow().add(event1);
        board1.getLowerCardsRow().add(event2);
        ArrayList<Event> result = board1.checkEvent();

        assertEquals(2, result.size());
        assertTrue(result.contains(event1));
        assertTrue(result.contains(event2));

        assertSame(event1, result.get(0));

    }

    @Test
    void checkUpperEvent() {
        Board board2 = new Board();

        Event event1 = new ShamanicEvent(1, "EVENT", "PaEvePointsLoss", 2, 3);
        Event event2 = new HuntingEvent(1, "EVENT", "PaEveNumMinPainters", 2);

        board2.getUpperCardRow().add(event1);
        board2.getUpperCardRow().add(event2);
        ArrayList<Event> result = board2.checkEvent();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void checkUpperEventReturnTest() {
        Board board = new Board();

        Event event1 = new ShamanicEvent(1, "EVENT", "event1", 2, 3);
        Event event2 = new HuntingEvent(1, "EVENT", "event2", 2);

        Card c1 = new Painter(1, "CHARACTER", 2, "PAINTER");

        board.getUpperCardRow().add(event1);
        board.getUpperCardRow().add(c1);
        board.getUpperCardRow().add(event2);

        ArrayList<Event> result = board.checkUpperEvent();

        assertEquals(2, result.size());
        assertTrue(result.contains(event1));
        assertTrue(result.contains(event2));
        assertFalse(result.contains(c1));
    }


    @Test
    void shiftUpToDownTest() {
        Board board = new Board();
        //ho usato le carte per testare gli eventi
        Event event1 = new ShamanicEvent(1, "EVENT", "event1", 2, 3);
        Event event2 = new HuntingEvent(1, "EVENT", "event2", 2);
        board.getUpperCardRow().add(event1);
        board.getUpperCardRow().add(event2);
        board.getUpperCardRow().add(b2);
        board.shiftUpToDown();

        assertTrue(board.getUpperCardRow().isEmpty());

        assertEquals(3, board.getLowerCardsRow().size());
        assertSame(event1, board.getLowerCardsRow().get(0));
        assertSame(event2, board.getLowerCardsRow().get(1));
        assertSame(b2, board.getLowerCardsRow().get(2));

    }


    @Test
    void setCardBoardTest() {
        Board board1 = new Board();
        Player p1 = new Player("beppe", Totem.ORANGE, 0, null);
        Player p2 = new Player("nina", Totem.YELLOW, 2, null);

        board1.getPlayers().add(p1);
        board1.getPlayers().add(p2);

        Card c1 = new Painter(1, "CHARACTER", 2, "PAINTER");
        Card c2 = new Builder(1, "CHARACTER", 2, "BUILDER", 3, 5);
        Card c3 = new Builder(1, "CHARACTER", 2, "BUILDER", 2, 2);

        Card c4 = new Painter(1, "CHARACTER", 2, "PAINTER");
        Card c5 = new Builder(1, "CHARACTER", 2, "BUILDER", 3, 1);
        Card c6 = new Builder(1, "CHARACTER", 2, "BUILDER", 2, 2);
        Card c7 = new Painter(1, "CHARACTER", 2, "PAINTER");
        Card c8 = new Builder(1, "CHARACTER", 2, "BUILDER", 1, 3);
        Card c9 = new Painter(1, "CHARACTER", 2, "PAINTER");

        board1.getDeck().add(c1);
        board1.getDeck().add(c2);
        board1.getDeck().add(c3);
        board1.getDeck().add(c4);
        board1.getDeck().add(c5);
        board1.getDeck().add(c6);
        board1.getDeck().add(c7);
        board1.getDeck().add(c8);
        board1.getDeck().add(c9);

        board1.setCardOnBoard();

        assertEquals(6, board1.getUpperCardRow().size());
        assertEquals(3, board1.getLowerCardsRow().size());

        assertSame(c1, board1.getLowerCardsRow().get(0));
        assertSame(c2, board1.getLowerCardsRow().get(1));
        assertSame(c3, board1.getLowerCardsRow().get(2));

        assertSame(c4, board1.getUpperCardRow().get(0));
        assertSame(c5, board1.getUpperCardRow().get(1));
      

    }

    @Test
    void initializeTurnOrderCardTest() {
        Board board = new Board();

        Player p1 = new Player("gemma", Totem.YELLOW,  0, null);
        Player p2 = new Player("luna", Totem.ORANGE, 3, null);
        Player p3 = new Player("mattia", Totem.BLACK,3 , null);

        board.getPlayers().add(p1);
        board.getPlayers().add(p2);
        board.getPlayers().add(p3);

        board.initializeTurnOrderCard();

        assertNotNull(board.getTurnOrderCard());

        assertEquals(3, board.getTurnOrderCard().getOrder().size());

        assertTrue(board.getTurnOrderCard().getOrder().contains(p1));
        assertTrue(board.getTurnOrderCard().getOrder().contains(p2));
        assertTrue(board.getTurnOrderCard().getOrder().contains(p3));


    }
    @Test
    void checkUpperNoEventsTest() {
        Board board = new Board();

        Card c2 = new Painter(1, "CHARACTER", 2, "PAINTER");

        board.getUpperCardRow().add(c2);

        ArrayList<Event> result = board.checkUpperEvent();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void initializeDeckTest() {
        Board board2 = new Board();

        Player p1 = new Player("gemma", Totem.YELLOW,  0, null);
        Player p2 = new Player("luna", Totem.ORANGE, 3, null);
        Player p3 = new Player("mattia", Totem.BLACK,3 , null);

        board2.getPlayers().add(p1);
        board2.getPlayers().add(p2);
        board2.getPlayers().add(p3);

        board2.initializeDeck();

        assertNotNull(board.getDeck());
        assertFalse(board2.getDeck().isEmpty());


    }

    @Test
    void refillUpperBuildingByEra2Test() {
          Board board = new Board();
          board.buildingPerPlayers(3);
          ArrayList<Building> expectedBuildingEra2 = new ArrayList<>(board.getBuildingsEra2());
          board.getUpperBuildingRow().addAll(board.getBuildingsEra2());
          board.setEra(2);
          board.refillUpperBuildingByEra();
          assertEquals(expectedBuildingEra2.size(), board.getUpperBuildingRow().size());
          assertEquals(expectedBuildingEra2.get(0), board.getUpperBuildingRow().get(0));
          assertNotNull(board.getUpperBuildingRow().get(1));
          assertEquals(2, board.getUpperBuildingRow().size());
    }
    @Test
    void refillUpperBuildingByEra3Test(){
        Board board = new Board();
        board.buildingPerPlayers(4);
        ArrayList<Building> expectedBuildingEra3 = new ArrayList<>(board.getBuildingsEra3());
        board.getUpperBuildingRow().addAll(board.getBuildingsEra3());
        board.setEra(3);
        board.refillUpperBuildingByEra();
        assertEquals(expectedBuildingEra3.size(), board.getUpperBuildingRow().size());
        assertEquals(expectedBuildingEra3.get(3), board.getUpperBuildingRow().get(3));
        assertNotNull(board.getUpperBuildingRow().get(2));
        assertEquals(4, board.getUpperBuildingRow().size());
    }

    @Test
    void refillCardsWithoutChangeEraTest() {
        Board board1 = new Board();

        Player p1 = new Player("gemma", Totem.YELLOW,  0, null);
        Player p2 = new Player("luna", Totem.ORANGE, 3, null);
        board.getPlayers().add(p1);
        board.getPlayers().add(p2);
        Card c1 = new Painter(1, "CHARACTER", 2, "PAINTER");
        Card c2 = new Builder(1, "CHARACTER", 2, "BUILDER", 3, 5);
        Card c3 = new Builder(1, "CHARACTER", 2, "BUILDER", 2, 2);
        Card c4 = new Painter(1, "CHARACTER", 2, "PAINTER");
        Card c6 = new Builder(1, "CHARACTER", 2, "BUILDER", 3, 1);
        Card c5 = new Builder(1, "CHARACTER", 2, "BUILDER", 2, 2);

        board.getDeck().add(c1);
        board.getDeck().add(c2);
        board.getDeck().add(c3);
        board.getDeck().add(c4);
        board.getDeck().add(c5);
        board.getDeck().add(c6);
        boolean eraChanged = board.refillCards();

        assertFalse(eraChanged);
        assertEquals(1, board.getEra());
        assertEquals(6, board.getUpperCardRow().size());
        assertTrue(board.getDeck().isEmpty());

        assertSame(c1, board.getUpperCardRow().get(0));
        assertSame(c6, board.getUpperCardRow().get(5));
    }
    @Test
    void refillCardsShouldChangeEraWhenNewEraCardIsDrawn() {
        Board board = new Board();

        Player p1 = new Player("p1", Totem.BLACK, 0, null);
        Player p2 = new Player("p2", Totem.YELLOW, 0, null);

        board.getPlayers().add(p1);
        board.getPlayers().add(p2);

        board.buildingPerPlayers(2);

        board.getUpperBuildingRow().addAll(board.getBuildingsEra1());

        ArrayList<Card> oldUpperBuildings = new ArrayList<>(board.getUpperBuildingRow());
        ArrayList<Building> expectedEra2Buildings = new ArrayList<>(board.getBuildingsEra2());

        Card era2Card = new Painter(2, "CHARACTER", 2, "PAINTER");

        board.getDeck().add(era2Card);

        boolean eraChanged = board.refillCards();

        assertTrue(eraChanged);
        assertEquals(2, board.getEra());

        assertIterableEquals(oldUpperBuildings, board.getLowerBuildingRow());
        assertIterableEquals(expectedEra2Buildings, board.getUpperBuildingRow());

        assertEquals(1, board.getUpperCardRow().size());
        assertSame(era2Card, board.getUpperCardRow().get(0));
    }


    @Test
    void shiftBuildingUpToDownTest() {

    }
    @Test
    void bringBackToTOCWhenThePlayerIsOnOCTest(){
        Board board = new Board();
        Player p1 = new Player("gemma", Totem.YELLOW,  0, null);
        Player p2 = new Player("luna", Totem.ORANGE, 3, null);
        board.getPlayers().add(p1);
        board.initializeTurnOrderCard();
        int idx = board.bringBackToTOC(p2);
        assertEquals(1,idx);
        assertTrue(board.getTurnOrderCard().getOrder().contains(p2));
        assertEquals(2,board.getTurnOrderCard().getOrder().size());

    }
    @Test
    void bringBackToTOCWhenThePlayerIsOnTOCTest(){
        Board board = new Board();
        Player p1 = new Player("gemma", Totem.YELLOW,  0, null);
        Player p2 = new Player("luna", Totem.ORANGE, 3, null);
        board.getPlayers().add(p1);
        board.initializeTurnOrderCard();
        int idx = board.bringBackToTOC(p1);
        assertEquals(-1,idx);
        assertTrue(board.getTurnOrderCard().getOrder().contains(p1));
        assertEquals(1,board.getTurnOrderCard().getOrder().size());





    }
    @Test
    void giveFoodForTOCTest(){
        Board board = new Board();
        Player p1 = new Player("gemma", Totem.YELLOW,  3, null);
        Player p2 = new Player("luna", Totem.ORANGE, 3, null);
        board.getPlayers().add(p1);
        board.getPlayers().add(p2);
        board.initializeTurnOrderCard();
        int idx = board.getTurnOrderCard().getOrder().indexOf(p1);
        int initialFood = p1.getFood();
        int foodFromTOC = board.getTurnOrderCard().getFoodByIndex(idx);

        board.giveFoodForTOC(p1, idx);
        int expectedFood = Math.max(0, initialFood + foodFromTOC);

        assertEquals(expectedFood, p1.getFood());

    }
    @Test
    void drawCardTest(){
        Board board = new Board();
        Card c1 = new Painter(1, "CHARACTER", 2, "PAINTER");
        Card c2 = new Builder(1, "CHARACTER", 2, "BUILDER", 3, 5);

        board.getDeck().add(c1);
        board.getDeck().add(c2);

        Card result = board.drawCard();

        assertSame(c1, result);
        assertEquals(1, board.getDeck().size());
        assertSame(c2, board.getDeck().getFirst());

    }
    @Test
    void drawCardShouldException() {
        Board board = new Board();

        assertThrows(IllegalArgumentException.class, board::drawCard);
    }



}

