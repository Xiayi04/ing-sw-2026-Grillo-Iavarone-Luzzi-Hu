package it.polimi.ingsw.Network.RMI;


import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIVirtualClient implements VirtualClientInterface {
//riceve le richieste dal server per notificare il client
    //serve al server per notificare il client
    private final RemoteClientInterface client;

    public RMIVirtualClient(RemoteClientInterface client) {
        this.client = client;
    }
    @Override
    public void showStartGame(String myName, ArrayList<Player> players, Totem myTotem, int myFood) {
        try {
            client.showStartGame();
        } catch (RemoteException e) {
            System.out.println("Error while sending start game notification");
        }
    }
    @Override
    public void showPlayerTurn(Player player) {
        try {
            client.showPlayerTurn(player);
        } catch (RemoteException e) {
            System.out.println(" ");
        }
    }
    @Override
    public void newPlayer (Player newPlayer) {
        try {
            client.addedPlayer(newPlayer);
        } catch (RemoteException e) {
            System.out.println("new player added");
        }
    }
    @Override
    public void pickedCard (Player player, boolean row, boolean isBuilding, int index) {
        try {
            client.showPickedCard(player, row, isBuilding, index);
        } catch (RemoteException e) {
            System.out.println(player + " picked a card ");
        }
    }
    @Override
    public void movedTotem(Player player, int index){
        try{
            client.movedTotem(player, index);
        } catch (RemoteException e) {
            System.out.println(player+ "'s totem moved to: " + index );
        }
    }
    @Override
    public void updatePlayerFood(Player player, int foodUpdated){
        try{
            client.foodUpdated(player, foodUpdated);
        } catch (RemoteException e) {
            System.out.println(player+"'s food updated: "+ foodUpdated);
        }
    }
    @Override
    public void updatePlayerPP(Player player, int PPUpdate){
        try{
            client.updatePlayerPP(player,  PPUpdate );
        } catch (RemoteException e) {
            System.out.println(player+"'s food updated: "+  PPUpdate);
        }
    }
    @Override
    public void cardPickError(){
        try{
            client.showErrorMessage("Building not purchasable");
        } catch (RemoteException e) {
            System.out.println("error card chosen not valid");
        }
    }
    @Override
    public void buildingPurchaseError(){
        try{
            client.showErrorMessage("Building not purchasable ");
        } catch (RemoteException e) {
            System.out.println("error: building not purchasable ");
        }
    }
    @Override
    public void totemPositionError(){
        try{
            client.showErrorMessage("Position not valid or already chosen");
        } catch (RemoteException e) {
            System.out.println("error:not valid or already chosen: ");
        }
    }
    @Override
    public void showEndGame() {
        try {
            client.showEndGame();
        } catch (RemoteException e) {
            System.out.println(" ");
        }
    }
}
//







