package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.ClientDisconnectedException;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Network.VirtualClientInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Notifier{

    private final Object outputLock = new Object();
    private final ArrayList<VirtualClientInterface> clients =  new ArrayList<>();
    private final AtomicBoolean startTermProcedure = new AtomicBoolean(false);

    private final ExecutorService pool = Executors.newCachedThreadPool();

    public Notifier(){}

    public void addClientToNotifier(VirtualClientInterface client){
        synchronized (clients){
            clients.add(client);
        }
    }

    private List<VirtualClientInterface> getClients() {

        synchronized (outputLock) {
            return new ArrayList<>(clients);
        }
    }

    private synchronized void handleDisconnect(VirtualClientInterface disconnectedClient) {
        Server.closeConnection(disconnectedClient);
    }

    public synchronized void sendFarewell(VirtualClientInterface disconnectedClient) {
        if(startTermProcedure.get())
            return;

        startTermProcedure.set(true);

        synchronized (clients){
            if(!clients.isEmpty() && clients.contains(disconnectedClient)){
                clients.remove(disconnectedClient);
                for(VirtualClientInterface client:clients){
                    pool.submit(client::updateForcedEndGame);
                }
                if(!pool.isShutdown())
                    pool.shutdown();
            }
        }
        terminationSignalBroadcast();
        Server.terminate();
    }

    //BROADCAST

    public void terminationSignalBroadcast(){
        synchronized (outputLock) {
            for (VirtualClientInterface client : clients) {
                try {
                    pool.submit(() -> {
                        try {
                            client.updateForcedEndGame();
                        } catch (Exception ignored) {
                        }
                    });
                } catch (RejectedExecutionException e) {
                    if (!pool.isShutdown())
                        System.out.println("Notifier thread RejectedExecutionException catch even if pool is not shutdown:" + e.getMessage());
                }
            }
        }
    }

    public void nextRoundBroadcast(Board board, int round){
        synchronized (outputLock) {
            for(VirtualClientInterface client:clients){
                pool.submit(()->{
                    try {
                        client.updateNextRound(board, round);
                    } catch (ClientDisconnectedException e) {
                        handleDisconnect(client);
                    }
                });
            }
        }
    }

    public void returnTotemOnTurnOrderBroadcast(Player player, int index) {
        synchronized (outputLock) {
            for (VirtualClientInterface client : getClients()) {
                pool.submit(() -> {
                    try {
                        client.returnTotemToTOC(player.getName(), index);
                    } catch (ClientDisconnectedException e) {
                        handleDisconnect(client);
                    }
                });
            }
        }
    }

    public void resolvingEventBroadcast(Event e) {
        synchronized (outputLock) {
            for (VirtualClientInterface client : getClients()) {
                pool.submit(() -> {
                    try {
                        client.updateForEvent(e);
                    } catch (ClientDisconnectedException exception) {
                        handleDisconnect(client);
                    }
                });
            }
        }
    }

    public void newEraBroadcast(int era) {
        synchronized (outputLock) {
            for (VirtualClientInterface client : getClients()) {
                pool.submit(() -> {
                    try {
                        client.showUpdateEra(era);
                    } catch (ClientDisconnectedException e) {
                        handleDisconnect(client);
                    }
                });
            }
        }
    }

    public void gameStartedBroadcast(ArrayList<Player> players, Board board) {
        synchronized (outputLock) {
            for (VirtualClientInterface client : getClients()) {
                pool.submit(() -> {
                    try {
                        client.updateStartGame(players, board);
                    } catch (ClientDisconnectedException e) {
                        handleDisconnect(client);
                    }
                });
            }
        }
    }

    public void pickedCardBroadcast(Player player, boolean row, boolean isBuilding, int index, int round) {
        synchronized (outputLock) {
            for (VirtualClientInterface client : getClients()) {
                pool.submit(() -> {
                    try {
                        client.pickedCard(player.getName(), row, isBuilding, index, round);
                    } catch (ClientDisconnectedException e) {
                        handleDisconnect(client);
                    }
                });
            }
        }
    }

    public void movedTotemBroadcast(Player player, int index) {
        synchronized (outputLock) {
            for (VirtualClientInterface client : getClients()) {
                pool.submit(() -> {
                    try {
                        client.movedTotem(player.getName(), index);
                    } catch (ClientDisconnectedException e) {
                        handleDisconnect(client);
                    }
                });
            }
        }
    }


    public void foodUpdateBroadcast(Player player, int update) {
        synchronized (outputLock) {
            for (VirtualClientInterface client : getClients()) {
                pool.submit(() -> {
                    try {
                        client.updatePlayerFood(player.getName(), update);
                    } catch (ClientDisconnectedException e) {
                        handleDisconnect(client);
                    }
                });
            }
        }
    }

    public void ppUpdateBroadcast(Player player, int update) {
        synchronized (outputLock) {
            for (VirtualClientInterface client : getClients()) {
                pool.submit(() -> {
                    try {
                        client.updatePlayerPP(player.getName(), update);
                    } catch (ClientDisconnectedException e) {
                        handleDisconnect(client);
                    }
                });
            }
        }
    }

    public void showTurnBroadcast(Player player) {
        synchronized (outputLock) {
            for (VirtualClientInterface client : getClients()) {
                pool.submit(() -> {
                    try {
                        client.showPlayerTurn(player.getName());
                    } catch (ClientDisconnectedException e) {
                        handleDisconnect(client);
                    }
                });
            }
        }
    }

    //SINGLE PLAYER MESSAGES

    public void allowSkipTurn(Player player) {
        synchronized (outputLock) {
            pool.submit(()->{
                try{
                    player.getVirtualClient().skipTurn();
                }catch(ClientDisconnectedException e){
                    handleDisconnect(player.getVirtualClient());
                }
            });
        }
    }

    public void invalidCardPick(Player player) {

        pool.submit(() -> {
            try {
                player.getVirtualClient().pickCardError();
            } catch (ClientDisconnectedException e) {
                handleDisconnect(player.getVirtualClient());
            }
        });
    }

    public void invalidBuildingPurchase(Player player) {
        pool.submit(() -> {
            try {
                player.getVirtualClient().pickCardError();
            } catch (ClientDisconnectedException e) {
                handleDisconnect(player.getVirtualClient());
            }
        });
    }

    public void invalidTotemPosition(Player player) {
        pool.submit(() -> {
            try {
                player.getVirtualClient().movedTotemError();
            } catch (ClientDisconnectedException e) {
                handleDisconnect(player.getVirtualClient());
            }
        });
    }

    public void showEndGameBroadcast(Player winner, List<PlayerScore> leaderboard) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.updateEndGame(winner.getName(), leaderboard);
                } catch (ClientDisconnectedException e) {
                    handleDisconnect(client);
                }
            });
        }
    }

    public void sendLeaderBoard(Player player, int leaderBoardPosition,  ArrayList<LeaderBoardData> leaderBoardDB){
        pool.submit(() -> {
            try {
                player.getVirtualClient().updateLeaderboardFromDB(leaderBoardPosition, leaderBoardDB);
            } catch (ClientDisconnectedException e) {
                handleDisconnect(player.getVirtualClient());
            }
        });
    }
}