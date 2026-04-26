package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.FileLoader.BuildingDTO;
import it.polimi.ingsw.FileLoader.CardDTO;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class ClientRMI extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;

    public ClientRMI (VirtualServer server) throws RemoteException {
        this.server = server;
        //devo ancora scrivere i metodi per il login


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
        System.out.println(" " + message);
    }

    @Override
    public void updateOtherPlayerStatus(String playerName, List<CardDTO> tribeCards, List<BuildingDTO> buildings)
            throws RemoteException {

    }

    @Override
    public void endGame(int finalScore) throws RemoteException {
        System.out.println("Partita conclusa punteggio finale: " + finalScore);
    }

    public static void main(String[] args) {

    }
}



