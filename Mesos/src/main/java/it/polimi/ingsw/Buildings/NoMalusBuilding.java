package it.polimi.ingsw.Buildings;

public class NoMalusBuilding extends Building{
    public NoMalusBuilding(int era, int price, int pp) {
        super(era, price, pp);
    }
    public boolean noMalus(){
        return true;
    }
}
