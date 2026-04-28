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
import java.util.Scanner;

public class ClientRMI extends UnicastRemoteObject implements VirtualView {

    private final VirtualServer server;
    private String username;

    public ClientRMI(VirtualServer server) throws RemoteException {
        super();
        this.server = server;
    }

    private void runCli() throws RemoteException {
        Scanner scan = new Scanner(System.in);

        System.out.print("Username: ");
        username = scan.nextLine();

        System.out.print("Totem: ");
        String totemInput = scan.nextLine();

        Totem chosenTotem = Totem.valueOf(totemInput.toUpperCase());

        server.login(username, chosenTotem, this);

        System.out.println("Client connected to the server RMI");


    }

    @Override
    public void showStartGame(String name, ArrayList<Player> players, Totem totem, int food)
            throws RemoteException {
        System.out.println("Game started");
    }

    @Override
    public void showMyTurn() throws RemoteException {
        System.out.println("It's your turn");
    }

    @Override
    public void updateBoardStatus(Board board) throws RemoteException {
        System.out.println("Board updated:");
        System.out.println(board);
    }

    @Override
    public void showCurrentPlayer(String playerName) throws RemoteException {
        System.out.println("Turn of: " + playerName);
    }

    @Override
    public void showError(String message) throws RemoteException {
        System.out.println("ERROR: " + message);
    }

    @Override
    public void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings)
            throws RemoteException {

        System.out.println("Player update: " + playerName);

        System.out.println("Tribe cards of " + playerName + ":");
        for (Card card : tribeCards) {
            System.out.println("  " + card);
        }

        System.out.println("Buildings of " + playerName + ":");
        for (Building building : buildings) {
            System.out.println("  " + building);
        }
    }

    @Override
    public void showEndGame(int finalScore) throws RemoteException {
        System.out.println("Game ended. Final score: " + finalScore);
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1234);

            VirtualServer server = (VirtualServer) registry.lookup("---MESOS_SERVER---");

            ClientRMI client = new ClientRMI(server);

            client.runCli();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}