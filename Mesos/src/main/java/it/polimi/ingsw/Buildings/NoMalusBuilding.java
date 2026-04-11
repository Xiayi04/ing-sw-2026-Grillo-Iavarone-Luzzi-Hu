package it.polimi.ingsw.Buildings;

public class NoMalusBuilding extends Building{
    public NoMalusBuilding(int era, int price, int pp,String name) {
        super(era, price, pp, "NoMalusBuilding");
    }
    public boolean noMalus(){
        return true;
    }
}
