package it.polimi.ingsw.Network.Socket.Server.Command;

import java.io.Serializable;

public record UpdateFood(String playerName, int food) implements Serializable {
}
