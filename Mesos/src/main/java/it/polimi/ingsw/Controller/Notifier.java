package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Game.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Notifier {
    private final Object OutputLock = new Object();

    //messaggi per broadcast


    public synchronized void addedNewPlayerBroadcast(ArrayList<Player> players, Player newPlayer) throws RemoteException {
        synchronized (OutputLock){
            for (Player player : players) {
                player.getVirtualClient().newPlayer(newPlayer);
            }
        }
    }

    public synchronized void pickedCardBroadcast(ArrayList<Player> players, Player player, boolean row, boolean isBuilding, int index) throws RemoteException {
        synchronized (OutputLock){
            for(Player p : players){
                p.getVirtualClient().pickedCard(player, row, isBuilding,index);
            }
        }
    }

    public synchronized void movedTotemBroadcast(ArrayList<Player> players, Player player, int index) throws RemoteException {
        synchronized (OutputLock){
            for(Player p : players){
                p.getVirtualClient().movedTotem(player, index);
            }
        }
    }

    public void foodUpdateBroadcast(ArrayList<Player> players, Player player, int update) throws RemoteException {
        synchronized (OutputLock){
            for(Player p : players){
                p.getVirtualClient().updatePlayerFood(player, update);
            }
        }
    }

    public void ppUpdateBroadcast(ArrayList<Player> players, Player player, int update) throws RemoteException {
        synchronized (OutputLock){
            for(Player p : players){
                p.getVirtualClient().updatePlayerPP(player, update);
            }
        }
    }

    public void showTurnBroadcast(ArrayList<Player> players, Player player) throws RemoteException {
        synchronized (OutputLock){
            for(Player p : players){
                p.getVirtualClient().showPlayerTurn(player);
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




}
