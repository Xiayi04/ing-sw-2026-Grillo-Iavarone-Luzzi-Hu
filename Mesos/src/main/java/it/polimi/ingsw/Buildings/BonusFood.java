package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings.TurnOrderCardFoodBonus;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;

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
    public void acceptActivation(ActivationVisitor activationVisitor, Player player){
        activationVisitor.visit( this, player);
    }

    @Override
    public int acceptFoodBonus(TurnOrderCardFoodBonus visitor, Player p){
        return visitor.visit( this, p);
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }
}
//ricommittato