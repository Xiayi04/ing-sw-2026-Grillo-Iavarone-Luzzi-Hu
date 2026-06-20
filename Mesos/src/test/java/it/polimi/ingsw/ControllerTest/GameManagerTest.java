package it.polimi.ingsw.ControllerTest;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.Notifier;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Game.*;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.UI.Printer;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GameManagerTest {
    Player player1 = new Player("beppe", Totem.ORANGE,0,null);
    Player player2 = new Player("mattia", Totem.BLACK,0,null);
    Player player3 = new Player("xia", Totem.WHITE,0,null);
    Player player4 = new Player("denise", Totem.BLUE,0,null);
    Player player5 = new Player("juan", Totem.YELLOW,0,null);

    @Test
    public void resolvePositionTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        GameManager gm = new GameManager(players, players.size(), new Board());
        gm.setNotifier(new  Notifier() {
            @Override
            public void gameStartedBroadcast(ArrayList<Player> players, Board board) {
            }
        });
        gm.startGame();
        assertEquals(players.size(),gm.getBoard().getPlayers().size());

        gm.positionPhase();

        gm.resolvePosition(player1.getName(), 1);
        gm.resolvePosition(player2.getName(), 1);
        gm.resolvePosition(player3.getName(), 1);
        gm.resolvePosition(player4.getName(), 1);
        gm.resolvePosition(player5.getName(), 1);

        gm.resolvePosition(player1.getName(), 2);
        gm.resolvePosition(player2.getName(), 2);
        gm.resolvePosition(player3.getName(), 2);
        gm.resolvePosition(player4.getName(), 2);
        gm.resolvePosition(player5.getName(), 2);

        gm.resolvePosition(player1.getName(), 3);
        gm.resolvePosition(player2.getName(), 3);
        gm.resolvePosition(player3.getName(), 3);
        gm.resolvePosition(player4.getName(), 3);
        gm.resolvePosition(player5.getName(), 3);

        gm.resolvePosition(player1.getName(), 4);
        gm.resolvePosition(player2.getName(), 4);
        gm.resolvePosition(player3.getName(), 4);
        gm.resolvePosition(player4.getName(), 4);
        gm.resolvePosition(player5.getName(), 4);

        gm.resolvePosition(player1.getName(), 5);
        gm.resolvePosition(player2.getName(), 5);
        gm.resolvePosition(player3.getName(), 5);
        gm.resolvePosition(player4.getName(), 5);
        gm.resolvePosition(player5.getName(), 5);

        gm.resolvePick(player1.getName(),true,false,1);
        gm.resolvePick(player2.getName(),true,false,1);
        gm.resolvePick(player3.getName(),true,false,1);
        gm.resolvePick(player4.getName(),true,false,1);
        gm.resolvePick(player5.getName(),true,false,1);


    }

    @Test
    public void BuyBuildingTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        player1.modifyFood(10);

        Board board = new Board();
        Building b = new MultiplicationBuilding(1,6,6,"INVENTOR",2);
        Building b1 = new MultiplicationBuilding(1,8,8,"HUNTER",3);
        Building b2 = new BonusStarBuilding(2,6,4);
        Building b3 = new BonusFood(1,3,3);
        Building b4 = new DoubleBonusBuilding(2,7,0);

        board.getUpperBuildingRow().add(b);
        board.getUpperBuildingRow().add(b1);
        board.getLowerBuildingRow().add(b2);
        board.getUpperBuildingRow().add(b3);
        board.getLowerBuildingRow().add(b4);

        GameManager gm = new GameManager(players, players.size(), board);
        gm.buyBuilding(player1, true, 0);

        assertTrue(player1.getBuilding().contains(b));
        assertFalse(board.getUpperBuildingRow().contains(b));
        assertEquals(4, player1.getFood());

    }
    @Test
    public void PurchaseBuildingWithDiscountTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        player1.modifyFood(10);

        Character c = new Builder(1,"CHARACTER",2,"BUILDER",2,0);
        Building b = new MultiplicationBuilding(1,6,6,"INVENTOR",2);
        Board board = new Board();
        board.getUpperCardRow().add(c);
        board.getLowerBuildingRow().add(b);

        GameManager gm = new GameManager(players,players.size(),board);
        gm.takeCharacter(player1,true,0);
        gm.buyBuilding(player1,false,0);

        assertTrue(player1.getTribeCard().contains(c));
        assertFalse(board.getUpperCardRow().contains(c));
        assertEquals(6,player1.getFood());
        assertTrue(player1.getBuilding().contains(b));
        assertFalse(board.getLowerBuildingRow().contains(b));

    }

    @Test
    public void PurchaseBuildingFailureTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player2);

        Building b = new BonusFood(1,3,3);
        Board board = new Board();
        board.getUpperBuildingRow().add(b);

        GameManager gm = new GameManager(players,players.size(),board);
        gm.setNotifier(new Notifier() {
            @Override public void invalidCardPick(Player player) {};
        });
        gm.buyBuilding(player2,true,0);

        assertFalse(player2.getBuilding().contains(b));
        assertTrue(board.getUpperBuildingRow().contains(b));
        assertEquals(0, player2.getFood());

    }

    @Test
    public void TakeCharacterTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        Character h = new Hunter(1,"CHARACTER",2,"HUNTER",true);
        Character h1 = new Hunter(1,"CHARACTER",2,"HUNTER",false);
        Character i = new Inventor(1,"CHARACTER",2,"INVENTOR","boat");
        Character s= new Shaman(2,"CHARACTER",2,"SHAMAN",1);
        Character p = new Picker(2,"CHARACTER",2,"PICKER");

        Board board = new Board();
        board.getUpperCardRow().add(h);
        board.getUpperCardRow().add(i);
        board.getUpperCardRow().add(s);
        board.getLowerCardsRow().add(h1);
        board.getLowerCardsRow().add(p);

        GameManager gm = new GameManager(players,players.size(),board);
        gm.setNotifier(new Notifier() {
            @Override public void invalidCardPick(Player player) {};
            @Override public void foodUpdateBroadcast(Player player, int update) {};
        });
        gm.takeCharacter(player1,true,0);

        assertTrue(player1.getTribeCard().contains(h));
        assertFalse(board.getUpperCardRow().contains(h));
    }

    @Test
    public  void executeNextPickisEmptyTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);

        Board board = new Board();

        GameManager gm = new GameManager(players,players.size(),board);
        gm.setNotifier(new Notifier() {
            @Override public void showTurnBroadcast(Player player) {}
            @Override public void gameStartedBroadcast(ArrayList<Player> players,Board board) {}
            @Override public void newEraBroadcast(int era){}
        });
        gm.startGame();
        gm.getPickingQueue().clear();
        gm.executeNextPick();

        assertEquals(2, gm.getRound());
    }

    @Test
    public  void executeNextPickNotEmptyTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);

        Character c = new Picker(2,"CHARACTER",2,"PICKER");
        Character c1 = new Hunter(1,"CHARACTER",2,"HUNTER",true);
        Board board = new Board();
        board.getUpperCardRow().add(c);
        board.getLowerCardsRow().add(c1);

        OfferCard offerCard = new OfferCard(0,1,1,false);
        offerCard.setOccupiedBy(player1);
        board.getPath().add(offerCard);

        GameManager gm = new GameManager(players,players.size(),board);
        gm.setNotifier(new  Notifier() {
            @Override public void showTurnBroadcast(Player player) {}
            @Override public void gameStartedBroadcast(ArrayList<Player> players,Board board) {}
            @Override public void newEraBroadcast(int era){}
            @Override public void returnTotemOnTurnOrderBroadcast(Player players, int index){}
        });
        gm.startGame();
        gm.getPickingQueue().clear();
        gm.getPickingQueue().add(new GameManager.PendingPick(player1, true, false, false));
        gm.getPickingQueue().add(new GameManager.PendingPick(player1, false, false, false));
        gm.executeNextPick();

        assertEquals(1, gm.getRound());
    }


    @Test
    public void startGameTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        Board board = new Board();
        GameManager gm = new GameManager(players,players.size(),board);
        gm.setNotifier(new Notifier() {
            @Override public void gameStartedBroadcast(ArrayList<Player> player,Board board) {}
        });
        gm.startGame();

        assertEquals(1, gm.getRound());
        assertEquals(1, board.getEra());
        assertEquals(5, board.getPlayers().size());
        assertTrue(board.getPlayers().contains(player1));

    }

    @Test
    public  void inizializeFood_For3PlayersTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);

        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players,players.size(), board);
        gm.initializeFood();

        assertEquals(2, players.get(0).getFood());
        assertEquals(3, players.get(1).getFood());
        assertEquals(3, players.get(2).getFood());
    }

    @Test
    public void initializeFood_For5PlayersTest() throws RemoteException {

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players,players.size(), board);
        gm.initializeFood();

        assertEquals(2, players.get(0).getFood());
        assertEquals(3, players.get(1).getFood());
        assertEquals(3, players.get(2).getFood());
        assertEquals(4, players.get(3).getFood());
        assertEquals(4, players.get(4).getFood());
    }

    @Test
    public void addPlayerSuccessTest(){
        GameManager gm = new GameManager(new ArrayList<>(), 5, new Board());
        gm.addPlayer(player1);

        assertEquals(1, gm.getPlayers().size());
        assertEquals("beppe", gm.getPlayers().get(0).getName());
    }

    @Test
    public void addPlayerLimitReachedTest(){
        GameManager gm = new GameManager(new ArrayList<>(), 5, new Board());
        gm.addPlayer(player1);
        gm.addPlayer(player2);
        gm.addPlayer(player3);
        gm.addPlayer(player4);
        gm.addPlayer(player5);

        assertThrows(IllegalStateException.class, () -> {
            gm.addPlayer(new Player("player6", Totem.BLACK, 0, null));
        });
    }
    @Test
    public void addPlayerAfterGameStartTest(){
        GameManager gm = new GameManager(new ArrayList<>(), 5, new Board());
        gm.setNotifier(new Notifier() {});
        gm.addPlayer(player1);
        gm.addPlayer(player2);
        gm.addPlayer(player3);
        gm.addPlayer(player4);
        gm.addPlayer(player5);
        gm.startGame();

        assertThrows(IllegalStateException.class, () -> {
            gm.addPlayer(new Player("Player6", Totem.WHITE , 0, null));
        });
    }

    @Test
    public void nextRoundIncrementTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players, 0, board);
        gm.setNotifier(new Notifier() {
            @Override public void nextRoundBroadcast(Board board, int round) {}
            @Override public void newEraBroadcast(int era) {}
        });
        gm.startGame();
        gm.nextRound();

        assertEquals(2, gm.getRound(), "the round should added to  2");
    }
    @Test
    public void nextRoundTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players,4,board);
        gm.setNotifier(new Notifier() {
            @Override public void nextRoundBroadcast(Board board, int round ) {};
        });
        for(int i = 0; i < 9; i++){
            gm.nextRound();
        }
        gm.nextRound();

        assertEquals(10,gm.getRound());

    }
    @Test
    public void nextRound_ShouldEndGameAtRound11() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players, 2, board);

        gm.setNotifier(new Notifier() {
            @Override public void nextRoundBroadcast(Board board, int round) {}
            @Override public void newEraBroadcast(int era) {}
            @Override public void showEndGameBroadcast(Player winner, List<PlayerScore> leaderboard){}
        });

        gm.startGame();
        for (int i = 0; i < 10; i++) {
            gm.nextRound();
        }

        assertEquals(11, gm.getRound());
    }

    @Test
    public void endRound_ContinuesIfDeckHasCards() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players, 3, board);
        gm.setNotifier(new Notifier() {
            @Override public void nextRoundBroadcast(Board board, int round) {}
            @Override public void newEraBroadcast(int era) {}
        });
        gm.startGame();
        for(int i = 0; i < 3; i++) gm.nextRound();
        gm.endRound();

        assertEquals(5, gm.getRound());
    }
    @Test
    public void endRound_EndsGameIfDeckIsEmpty() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        Board board = new Board();

        GameManager gm = new GameManager(players, 3, board);
        gm.setNotifier(new Notifier() {
            @Override public void nextRoundBroadcast(Board board, int round) {}
            @Override public void newEraBroadcast(int era) {}
        });
        gm.startGame();
        board.getDeck().clear();
        gm.endRound();

        assertEquals(1, gm.getRound());
    }
    @Test
    public void resolveEvents_ShouldPlaceSustenanceLast() {
        ArrayList<String> orderExecution = new ArrayList<>();
        ArrayList <Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        GameManager gm = new GameManager(new ArrayList<>(), 2, new Board());
        gm.setNotifier(new Notifier() {
            @Override
            public void resolvingEventBroadcast(Event e) {
                orderExecution.add(e.getEventName());
            }
        });

        Event shamanic = new Event(1, "EVENT","SHAMANIC_EVENT") {
            @Override public void resolveEvent(ArrayList<Player> players){}
            @Override public String[] print(Printer printer) {
                return new String[0];
            }
        };
        Event sustenance = new Event(1,"EVENT", "Sustenance") {
            @Override public String[] print(Printer printer) {
                return new String[0];
            }
            @Override public void resolveEvent(ArrayList<Player> players){}
        };

        ArrayList<Event> events = new ArrayList<>();
        events.add(shamanic);
        events.add(sustenance);

        gm.resolveEvents(events);

        assertEquals("SHAMANIC_EVENT", orderExecution.get(0));
        assertEquals("Sustenance", orderExecution.get(1));
    }

    @Test
    public void endGameTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        player1.modifyFood(2);
        player1.modifyPP(60);
        player2.modifyFood(1);
        player2.modifyPP(45);
        player3.modifyFood(1);
        player3.modifyPP(43);

        Board board = new Board();
        final Player[] winnerCaptor = new Player[1];
        GameManager gm = new GameManager(players, 3, board);
        gm.setNotifier(new Notifier() {
            @Override public void showEndGameBroadcast(Player winner, List<PlayerScore> leaderboard){
                winnerCaptor[0] = winner;
            }
            @Override public void sendLeaderBoard(Player player, int leaderBoardPosition,  ArrayList<LeaderBoardData> leaderBoardDB){}
        });
        gm.endGame();

        assertNotNull(winnerCaptor[0], "The notifier has not been called");
        assertEquals("beppe", winnerCaptor[0].getName());
    }

    @Test
    public void executeNextPosition_WhenOrderIsEmptyTest() throws RemoteException {
        Board board = new Board();
        board.initializeTurnOrderCard();
        final boolean[] pickingPhaseCalled = {false};

        GameManager gm = new GameManager(new ArrayList<>(), 4, board) {
            @Override public void pickingPhase() {
                pickingPhaseCalled[0] = true;
            }
        };
        gm.executeNextPosition();

        assertTrue(pickingPhaseCalled[0], "pickingPhase() had to be called");
    }

    @Test
    public void executeNextPosition_WhenOrderIsNotEmptyTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players, 2, board);
        gm.executeNextPosition();

        assertEquals("beppe", gm.getCurrentPlayer().getName());
    }

    @Test
    public void setNumPlayersTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players, 2, board);
        gm.setNumPlayers(2);

        assertEquals(2, gm.getNumPlayers());
    }

    @Test
    public void pickingPhaseTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        OfferCard card = new OfferCard(1,1,1,false);
        card.setOccupiedBy(player1);
        board.getPath().add(card);

        GameManager gm = new GameManager(new ArrayList<>(), 2, board) {
            @Override public void executeNextPick(){}
        };
        gm.setNotifier(new Notifier() {
            @Override public void nextRoundBroadcast(Board board, int round){}
        });
        gm.pickingPhase();

        assertEquals(2, gm.getPickingQueue().size());
        assertTrue(gm.isPickingPhase());
    }

    @Test
    public void pickingPhase_ShouldGiveFood_When5Players() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        player1.modifyFood(5);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        OfferCard card = new OfferCard(1,0,0,true);
        card.setOccupiedBy(player1);
        board.getPath().add(card);

        GameManager gm = new GameManager(new ArrayList<>(), 5, board) ;
        gm.setNotifier(new Notifier() {
            @Override public void nextRoundBroadcast(Board board, int round){}
        });
        gm.pickingPhase();

        assertEquals(8, player1.getFood());
    }

    @Test
    public void resolvePickTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        player1.modifyFood(5);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);
        Character c1 = new Inventor(1,"CHARACTER",2,"INVENTOR","boat");
        Character c2 = new Shaman(1,"CHARACTER",2,"SHAMAN",2);
        Character c3 = new Inventor(2,"CHARACTER",2,"INVENTOR","bowl");
        Character c4 = new Inventor(2,"CHARACTER",2,"INVENTOR","bread");
        Character c5 = new Inventor(2,"CHARACTER",2,"INVENTOR","tree");
        Building b1 = new SameIconBuilding(2,3,4);
        board.getUpperCardRow().add(c1);
        board.getUpperCardRow().add(c2);
        board.getUpperCardRow().add(c3);
        board.getLowerCardsRow().add(c4);
        board.getLowerCardsRow().add(c5);
        board.getLowerBuildingRow().add(b1);

        GameManager gm = new GameManager(players, 2, board){
            @Override public void executeNextPick(){}
        };
        gm.setNotifier(new Notifier() {
            @Override public void invalidCardPick(Player player) {}
            @Override public void pickedCardBroadcast(Player player, boolean row, boolean isBuilding, int index, int round){}
        });
        gm.getPickingQueue().add(new GameManager.PendingPick(player1,true,false,false));
        assertEquals(1, gm.getPickingQueue().size());
        assertEquals("beppe", player1.getName());

        gm.setPickingPhaseForTest(true);
        gm.resolvePick("beppe",true,false,1);

        assertTrue(gm.getPickingQueue().isEmpty());
    }
    @Test
    public void resolvePickFailureTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        player1.modifyFood(5);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);
        Character c1 = new Inventor(1,"CHARACTER",2,"INVENTOR","boat");
        Building b1 = new SameIconBuilding(2,3,4);
        board.getUpperCardRow().add(c1);
        board.getLowerBuildingRow().add(b1);
        GameManager gm = new GameManager(players, 2, board){
            @Override public void executeNextPick(){}
        };
        gm.setNotifier(new Notifier() {
            @Override public void invalidCardPick(Player player) {}
            @Override public void pickedCardBroadcast(Player player, boolean row, boolean isBuilding, int index, int round){}
        });
        gm.getPickingQueue().add(new GameManager.PendingPick(player1,true,false,false));
        gm.resolvePick("beppe",false,false,1);

        assertFalse(gm.getPickingQueue().isEmpty());
    }

    @Test
    public void isDirectionPickable_NoCardsAvailable_Test() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players, 2, board){
            @Override public void executeNextPick(){}
        };
        gm.setNotifier(new Notifier() {
            @Override public void allowSkipTurn(Player player) {}
        });
        gm.getPickingQueue().add(new GameManager.PendingPick(player1,true,false,true));

        boolean result = gm.isDirectionPickable();

        assertFalse(result);
        assertTrue(gm.getPickingQueue().isEmpty());

    }
    @Test
    public void isDirectionPickable_OnlyBuildingsAvailable_Test() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Board board = new Board();
        Building b1 = new SameIconBuilding(2,3,4);
        Building b2 = new MultiplicationBuilding(1,6,3,"builder",4);
        board.getUpperBuildingRow().add(b1);
        board.getUpperBuildingRow().add(b2);
        GameManager gm = new GameManager(players, 2, board){
            @Override public void executeNextPick(){}
        };
        gm.setNotifier(new Notifier() {
            @Override public void allowSkipTurn(Player player) {}
        });
        gm.getPickingQueue().add(new GameManager.PendingPick(player1,true,false,true));

        boolean result = gm.isDirectionPickable();

        assertTrue(result);
        assertTrue(gm.getPickingQueue().get(0).isSkippable());
    }
    @Test
    public void isDirectionPickable_CharactersAvailableTest() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);
        Character c1 = new Shaman(1,"CHARACTER",2,"SHAMAN",2);
        Character c2 = new Inventor(2,"CHARACTER",2,"INVENTOR","bowl");
        Character c3 = new Hunter(1,"CHARACTER",2,"HUNTER",true);
        board.getUpperCardRow().add(c1);
        board.getUpperCardRow().add(c2);
        board.getUpperCardRow().add(c3);
        GameManager gm = new GameManager(players, 2, board){
            @Override public void executeNextPick(){}
        };
        gm.setNotifier(new Notifier() {
            @Override public void allowSkipTurn(Player player) {}
        });
        gm.getPickingQueue().add(new GameManager.PendingPick(player1,true,false,false));

        boolean result = gm.isDirectionPickable();

        assertTrue(result);
        assertFalse(gm.getPickingQueue().get(0).isSkippable());
    }
    @Test
    public void isDirectionPickable_MixedContent_Test() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);
        Character c1 = new Shaman(1,"CHARACTER",2,"SHAMAN",2);
        Character c2 = new Inventor(2,"CHARACTER",2,"INVENTOR","bowl");
        Character c3 = new Hunter(1,"CHARACTER",2,"HUNTER",true);
        Building b1 = new NoMalusBuilding(2,5,2);
        Building b2 = new MultiplicationBuilding(1,6,3,"BUILDER",4);
        board.getUpperCardRow().add(c1);
        board.getUpperCardRow().add(c2);
        board.getUpperCardRow().add(c3);
        board.getUpperBuildingRow().add(b1);
        board.getUpperBuildingRow().add(b2);
        GameManager gm = new GameManager(players, 2, board){
            @Override public void executeNextPick(){}
        };
        gm.setNotifier(new Notifier() {
            @Override public void allowSkipTurn(Player player) {}
        });
        gm.getPickingQueue().add(new GameManager.PendingPick(player1,true,false,false));

        boolean result = gm.isDirectionPickable();

        assertTrue(result);
        assertFalse(gm.getPickingQueue().get(0).isSkippable());
    }


    @Test
    public void skipPickSuccessTest() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players, 1, board) {
            @Override public void executeNextPick() {}
        };
        gm.setNotifier(new Notifier() {
            @Override public void invalidCardPick(Player player) {}
        });
        gm.getPickingQueue().add(new GameManager.PendingPick(player1,true,false,true));
        gm.skipPick(player1.getName());

        assertTrue(gm.getPickingQueue().isEmpty());
    }
    @Test
    public void skipPickFailureTest() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players, 1, board) {
            @Override public void executeNextPick() {}
        };
        gm.setNotifier(new Notifier() {
            @Override public void invalidCardPick(Player player) {}
        });
        gm.getPickingQueue().add(new GameManager.PendingPick(player1,true,false,true));
        gm.skipPick(player2.getName());

        assertFalse(gm.getPickingQueue().isEmpty());
        assertEquals(1, gm.getPickingQueue().size());
    }
    @Test
    public void skipPickNotSkippableTest() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Board board = new Board();
        board.initializeTurnOrderCard();
        board.getTurnOrderCard().getOrder().addAll(players);

        GameManager gm = new GameManager(players, 1, board) {
            @Override public void executeNextPick() {}
        };
        gm.setNotifier(new Notifier() {
            @Override public void invalidCardPick(Player player) {}
        });
        gm.getPickingQueue().add(new GameManager.PendingPick(player1,true,false,false));
        gm.skipPick(player1.getName());

        assertFalse(gm.getPickingQueue().isEmpty());
    }
    @Test
    public void getPlayerByNameTest()throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        GameManager gm = new GameManager(players, 1, new Board());
        Player found = gm.getPlayerByName(player1.getName());

        assertNotNull(found);
        assertEquals(player1, found, "The player found does not match the one searched for");
    }
    @Test
    public void getPlayerByNameFailure_Test() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        GameManager gm = new GameManager(players, 1, new Board());
        Player found = gm.getPlayerByName("non-existent name");

        assertNull(found, "The method should return null for a non-existent player");
    }


}
