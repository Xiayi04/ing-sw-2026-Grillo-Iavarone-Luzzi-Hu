package it.polimi.ingsw.Model.Cards.Events;

import it.polimi.ingsw.Model.Cards.Buildings.Building;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitorInterface;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class SustenanceEvent extends Event {
    private final Integer SuEvePointsLossMultiplier;
    //CONSTRUCTOR
    public SustenanceEvent(int era, String cardType, String eventName, Integer pointsLossMultiplier) {
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

    public String[] print(Printer printer){
        return printer.print(this);
    }

    /**
     * This method resolves the Sustenance Event by counting the number of characters and picker each player has
     */
    @Override
    public void resolveEvent(ArrayList<Player> players) {
        if (players == null || players.size() < 2)
            throw new IllegalArgumentException("players ArrayList is not valid");

        for (Player player : players) {
            int numCards = player.getTribeCard().size();
            int pickerDiscount = player.countTribeCardsByIcon("PICKER") * 3;
            int buildingDiscount = 0;
            DiscountVisitorInterface visitor = new DiscountVisitor();

            for (Building b : player.getBuilding()) {
                buildingDiscount += abs(b.acceptDiscountEvent(visitor, player, this));
            }
            numCards = abs(numCards);
            pickerDiscount = abs(pickerDiscount);
            buildingDiscount = abs(buildingDiscount);

            if (numCards > pickerDiscount + buildingDiscount) {
                int penalty = numCards - pickerDiscount - buildingDiscount;

                if (penalty > player.getFood()) {
                    penalty = penalty - player.getFood();   //>0
                    player.modifyFood(-player.getFood());

                    player.modifyPP(penalty * this.SuEvePointsLossMultiplier); //SuEvePointsLossMultiplier<0    penalty * this.SuEvePointsLossMultiplier<0
                } else
                    player.modifyFood(-penalty);
            }
        }
    }
}



