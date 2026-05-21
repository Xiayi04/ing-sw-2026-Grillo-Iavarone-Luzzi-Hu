package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Notifier {

    private final Object outputLock = new Object();

    private final ArrayList<VirtualClientInterface> clients;

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

    private void handleDisconnect() {
        //gestione della disconnessione in qualche modo

        System.out.println("Client disconnected");
    }

    //BROADCAST

    public void returnTotemOnTurnOrderBroadcast(Player player, int index) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.returnTotemToTOC(player, index);
                } catch (RuntimeException | IOException e) {
                    handleDisconnect();
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
                    handleDisconnect();
                }
            });
        }
    }

    public void newEraBroadcast(int era) {

        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.showUpdateEra(era);
                } catch (RuntimeException | IOException e) {
                    handleDisconnect();
                }
            });
        }
    }

    public void gameStartedBroadcast(ArrayList<Player> players, Board board) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.updateStartGame(players, board);
                } catch (RuntimeException | IOException e) {
                    handleDisconnect();
                }
            });
        }
    }

    public void pickedCardBroadcast(Player player, boolean row, boolean isBuilding, int index) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.pickedCard(player, row, isBuilding, index);
                } catch (RuntimeException  e) {
                    handleDisconnect();
                }
            });
        }
    }

    public void movedTotemBroadcast(Player player, int index) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.movedTotem(player, index);
                } catch (RuntimeException e) {
                    handleDisconnect();
                }
            });
        }
    }

    public void foodUpdateBroadcast(Player player, int update) {

        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.updatePlayerFood(player, update);
                } catch (RuntimeException e) {
                    handleDisconnect();
                }
            });
        }
    }

    public void ppUpdateBroadcast(Player player, int update) {

        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.updatePlayerPP(player, update);
                } catch (RuntimeException e) {
                    handleDisconnect();
                }
            });
        }
    }

    public void showTurnBroadcast(Player player) {

        for (VirtualClientInterface client : getClients()) {

            pool.submit(() -> {
                try {

                    client.showPlayerTurn(player);

                } catch (RuntimeException e) {

                    handleDisconnect();

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

                handleDisconnect();

            }
        });
    }

    public void invalidBuildingPurchase(Player player) {
        pool.submit(() -> {
            try {
                player.getVirtualClient().pickCardError();
            } catch (RuntimeException | IOException e) {
                handleDisconnect();
            }
        });
    }

    public void invalidTotemPosition(Player player) {
        pool.submit(() -> {
            try {
                player.getVirtualClient().movedTotemError();
            } catch (RuntimeException | IOException e) {
                handleDisconnect();
            }
        });
    }

    public void showEndGameBroadcast(Player winner, List<PlayerScore> leaderboard) {
        for (VirtualClientInterface client : getClients()) {
            pool.submit(() -> {
                try {
                    client.showEndGame(winner, leaderboard);
                } catch (RuntimeException | IOException e) {

                    handleDisconnect();

                }
            });
        }
    }



    public void shutdown() {
        pool.shutdown();
    }
}