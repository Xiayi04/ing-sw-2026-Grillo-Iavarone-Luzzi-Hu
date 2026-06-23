package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Model.Game.Board;

import java.io.Serializable;

public record NextRoundData(Board board, int round) implements Serializable {
}
