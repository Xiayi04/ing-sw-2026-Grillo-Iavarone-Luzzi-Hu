package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Game.Player;

public class SetBonus extends Building{
    private int setCompleted;
    public SetBonus(int era, int price, int pp) {
        super(era, price, pp, "SetBonus");
       // meglio avere un metodo contatore  aparte o uso countset(?)  this.setCompleted = 0;
    }
   public int giveExtraFoodSet(Player player){
        return player.countSet()*5;
   }
}
