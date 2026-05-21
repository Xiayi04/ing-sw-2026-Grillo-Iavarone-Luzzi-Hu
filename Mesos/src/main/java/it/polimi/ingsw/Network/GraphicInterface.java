package it.polimi.ingsw.Network;

import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Totem;

import java.util.ArrayList;

public interface GraphicInterface {
    void showError(String message);
    void showMessage(String message);
    void showCurrentPlayer(String username);
    void showAvailableTotems(ArrayList<Totem> availableTotems);
    void showPlayerFoodUpdate(String playerName, int food);
    void showPlayerPPUpdate(String playerName, int pp);
    void showLobbyMenu();
    void showErrorMessage(String message);
    void pickCard(String name, boolean isUpper, boolean isBuilding, int index);
    void moveTotem(String username, int index);
    void askNumToPlayer();
    void showNextRound();

}
