package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ServerRMI extends UnicastRemoteObject implements VirtualServer {
    private GameManager gameManager;
    private final ArrayList<VirtualView> clients = new ArrayList<>();

    //Costruttore
    public ServerRMI( GameManager gameManager) throws RemoteException {
        super();
        this.gameManager = gameManager;
    }

    public static void main( String[] args ) {
        final String serverName = "---MESOS_SERVER---";
        try {
            GameManager gameManager = new GameManager(new ArrayList<Player>(),5,new Board());
            ServerRMI server = new ServerRMI(gameManager);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(serverName, server);
            System.out.println("---Server is ready!---");
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    //Corpo dei metodi che stanno nel virtualServer
    @Override
    public void login(String username,Totem chosenColor,VirtualView client) throws RemoteException{
        synchronized (this.clients){
            if (gameManager.getPlayers().size() >= 5) {
                client.showError("Partita piena!");
                return;
            }
            Player player = new Player(username, chosenColor, 0);
            gameManager.addPlayer(player);
            this.clients.add(client);
            System.out.println(username + "si è connesso");
        }
    }

    public synchronized void buyBuilding(String username,boolean isUpper,int index) throws RemoteException{
        Player p = gameManager.getPlayerByName(username);
        ArrayList<Building> buildings;
        if (isUpper) {
            // Se isUpper è true, puntiamo alla riga superiore
            buildings = gameManager.getBoard().getUpperBuildingRow();
        } else {
            // Altrimenti puntiamo alla riga inferiore
            buildings = gameManager.getBoard().getLowerBuildingRow();
        }
        Building chosen = buildings.get(index);
        int cost = chosen.getPrice();
        if(p.getFood()>= cost) {
            p.modifyFood(-cost);
            Building picked = (Building) gameManager.getBoard().pickCard(isUpper,true,index);
            p.getBuilding().add(picked);
            notifyAllClients();
        }else{
            System.out.println("INSUFFICIENT FOOD! (Requested :" + cost +")");
        }

    }

    private void notifyAllClients() {
        for(VirtualView client : new ArrayList<>(clients)){
            try{
                client.updateBoardStatus(gameManager.getBoard());
                // i client osservamo i cambiamenti che avvengono sul server
                client.showCurrentPlayer("username");
            } catch (Exception e) {
                System.err.println("Impossibile contattare un client, potrebbe essere disconnesso");
            }
        }
    }

    @Override
    public synchronized void moveTotem(String username, int pathIndex)throws RemoteException{
        Player player = gameManager.getPlayerByName(username);
        OfferCard chosenCard = gameManager.getBoard().getPath().get(pathIndex);
        if(chosenCard.isOccupied()){
            System.out.println("La posizione" +pathIndex + " è già occupata");
        }
        gameManager.getBoard().moveTotem(player,chosenCard);
        System.out.println("Il giocatore " + player.getName() + " ha occupato la posizione " + pathIndex);
        notifyAllClients();
    }

    @Override
    public synchronized void pickCard(String username, boolean isUpper,boolean isBuilding,int index) throws RemoteException{
        Player p = gameManager.getPlayerByName(username);
        try{
            if(isBuilding){
                this.buyBuilding(username,isUpper,index);
            }else{
                this.takeCharacter(username,isUpper,index);
            }
        } catch (Exception e) {
            System.err.println("Errore durante pickCard: " + e.getMessage());
        }

    }

    public void takeCharacter(String username, boolean isUpper, int index) throws RemoteException{
        Player player = gameManager.getPlayerByName(username);
        gameManager.takeCharacter(player,isUpper,index);
        System.out.println(username + "ha preso il personaggio:" +player.getName());
        notifyAllClients();
    }
}
