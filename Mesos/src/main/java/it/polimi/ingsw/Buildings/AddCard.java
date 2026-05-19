package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings.AddCardVisitor;
import it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings.TurnOrderCardFoodBonus;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;


public class AddCard extends Building implements BuildingInterface {

    public AddCard(int era, int price, int pp) {
        super(era, price, pp, "AddCard");
    }
    public int addArrow(){
        return 1;
    }

    public void acceptActivation(ActivationVisitor activationVisitor, Player player){
        activationVisitor.visit(this, player);
    }

    public int acceptAddCard(TurnOrderCardFoodBonus visitor, Player player){
        return visitor.visit(this,player);
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }



}
