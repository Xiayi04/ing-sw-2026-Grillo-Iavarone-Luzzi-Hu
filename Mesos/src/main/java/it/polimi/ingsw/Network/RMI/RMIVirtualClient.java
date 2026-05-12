package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;

public class RMIVirtualClient implements VirtualClientInterface {
//riceve le richieste dal server
    private final RemoteClientInterface client;

    public RMIVirtualClient(RemoteClientInterface client) {
        this.client = client;
    }


    @Override
    public void showMyTurn() {
        try {
            client.showMyTurn();
        } catch (RemoteException e) {
            System.out.println(" ");
        }
    }
    @Override
    public void updateBoard(Board board) {
        try {
            client.updateBoard(board);
        } catch (RemoteException e) {
            System.out.println("Client RMI disconnesso");
        }
    }
    @Override
    public void showError(String message) {
        try {
            client.showError(message);
        } catch (RemoteException e) {
            System.out.println(" ");
        }
    }


    @Override
    public void showMessage(String message) {
        try {
            client.showMessage(message);
        } catch (RemoteException e) {
            System.out.println(" ");
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







