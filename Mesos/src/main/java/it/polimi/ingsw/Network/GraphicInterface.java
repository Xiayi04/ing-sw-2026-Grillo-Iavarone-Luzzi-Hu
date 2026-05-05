package it.polimi.ingsw.Network;

import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Totem;

import java.util.ArrayList;
import java.util.List;

public interface GraphicInterface {
    String askUsername();
    Totem askTotem(List<Totem> availableTotems);
    String showError( String message);
    int askNumToPlayer();
    String showMessage( String message);
    int askPosition(ArrayList<OfferCard> path);
    void updateBoardStatus(Board board);
    void showCurrentPlayer(String username);
}
