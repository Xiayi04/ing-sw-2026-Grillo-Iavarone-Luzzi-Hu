package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.DiscountBuilding;
import it.polimi.ingsw.Buildings.Events;
import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Game.Player;

import java.util.ArrayList;

public class SustenanceEvent extends Event {
    private final Integer SuEvePointsLossMultiplier;
    //CONSTRUCTOR
    public SustenanceEvent(int era, CardType cardType, EventName eventName, Integer pointsLossMultiplier) {
        super(era, cardType, eventName);
        this.SuEvePointsLossMultiplier = pointsLossMultiplier;
    }

    public Integer getSuEvePointsLossMultiplier() {
        return SuEvePointsLossMultiplier;
    }

    public void printCard() {
        super.printCard();
        System.out.println("moltiplicatore:"+SuEvePointsLossMultiplier);
    }

    /**
     * This method resolves the Sustenance Event by counting the number of characters and picker each player has
     *
     * @param players </Player> players
     */
    public void resolveEvent(ArrayList<Player> players) {
        if(players.size()<2 || players == null)
            throw new IllegalArgumentException("players ArrayList is not valid");

        for(Player player : players){
            int numCards = player.getCard().size();
            int pickerDiscount = player.countTribeCardsByIcon(Icons.PICKER) * 3;
            int buildingDiscount = 0;

            ArrayList<Building> buildings = player.getBuilding();
            for(Building b : buildings){

                if(b.getName().equals("DiscountBuilding") && ((DiscountBuilding)b).getTypeEvents().equals(Events.SUSTENANCEEVENT)){
                    buildingDiscount += ((DiscountBuilding)b).getFoodBonus();
                }
            }

            if(numCards>pickerDiscount+buildingDiscount){
                int penalty = numCards-pickerDiscount-buildingDiscount;

                if(penalty> player.getFood()){
                    player.modifyFood(-player.getFood());
                    penalty -= player.getFood();
                    player.modifyPP(-penalty * SuEvePointsLossMultiplier );
                }
            }
        }
    }

}
