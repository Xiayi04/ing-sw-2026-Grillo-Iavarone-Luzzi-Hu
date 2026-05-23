package it.polimi.ingsw.Network;

public class ClientDisconnectedException extends RuntimeException {
    public ClientDisconnectedException(String message) {
        super(message);
    }
}
