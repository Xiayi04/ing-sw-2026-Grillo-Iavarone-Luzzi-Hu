package it.polimi.ingsw.Network;

import java.io.Serializable;

public record PlayerScore(String username, int points, int food) implements Serializable {
}
