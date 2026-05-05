package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitor;
import it.polimi.ingsw.Buildings.BuildingVisitor.EventBuildings.Discount.DiscountVisitorInterface;
import it.polimi.ingsw.Buildings.DiscountBuilding;
import it.polimi.ingsw.Buildings.Events;
import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Game.Player;

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

    /**
     * This method resolves the Sustenance Event by counting the number of characters and picker each player has
     *
     * @param players </Player> players
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
                buildingDiscount += b.acceptDiscountEvent(visitor, player, this);
            }
            numCards = abs(numCards);
            pickerDiscount = abs(pickerDiscount);
            buildingDiscount = abs(buildingDiscount);

            if (numCards > pickerDiscount + buildingDiscount) {
                int penalty = numCards - pickerDiscount - buildingDiscount;

                if (penalty > player.getFood()) {
                    penalty -= player.getFood();
                    player.modifyFood(-player.getFood());

                    player.modifyPP(-penalty * this.SuEvePointsLossMultiplier);
                } else
                    player.modifyFood(-penalty);
            }
            player.getVirtualClient().notifyAll();
        }
    }
}



