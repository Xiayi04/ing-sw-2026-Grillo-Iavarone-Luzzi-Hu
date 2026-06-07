package it.polimi.ingsw.Network.Socket.Server.Command;

import java.io.Serializable;

public record UpdatePP(String playerName, int update)implements Serializable {
}
