package it.polimi.ingsw.Network;

import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Game.Totem;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public interface GraphicInterface extends AutoCloseable{
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
    void showStartGame();
    void showLeaderboardFromDB(int playerPosition, List<LeaderBoardData> updatedDB);
    void showEndGameSuccessfully(String winner, List<PlayerScore> leaderboard);
    void showReturnToTOC(String playerName);
    @Override
    void close();
    default void openManual(){
        try{
            InputStream is = getClass().getResourceAsStream("/images/manual.pdf");
            if(is == null){
                throw new IllegalStateException("Resource not found");
            }

            File tempfile = File.createTempFile("manual", ".pdf");
            tempfile.deleteOnExit();

            Files.copy(is, tempfile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Desktop.getDesktop().open(tempfile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
