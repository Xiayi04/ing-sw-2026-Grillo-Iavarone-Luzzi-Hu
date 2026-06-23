package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Network.PlayerScore;

import java.io.Serializable;
import java.util.List;

public record EndGameData(String winner, List<PlayerScore> leaderboard) implements Serializable {}
