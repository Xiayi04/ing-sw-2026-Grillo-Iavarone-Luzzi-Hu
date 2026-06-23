package it.polimi.ingsw.Model.Cards.Buildings;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitorInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Model.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Model.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;

public class DiscountBuilding extends Building implements BuildingInterface {
    private final int foodBonus;
    private final int ppBonus;
    private final Icons typeIcons;
    private final Events typeEvents;


    public DiscountBuilding(int era, int price, int pp, int foodBonus, int ppBonus, Icons typeIcons, Events typeEvents) {
        super(era, price, pp,"DiscountBuilding");
        this.foodBonus = foodBonus;
        this.ppBonus = ppBonus;
        this.typeIcons = typeIcons;
        this.typeEvents = typeEvents;
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }

    public int getFoodBonus(){
        return foodBonus;
    }

    public int getPpBonus(){
        return ppBonus;
    }

    public Icons getTypeIcons(){
        return typeIcons;
    }

    public Events getTypeEvents() {
        return typeEvents;
    }

    public int getFoodBonusForPlayer(Player player){
        return foodBonus * player.countTribeCardsByIcon(typeIcons.toString());
    }

    public int getPpBonus(Player player) {
        return ppBonus * player.countTribeCardsByIcon(typeIcons.toString());
    }


    public void acceptActivation(ActivationVisitor activationVisitor, Player player) {
        activationVisitor.visit(this, player);
    }

    public int acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, SustenanceEvent event) {
        return visitor.visit(this, player, event);
    }

    public void acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, HuntingEvent event) {
        visitor.visit(this, player, event);
    }

    public void acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, PaintingEvent event) {
        visitor.visit(this, player, event);
    }

    public String getImagePath(){
        return "/images/cards/buildings/"+getName()+"_"+getTypeEvents().toString().toLowerCase()+"_"+getTypeIcons().toString().toLowerCase()+".png";
    }
}
