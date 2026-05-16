package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitor;
import it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitorInterface;
import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class HuntingEvent extends Event {
    private final Integer HuEvePP;
    //CONSTRUCTOR
    public HuntingEvent(int era, String cardType, String eventName, Integer HuEvePP) {
        super(era, cardType, eventName);
        this.HuEvePP = HuEvePP;
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }

    public Integer getHuEvePP() {
        return HuEvePP;
    }

    public void printCard(){
        super.printCard();
        System.out.println("punti pp:"+HuEvePP);
    }

    @Override
    public void resolveEvent(ArrayList<Player> players){
        if(players == null || players.size()< 2){
            throw new IllegalArgumentException("Players list is not valid");
        }


        for(Player p : players){
            int numHunters = p.countTribeCardsByIcon(Icons.HUNTER.toString());
            int foodBonus = numHunters;
            int bonusPP = HuEvePP * numHunters;
            DiscountVisitorInterface v = new DiscountVisitor();

            for(Building b : p.getBuilding()){
                b.acceptDiscountEvent(v,p,this);
            }
            p.modifyPP(abs(bonusPP));
            p.modifyFood(abs(foodBonus));
        }
    }

}
