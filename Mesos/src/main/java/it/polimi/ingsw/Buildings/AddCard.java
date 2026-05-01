package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Game.Player;

public class AddCard extends Building implements BuildingInterface {

    public AddCard(int era, int price, int pp) {
        super(era, price, pp, "AddCard");
    }
    public int addArrow(){
        return 1;
    }

    public void acceptActivation(Visitor visitor, Player player){
        visitor.visit(this, player);
    }



}
