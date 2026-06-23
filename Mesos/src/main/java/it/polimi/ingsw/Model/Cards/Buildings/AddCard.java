package it.polimi.ingsw.Model.Cards.Buildings;

import it.polimi.ingsw.Visitors.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Visitors.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Visitors.BuildingVisitor.SpecialBuildings.AddCardException;
import it.polimi.ingsw.Visitors.BuildingVisitor.SpecialBuildings.AddCardVisitorInterface;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;


public class AddCard extends Building implements BuildingInterface {

    public AddCard(int era, int price, int pp) {
        super(era, price, pp, "AddCard");
    }
    public int addArrow(){
        return 1;
    }

    public void acceptActivation(ActivationVisitor activationVisitor, Player player){
        try {
            activationVisitor.visit(this, player);
        } catch (AddCardException e) {
            throw new AddCardException("");
        }
    }

    public int acceptAddCard(AddCardVisitorInterface visitor, Player player){
        return visitor.visit(this,player);
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }



}
