package it.polimi.ingsw.Cards.Events;

import java.util.ArrayList;

public class SustenanceEvent extends Event {
    private final Integer SuEvePointsLossMultiplier;
    //CONSTRUCTOR
    public SustenanceEvent(int era, EventName eventName, Integer pointsLossMultiplier) {
        super(era, eventName);
        this.SuEvePointsLossMultiplier = pointsLossMultiplier;
    }

    public Integer getSuEvePointsLossMultiplier() {
        return SuEvePointsLossMultiplier;
    }

    /**
     * This method resolves the Sustenance Event by counting the number of characters and picker each player has
     *
     * @param players
     */
   /* public void foodCollection(ArrayList<Player> players) {
        if(players.size()<2 || players == null)
            throw new IllegalArgumentException("players ArrayList is not valid");

        for(Player p : players){
            Integer discount =  3 * getNumPickers(p);
            discount +=
            Integer numCharacters = getNumCharacters(p);
            if(numCharacters > numPickers)
                modifyPP(discount-numCharacters);

        }
    }*/
}
