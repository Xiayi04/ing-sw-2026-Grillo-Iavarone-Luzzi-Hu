package it.polimi.ingsw.Model.Cards.Buildings;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;

public class BonusStarBuilding extends Building implements BuildingInterface {

    public BonusStarBuilding(int era, int price, int pp) {
        super(era, price, pp, "BonusStarBuilding");
    }

    public void acceptActivation(ActivationVisitor activationVisitor, Player player){
        activationVisitor.visit(this, player);
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }

}
