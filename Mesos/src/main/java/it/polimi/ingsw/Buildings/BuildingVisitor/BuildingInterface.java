package it.polimi.ingsw.Buildings.BuildingVisitor;

import it.polimi.ingsw.Game.Player;

public interface BuildingInterface {
    public void accept(Visitor visitor, Player player);
}
