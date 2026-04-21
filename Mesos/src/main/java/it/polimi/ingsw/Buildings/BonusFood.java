package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Game.Player;

public class BonusFood extends Building{
    public BonusFood(int era, int price, int pp) {
        super(era, price, pp, "BonusFood");
    }
    public int giveExtraFood(){
        return 1;

    }

    @Override
    public void buildingActivation(Player player) {}
}
//ricommittato