package it.polimi.ingsw.UI;

import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.GraphicInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TUI implements GraphicInterface {
private final Object LOCK = new Object();
    @Override
    public String askUsername() {
        Scanner input = new Scanner(System.in);
        String username;
        synchronized (LOCK) {
            System.out.print("Please enter your username: ");
            username = input.nextLine();
            while(!username.equals("") && !username.equals(" ")){//condizione da controllare
                System.out.println("Invalid input.");
                System.out.print("Please enter your username: ");
                username = input.nextLine();
            }
            LOCK.notifyAll();
        }
        return username;
    }

    @Override
    public Totem askTotem(List<Totem> availableTotems) {
        return null;
    }

    @Override
    public String showError(String message) {
        return "";
    }

    @Override
    public int askNumToPlayer() {
        return 0;
    }

    @Override
    public String showMessage(String message) {
        return "";
    }

    @Override
    public int askPosition(ArrayList<OfferCard> path) {
        return 0;
    }

    @Override
    public void updateBoardStatus(Board board) {

    }

    @Override
    public void showCurrentPlayer(String username) {

    }
}
