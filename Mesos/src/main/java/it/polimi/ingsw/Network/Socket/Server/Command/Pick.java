package it.polimi.ingsw.Network.Socket.Server.Command;

import java.io.Serializable;

public record Pick(String username, boolean isUpper, boolean isBuilding, int index, boolean skip, int round) implements Serializable {
    public Pick{
        if(username == null || username.isBlank()){
            throw new IllegalArgumentException();
        }
    }
}
