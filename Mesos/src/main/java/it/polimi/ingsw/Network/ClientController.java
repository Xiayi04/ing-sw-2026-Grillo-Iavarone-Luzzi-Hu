package it.polimi.ingsw.Network;


import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Totem;


import java.util.ArrayList;
import java.util.List;

public class ClientController {
    /*deve avere una view*/
    private final GraphicInterface view;
    //riferimento all'if che manda messaggi al server
    private final ClientInterface clientInterface;

    public ClientController(GraphicInterface view,ClientInterface clientInterface){
        this.view = view;
        this.clientInterface = clientInterface;
    }

    private Board currentBoard;


    public void askForLogin(List<Totem> availableTotems) {
        String username = view.askUsername();

        while (username == null) {
            view.showError("Username not valid.");
            username = view.askUsername();
        }

        Totem chosenTotem = view.askTotem(availableTotems);

        while(!availableTotems.contains(chosenTotem)){
            view.showError("Totem not available ");
            chosenTotem = view.askTotem(availableTotems);
        }
        clientInterface.login(username,chosenTotem);
    }

    public void askNumPlayers(){
        int numPlayers = view.askNumToPlayer();

        while(numPlayers < 2 || numPlayers > 5){
            view.showError("Number of Players is wrong ");
            numPlayers = view.askNumToPlayer();
        }
        clientInterface.setNumPlayers(numPlayers);
    }

    public void showStartGame(){
        view.showMessage("The game started ");
    }
    public void askForTotemMove(ArrayList<OfferCard> path){
        int chosenPosition = view.askPosition(path);
        while(chosenPosition < 0 || chosenPosition >= path.size()){
            view.showError("Position not valid ");
            chosenPosition = view.askPosition(path);
        }
        clientInterface.setTotemPosition(chosenPosition);
    }
    public void showMyTurn(){
        view.showMessage("It's your turn ");
    }
    public void updateBoardStatus(Board updatedBoard){
        this.currentBoard = updatedBoard;
        view.updateBoardStatus(currentBoard);
    }
    public void showCurrentPlayer(String playerName){
        view.showCurrentPlayer(playerName);
    }

    public void showError(String message){
        view.showError(message);
    }

    public void showMessage(String message){
        view.showMessage(message);
    }


    public void showEndGame(){
        view.showMessage("Game ended ");
    }














}
