package it.polimi.ingsw.Buildings;

public class DoubleBonusBuilding extends Building{
    public DoubleBonusBuilding(int era, int price, int pp, String name){
        super(era,price,pp,"DoubleBonusBuilding");
    }
    public int giveDouble(){
        return 2;
    }

}
