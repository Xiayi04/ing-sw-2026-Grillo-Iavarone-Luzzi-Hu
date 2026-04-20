package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Game.Player;

public class NoMalusBuilding extends Building{
    public NoMalusBuilding(int era, int price, int pp) {
        super(era, price, pp, "NoMalusBuilding");
    }
    public boolean noMalus(){
        return true;
    }

    @Override
    public void buildingActivation(Player player) {}
}
