package it.polimi.ingsw.Network.Socket.Client;


import java.io.Serializable;

public class MessageFromClient<T> implements Serializable {
    private String header;
    private T payload;

    public MessageFromClient(String message, T payload) {
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