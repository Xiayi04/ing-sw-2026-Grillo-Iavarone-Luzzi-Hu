package it.polimi.ingsw.Cards.Events;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.DiscountBuilding;
import it.polimi.ingsw.Buildings.Events;
import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Game.Player;

import java.util.ArrayList;

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
     * @param players
     */
    @Override
    public void resolveEvent(ArrayList<Player> players){
        boolean checkPaEveBuilding = false;

        for(Player player : players){
            Integer numPainters = player.countTribeCardsByIcon(Icons.PAINTER);

            if(numPainters >= PaEveNumMinPainters){
                player.modifyPP(numPainters*PaEveMultiplierPP);
            }else{
                player.modifyPP(PaEvePointsLoss);
            }

            if (!checkPaEveBuilding){
                ArrayList<Building> building = player.getBuilding();

                for(Building b : building){
                    if(b.getName().equals("DiscountBuilding") && ((DiscountBuilding)b).getTypeEvents().equals(Events.PAINTINGEVENT) ){
                        int bonus = ((DiscountBuilding)b).getFoodBonusForPlayer(player);
                        player.modifyFood(bonus);
                        checkPaEveBuilding = true;
                        break;
                    }
                }
            }

        }
    }
}
