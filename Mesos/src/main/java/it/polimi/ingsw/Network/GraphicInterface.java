package it.polimi.ingsw.Network;

import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Totem;

import java.util.ArrayList;

public interface GraphicInterface {
    void showError(String message);
    int askNumToPlayer();
    void showMessage(String message);
    int askPosition(ArrayList<OfferCard> path);
    void updateBoardStatus(Board board);
    void showCurrentPlayer(String username);
    void welcomeUser(String username);
    void invalidUsername();
    void showChosenTotem(Totem totem);
    void showAvailableTotems(ArrayList<Totem> availableTotems);
    void totemSelectionError();
    void showLobbyMenu();
    
}
