package it.polimi.ingsw.Buildings;

public class SameIconBuilding extends Building{

    public SameIconBuilding(int era, int price, int pp) {
        super(era, price, pp, "SameIconBuilding");

    }
    public int getFoodBonus(int pairOfInventor){
        return pairOfInventor*3;
    }
}
