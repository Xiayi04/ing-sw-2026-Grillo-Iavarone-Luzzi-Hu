package it.polimi.ingsw.Buildings;

public class SetBonus extends Building{
    private int setCompleted;
    public SetBonus(int era, int price, int pp, int setCompleted) {
        super(era, price, pp);
        this.setCompleted = 0;
    }
    /*public int getSetCompleted(){
        return setCompleted;
    }
    public void setCompletedCount(int setCompleted){
        this.setCompleted = setCompleted;
    }*/
    public int giveExtraFoodSet(){
        return 5;
    }
}
