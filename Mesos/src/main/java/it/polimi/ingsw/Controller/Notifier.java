package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Notifier {
    private final Object OutputLock = new Object();
    private final ArrayList<VirtualClientInterface> clients;

    public Notifier(ArrayList<VirtualClientInterface> clients){
        this.clients = clients;
    }


    //messaggi per broadcast
    public synchronized void gameStartedBroadcast(){
        synchronized (OutputLock){
            for(VirtualClientInterface client: clients){
                client.showStartGame();
            }
        }
    }

    public synchronized void addedNewPlayerBroadcast(Player newPlayer)  {
        synchronized (OutputLock){
            for (VirtualClientInterface client : clients) {
                try {
                    client.newPlayer(newPlayer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void pickedCardBroadcast(Player player, boolean row, boolean isBuilding, int index)  {
        new Thread(()->{
            synchronized (OutputLock){
                for(VirtualClientInterface client : clients){
                    client.pickedCard(player, row, isBuilding,index);
                }
            }
        }).start();
    }

    public synchronized void movedTotemBroadcast(Player player, int index) throws RemoteException {
        synchronized (OutputLock){
            for(VirtualClientInterface client : clients){
               client.movedTotem(player, index);
            }
        }
    }

    public void foodUpdateBroadcast( Player player, int update) throws RemoteException {
        synchronized (OutputLock){
            for(VirtualClientInterface client : clients){
                client.updatePlayerFood(player, update);
            }
        }
    }

    public void ppUpdateBroadcast( Player player, int update) throws RemoteException {
        synchronized (OutputLock){
            for(VirtualClientInterface client : clients){
                client.updatePlayerPP(player, update);
            }
        }
    }

    public void showTurnBroadcast( Player player) throws RemoteException {
        synchronized (OutputLock){
            for(VirtualClientInterface client : clients){
                client.showPlayerTurn(player);
            }
        }
    }

    //messaggi per singoli giocatori

    public void invalidCardPick( Player player) throws RemoteException {
        player.getVirtualClient().cardPickError();
    }

    public void invalidBuildingPurchase( Player player) throws RemoteException {
        player.getVirtualClient().buildingPurchaseError();
    }

    public synchronized void invalidTotemPosition(Player player) throws RemoteException {
        player.getVirtualClient().totemPositionError();
    }

    //messagi di notifica per inizio e fine gioco
    public void showStartGameBroadcast (String myName, ArrayList<Player> players, Totem myTotem, int myFood)  throws RemoteException {
        synchronized (OutputLock) {
            for (Player p : players) {
                p.getVirtualClient().showStartGame(myName,  players, myTotem, myFood);
            }
        }
    }

    public void showEndGameBroadcast(ArrayList<Player> players) throws RemoteException {
        synchronized (OutputLock){
            for(Player p : players){
                p.getVirtualClient().showEndGame();
            }
        }
    }





}
