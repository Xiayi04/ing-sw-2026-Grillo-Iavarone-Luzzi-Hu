package it.polimi.ingsw.Network.RMI;


import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class ClientRMI extends UnicastRemoteObject implements VirtualView {
    private final VirtualServer server;

    public ClientRMI (VirtualServer server) throws RemoteException {
        super();
        this.server = server;

    }

    @Override
    public void notifyStartGame(String name, ArrayList<Player> players, Totem totem, int food)
            throws RemoteException {

    }

    @Override
    public void notifyMyTurn() throws RemoteException {
        System.out.println("È il tuo turno!");
    }

    @Override
    public void updateBoardStatus(Board board) throws RemoteException {
        System.out.println("Board aggiornato");
        System.out.println(board);
    }

    @Override
    public void showCurrentPlayer(String playerName) throws RemoteException {
        System.out.println("Turno di: " + playerName);
    }

    @Override
    public void showError(String message) throws RemoteException {
        System.out.println("ERROR" + message);
    }

    @Override
    public void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings)
            throws RemoteException {

    }

    @Override
    public void endGame(int finalScore) throws RemoteException {
        System.out.println("Partita conclusa punteggio finale: " + finalScore);
    }

    public static void main(String[] args) throws RemoteException {
         try{
             Registry registry = LocateRegistry.getRegistry("localhost", 1234);
             VirtualServer server = (VirtualServer) registry.lookup("---MESOS_SERVER---");
             ClientRMI client = new ClientRMI(server);

             server.login("username", Totem.BLACK, client);

             System.out.println("Client connesso al server RMI");

           //  client.runCli(); ancora da completare

         } catch (Exception e) {
             e.printStackTrace();
         }
    }


}



