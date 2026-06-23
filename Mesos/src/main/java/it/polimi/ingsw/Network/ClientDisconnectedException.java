package it.polimi.ingsw.Network;

/**
 *  Called when the server tries to contact a client but the connection fails
 */
public class ClientDisconnectedException extends RuntimeException {
    public ClientDisconnectedException(String message) {
        super(message);
    }
}
