package it.polimi.ingsw.Network.Socket.Server;


import java.io.Serializable;

public class MessageFromServer<T> implements Serializable {
    private String header;
    private T payload;

    public MessageFromServer(String message, T payload) {
        this.header = message;
        this.payload = payload;
    }

    public String getHeader() {
        return header;
    }

    public T getPayload() {
        return payload;
    }
}
