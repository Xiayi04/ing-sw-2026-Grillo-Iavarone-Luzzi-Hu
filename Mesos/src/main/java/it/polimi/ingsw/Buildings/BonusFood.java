package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Game.Player;

public class BonusFood extends Building implements BuildingInterface {
    public BonusFood(int era, int price, int pp) {
        super(era, price, pp, "BonusFood");
    }
    public int giveExtraFood(){
        return 1;

    }

    /*@Override
    public void buildingActivation(Player player) {}*/

    @Override
    public void acceptActivation(Visitor visitor, Player player){
        visitor.visit( this, player);
    }
}
//ricommittato