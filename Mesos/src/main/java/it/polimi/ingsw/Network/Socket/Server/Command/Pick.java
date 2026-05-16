package it.polimi.ingsw.Network.Socket.Server.Command;

public record Pick(String username, boolean isUpper, boolean isBuilding, int index) {
    public Pick{
        if(username == null || username.isBlank()){
            throw new IllegalArgumentException();
        }
    }
}
