package it.polimi.ingsw.Network.Socket.Server.Command;

import java.io.Serializable;

public record TotemPosition(String playerName, int index) implements Serializable {}
