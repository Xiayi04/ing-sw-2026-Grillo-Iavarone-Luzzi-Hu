package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.PlayerScore;

import java.util.List;

public record EndGameData(Player winner, List<PlayerScore> leaderboard) {}
