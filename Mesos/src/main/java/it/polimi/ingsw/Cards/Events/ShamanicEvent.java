package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Game.Player;

import java.util.ArrayList;

public class ShamanicEvent extends Event {
    private final Integer ShEvePenPoints;
    private final Integer ShEvePrizePoints;
    //CONSTRUCTOR
    public ShamanicEvent(int era, CardType cardType, EventName eventName, Integer penPoints, Integer prizePoints) {
        super(era, cardType, eventName);
        this.ShEvePenPoints = penPoints;
        this.ShEvePrizePoints = prizePoints;
    }

    /**
     *  @return a negative integer
     */
    public Integer getShEvePenPoints() { //returns a negative integer
        return ShEvePenPoints;
    }

    public Integer getShEvePrizePoints() {
        return ShEvePrizePoints;
    }

    public void printCard() {
        super.printCard();
        System.out.println("punti penalità:"+ShEvePenPoints);
        System.out.println("punti guadagnati:"+ShEvePrizePoints);
    }

    public int getMaxStars(ArrayList<Player> players){
        if(players.size()<2 || players == null)
            throw new IllegalArgumentException("players ArrayList is not valid");
        int maxStars = 0;

        for(Player p : players){
            int starCounter = p.getStarCounter();
            if(starCounter> maxStars)
                maxStars = starCounter;
        }
        return maxStars;
    }

    public int getMinStars(ArrayList<Player> players){
        if(players.size()<2 || players == null)
            throw new IllegalArgumentException("players ArrayList is not valid");

        int minStars = 1000;
        int starCounter = 0;
        for(Player p : players){
            starCounter = p.getStarCounter();
            if(starCounter<minStars)
                minStars = starCounter;
        }
        return minStars;
    }

    @Override
    public void resolveEvent(ArrayList<Player> players){
        if(players.size()<2 || players == null)
            throw new IllegalArgumentException("players ArrayList is not valid");

        int maxStars = getMaxStars(players);
        int minStars = getMinStars(players);

        for(Player p : players){

            if(p.getStarCounter()==maxStars){

                boolean exists = p.getBuilding().stream()
                        .anyMatch(b -> b.getName().equals("DoubleBonusBuilding"));

                if(exists)
                    p.modifyPP(ShEvePrizePoints* 2);
                else
                    p.modifyPP(ShEvePrizePoints);

            } else if (p.getStarCounter()==minStars) {

                boolean exists =  p.getBuilding().stream()
                        .anyMatch(b -> b.getName().equals("NoMalusBuilding"));

                if(!exists)
                    p.modifyPP(ShEvePenPoints);
            }
        }

    }

    /*public static boolean containsBuilding(Player player, String buildingName){
        ArrayList<Building> buildings = player.getBuilding();

        for(Building b : buildings){
            if(b.getName().equals(buildingName))
                return true;
        }
    }*/
}
