package it.polimi.ingsw.Buildings;

public class DoubleBonusBuilding extends Building{
    public DoubleBonusBuilding(int era, int price, int pp){
        super(era,price,pp);
    }
    public int giveDouble(){
        return 2;
    }

}
