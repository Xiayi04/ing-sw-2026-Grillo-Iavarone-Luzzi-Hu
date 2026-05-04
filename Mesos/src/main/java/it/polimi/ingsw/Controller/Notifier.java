package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Game.Player;
import javafx.collections.ObservableList;

public class Notifier {
    private final Object OutputLock = new Object();


    public synchronized void addedNewPlayerBroadcast(Player player){
        synchronized (GameManager.playersLock){
            //
        }
    }

}
