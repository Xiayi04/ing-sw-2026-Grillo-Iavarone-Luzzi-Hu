package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitor;
import it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitorInterface;
import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Game.Player;
import java.util.ArrayList;
import static java.lang.Math.abs;

public class PaintingEvent extends Event {
    private final Integer PaEveNumMinPainters;
    private final Integer PaEveMultiplierPP;
    private final Integer PaEvePointsLoss;
    //CONSTRUCTOR
    public PaintingEvent(int era, CardType cardType, EventName eventName, Integer minPainters, Integer multiplierPP, Integer pointsLoss) {
        super(era, cardType ,eventName);
        this.PaEveNumMinPainters = minPainters;
        this.PaEveMultiplierPP = multiplierPP;
        this.PaEvePointsLoss = pointsLoss;
    }

    public Integer getPaEveNumMinPainters() {
        return PaEveNumMinPainters;
    }
    public Integer getPaEveMultiplierPP() {
        return PaEveMultiplierPP;
    }
    public Integer getPaEvePointsLoss() { //returns a negative integer
        return PaEvePointsLoss;
    }

    public void printCard() {
        super.printCard();
        System.out.println("pittori minimi:"+PaEveNumMinPainters);
        System.out.println("moltiplicatore:"+PaEveMultiplierPP);
        System.out.println("punti persi:"+PaEvePointsLoss);
    }

    /**
     * checks the number of painters for each player, if that number is greater or equal to NumMinPainters
     * gives the player a bonus in PP, else if the number is smaller gives them a penalty
     * @param players: array containing the game's players
     */
    @Override
    public void resolveEvent(ArrayList<Player> players){
        DiscountVisitorInterface v = new DiscountVisitor();
        for(Player player : players){
            Integer numPainters = player.countTribeCardsByIcon(Icons.PAINTER.toString());

            if(numPainters >= PaEveNumMinPainters){
                player.modifyPP(abs(numPainters*PaEveMultiplierPP));
            }else{
                player.modifyPP(-abs(PaEvePointsLoss));
            }
            for(Building b : player.getBuilding()){
                b.acceptDiscountEvent(v,player,this);
            }
        }
    }
}
