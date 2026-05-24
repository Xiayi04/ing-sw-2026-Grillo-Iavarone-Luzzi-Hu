package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.ClientDisconnectedException;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Notifier {

    private final Object outputLock = new Object();
    private final ArrayList<VirtualClientInterface> clients;
    private final AtomicBoolean startTermProcedure = new AtomicBoolean(false);

    private final ExecutorService pool =
            Executors.newCachedThreadPool();

    public Notifier(ArrayList<VirtualClientInterface> clients) {
        this.clients = clients;
    }

    private List<VirtualClientInterface> getClients() {

        synchronized (outputLock) {
            return new ArrayList<>(clients);
        }
    }

    private synchronized void handleDisconnect(VirtualClientInterface disconnectedClient) {
        if(startTermProcedure.get())
            return;

        startTermProcedure.set(true);
        System.out.println("Client disconnected-> The game can't continue");
        if(!clients.contains(disconnectedClient)){
            return;
        }
        clients.remove(disconnectedClient);
        terminationSignalBroadcast();
        Server.terminate();
    }

    //BROADCAST

    public void terminationSignalBroadcast(){
        for(VirtualClientInterface client: clients){
            pool.submit(() -> {
                try {
                    //client.bruteForceEndGame();
                } catch (RuntimeException | IOException e) {
                    handleDisconnect(client);
                }
            });
        }
    }

    public void returnTotemOnTurnOrderBroadcast(Player player, int index) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.returnTotemToTOC(player.getName(), index);
                } catch (RuntimeException | IOException e) {
                    handleDisconnect(client);
                }
            });
        }
    }

    public void resolvingEventBroadcast(Event e) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.updateForEvent(e);
                } catch (RuntimeException | IOException exception) {
                    handleDisconnect(client);
                }
            });
        }
    }

    public void newEraBroadcast(int era) {

        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.showUpdateEra(era);
                } catch (ClientDisconnectedException e) {
                    handleDisconnect(client);
                }catch (IOException e) {
                    throw new RuntimeException("Generic Error");
                }
            });
        }
    }

    public void gameStartedBroadcast(ArrayList<Player> players, Board board) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.updateStartGame(players, board);
                } catch (ClientDisconnectedException e) {
                    handleDisconnect(client);
                }catch (IOException e) {
                    throw new RuntimeException("Generic Error");
                }
            });
        }
    }

    public void pickedCardBroadcast(Player player, boolean row, boolean isBuilding, int index) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.pickedCard(player.getName(), row, isBuilding, index);
                } catch (ClientDisconnectedException e) {
                    handleDisconnect(client);
                }catch(RuntimeException e){
                    System.out.println(e.getMessage());
                }
            });
        }
    }

    public void movedTotemBroadcast(Player player, int index) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.movedTotem(player.getName(), index);
                } catch (RuntimeException e) {
                    handleDisconnect(client);
                }
            });
        }
    }

    public void foodUpdateBroadcast(Player player, int update) {

        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.updatePlayerFood(player.getName(), update);
                } catch (RuntimeException e) {
                    handleDisconnect(client);
                }
            });
        }
    }

    public void ppUpdateBroadcast(Player player, int update) {

        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.updatePlayerPP(player.getName(), update);
                } catch (RuntimeException e) {
                    handleDisconnect(client);
                }
            });
        }
    }

    public void showTurnBroadcast(Player player) {

        for (VirtualClientInterface client : getClients()) {

            pool.submit(() -> {
                try {

                    client.showPlayerTurn(player.getName());

                } catch (RuntimeException e) {

                    handleDisconnect(client);

                }
            });
        }
    }

    //SINGLE PLAYER MESSAGES

    public void invalidCardPick(Player player) {

        pool.submit(() -> {
            try {

                player.getVirtualClient().pickCardError();

            } catch (RuntimeException | IOException e) {

                handleDisconnect(player.getVirtualClient());

            }
        });
    }

    public void invalidBuildingPurchase(Player player) {
        pool.submit(() -> {
            try {
                player.getVirtualClient().pickCardError();
            } catch (RuntimeException | IOException e) {
                handleDisconnect(player.getVirtualClient());
            }
        });
    }

    public void invalidTotemPosition(Player player) {
        pool.submit(() -> {
            try {
                player.getVirtualClient().movedTotemError();
            } catch (RuntimeException | IOException e) {
                handleDisconnect(player.getVirtualClient());
            }
        });
    }

    public void showEndGameBroadcast(Player winner, List<PlayerScore> leaderboard) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.showEndGame(winner.getName(), leaderboard);
                } catch (RuntimeException | IOException e) {

                    handleDisconnect(client);

                }
            });
        }
    }



    public void shutdown() {
        pool.shutdown();
    }
}