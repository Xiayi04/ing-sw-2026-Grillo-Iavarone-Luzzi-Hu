package it.polimi.ingsw.Buildings;
import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import  it.polimi.ingsw.Game.Player;
public class BonusStarBuilding extends Building implements BuildingInterface {

    public BonusStarBuilding(int era, int price, int pp) {
        super(era, price, pp, "BonusStarBuilding");
    }

    /*@Override
    public void buildingActivation(Player player){
        player.modifyStarCounter(3);
    }*/

    public void accept(Visitor visitor, Player player){
        visitor.visit(this, player);
    }

}
