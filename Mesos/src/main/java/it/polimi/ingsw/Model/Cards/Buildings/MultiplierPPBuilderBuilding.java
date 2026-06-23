package it.polimi.ingsw.Model.Cards.Buildings;

import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EndGame.EndGameVisitorInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;

public class MultiplierPPBuilderBuilding extends EndGameBuilding implements BuildingInterface {

    public MultiplierPPBuilderBuilding(int era, int price, int pp, String typeIcon) {
        super(era, price, pp,"MultiplierPPBuilderBuilding", "BUILDER");
    }
    @Override
    public int countPP(Player player){
        return player.builderBonus()*2; /*devo creare un metodo in player che calcola builderbonus(?)*/
    }

    @Override
    public void acceptActivation(ActivationVisitor activationVisitor, Player player) {
        activationVisitor.visit(this, player);
    }

    @Override
    public void printCard() {
        super.printCard();
        System.out.println("icona:"+typeIcons.toString());
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }

    @Override
    public int acceptEndGame(EndGameVisitorInterface visitor, Player player) {
        return visitor.visit(this, player);
    }
}


