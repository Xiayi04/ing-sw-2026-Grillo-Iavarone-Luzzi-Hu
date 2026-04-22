package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Game.Player;

public class MultiplierPPBuilderBuilding extends EndGameBuilding implements BuildingInterface {

    public MultiplierPPBuilderBuilding(int era, int price, int pp, Icons typeIcon) {
        super(era, price, pp,"MultiplierPPBuilderBuilding", Icons.BUILDER);
    }
    @Override
    public int countPP(Player player){
        return player.builderBonus()*2; /*devo creare un metodo in player che calcola builderbonus(?)*/
    }

    @Override
    public void accept(Visitor visitor, Player player) {
        visitor.visit(this, player);
    }

    @Override
    public void printCard() {
        super.printCard();
        System.out.println("icona:"+typeIcons.toString());
    }
}


