package it.polimi.ingsw.Model.Cards.Buildings;

import it.polimi.ingsw.Visitors.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Visitors.BuildingVisitor.EventBuildings.Discount.DiscountVisitorInterface;
import it.polimi.ingsw.Visitors.BuildingVisitor.EndGame.EndGameVisitorInterface;
import it.polimi.ingsw.Visitors.BuildingVisitor.EventBuildings.Shamanic.ShamanicVisitorInterface;
import it.polimi.ingsw.Visitors.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Visitors.BuildingVisitor.SetAndIconVisitor.SetAndIconVisitor;
import it.polimi.ingsw.Visitors.BuildingVisitor.SpecialBuildings.AddCardVisitorInterface;
import it.polimi.ingsw.Visitors.BuildingVisitor.SpecialBuildings.TurnOrderCardFoodBonus;
import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Model.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Model.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Model.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Model.Game.Player;

import java.io.Serial;
import java.io.Serializable;

public abstract class Building extends Card implements BuildingInterface, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int price;
    private final int pp;
    private final String name;
/*costruttore */
    public Building(int era, int price, int pp, String name){
        super(era, "BUILDING", true);
        this.price = price;
        this.pp = pp;
        this.name = name;
    }

    public int getPrice(){
        return price;
    }

    public int getPP(){
        return pp;
    }

    public String getName(){return name; }

    public void printCard(){
        System.out.println("era:"+getEra());
        System.out.println("prezzo:"+price);
        System.out.println("pp:"+pp);
        System.out.println("nome:"+name);
    }

    @Override
    public void acceptActivation(ActivationVisitor activationVisitor, Player player) {

    }

    @Override
    public int acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, SustenanceEvent event) {
        return 0;
    }

    @Override
    public void acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, HuntingEvent event) {

    }

    @Override
    public void acceptDiscountEvent(DiscountVisitorInterface visitor, Player player, PaintingEvent event) {

    }

    @Override
    public void acceptShamanicEvent(ShamanicVisitorInterface visitor, Player player, ShamanicEvent shamanicEvent) {

    }

    @Override
    public void acceptFoodBonus(TurnOrderCardFoodBonus visitor, Player player){
    };

    @Override
    public int acceptAddCard(AddCardVisitorInterface visitor, Player player) {
        return 0;
    }

    @Override
    public int acceptEndGame(EndGameVisitorInterface visitor, Player player) {
        return 0;
    }

    @Override
    public void acceptSameIconBonus(SetAndIconVisitor visitor, Player player, String icon) {

    }

    @Override
    public void acceptSetBonus(SetAndIconVisitor visitor, Player player) {

    }

    /**
     * builds the path to retrieve the correct image of the building
     */
    public String getImagePath(){
        return "/images/cards/buildings/"+getName()+".png";
    }
}
