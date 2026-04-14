package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.DiscountBuilding;
import it.polimi.ingsw.Buildings.Events;
import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Game.Player;

import java.util.ArrayList;

public class HuntingEvent extends Event {
    private final Integer HuEvePP;
    //CONSTRUCTOR
    public HuntingEvent(int era, CardType cardType, EventName eventName, Integer HuEvePP) {
        super(era, cardType, eventName);
        this.HuEvePP = HuEvePP;
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

        boolean checkHuEveBuilding = false;

        for(Player p : players){
            int numHunters = p.countTribeCardsByIcon(Icons.HUNTER);
            int foodBonus = numHunters;
            int bonusPP = HuEvePP * numHunters;

            if(!checkHuEveBuilding ){
                ArrayList<Building> buildings = p.getBuilding();

                for(Building b : buildings){

                    if(b.getName().equals("DiscountBuilding") && ((DiscountBuilding)b).getTypeEvents().equals(Events.HUNTEREVENT)){
                        foodBonus += numHunters;
                        bonusPP += numHunters;
                    }
                }
            }

            p.modifyPP(bonusPP);
            p.modifyFood(foodBonus);
        }
    }

}
