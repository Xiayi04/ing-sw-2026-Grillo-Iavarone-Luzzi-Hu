package it.polimi.ingsw.Model.Cards.Events;

import it.polimi.ingsw.Model.Cards.Buildings.Building;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Shamanic.ShamanicDoubleBonusVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Shamanic.ShamanicNoMalusVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Shamanic.ShamanicVisitorInterface;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class ShamanicEvent extends Event {
    private final Integer ShEvePenPoints;
    private final Integer ShEvePrizePoints;
    private Integer MaxStars ;
    private Integer MinStars ;
    //CONSTRUCTOR
    public ShamanicEvent(int era, String cardType, String eventName, Integer penPoints, Integer prizePoints) {
        super(era, cardType, eventName);
        this.ShEvePenPoints = penPoints;
        this.ShEvePrizePoints = prizePoints;
        this.MaxStars = 0;
        this.MinStars = 0;
    }

    public String[] print(Printer printer){
        return printer.print(this);
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
        if(players == null || players.size()<2 )
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
        if(players == null || players.size()<2)
            throw new IllegalArgumentException("players ArrayList is not valid");

        int minStars = 1000;
        int starCounter;
        for(Player p : players){
            starCounter = p.getStarCounter();
            if(starCounter<minStars)
                minStars = starCounter;
        }
        return minStars;
    }

    @Override
    public void resolveEvent(ArrayList<Player> players){
        if(players == null || players.size()<2)
            throw new IllegalArgumentException("players ArrayList is not valid");

        MaxStars = getMaxStars(players);
        MinStars = getMinStars(players);

        for(Player p : players){

            if(p.getStarCounter()==MaxStars){
                ShamanicVisitorInterface v = new ShamanicDoubleBonusVisitor();

                for(Building b : p.getBuilding()){
                    b.acceptShamanicEvent(v,p,this);
                }
                p.modifyPP(abs(this.getShEvePrizePoints()));
            }
            if (p.getStarCounter()==MinStars) {
                ShamanicVisitorInterface v = new ShamanicNoMalusVisitor();

                for(Building b : p.getBuilding()){
                    b.acceptShamanicEvent(v,p,this);
                }
                p.modifyPP(-abs(this.getShEvePenPoints()));
            }
            //p.getProxy().notifyAll(p,this);
        }
        //resetting the values of max e min stars for security reasons.
        MaxStars = 0;
        MinStars = 0;

    }

}
