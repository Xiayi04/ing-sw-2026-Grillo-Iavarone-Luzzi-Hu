package it.polimi.ingsw.Game;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings.BonusFoodVisitor;
import it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings.TurnOrderCardFoodBonus;
import it.polimi.ingsw.UI.Printer;

import java.io.Serializable;
import java.util.ArrayList;
import static java.lang.Math.ceil;

public class TurnOrderCard implements Serializable {
    private static final long serialVersionUID = 1L;
    private final ArrayList<Player> order;
    private final int NumPlayers;

    public TurnOrderCard(int NumPlayers) {
        this.NumPlayers = NumPlayers;
        this.order = new ArrayList<Player>();
    }

    public int getNumPlayers() {
        return NumPlayers;
    }

    public Totem getHead(){
        return order.removeFirst().getTotem();
    }

    public ArrayList<Player> getOrder(){
        return order;
    }

    /**
     * This method gives the food to the players before the start of the round
     * The quantity of given food is based on their position in the ArrayList order
     */
    public void giveFood(){
        if(order.isEmpty() || order.size() != NumPlayers){
            throw  new IllegalArgumentException("Order is not valid");
        }

        int foodFirst = (int) ceil(NumPlayers / 2.0);
        order.getFirst().modifyFood(foodFirst);
        TurnOrderCardFoodBonus visitor = new BonusFoodVisitor();
        for(Building b : order.getFirst().getBuilding()) {
            if (b.acceptFoodBonus(visitor, order.getFirst()) == 1) {
                order.getFirst().modifyFood(+1);
                break;
            }
        }
        if(NumPlayers >= 4){
            order.get(1).modifyFood(1);
            for(Building b : order.get(1).getBuilding()) {
                if(b.acceptFoodBonus(visitor,order.get(1))==1){
                    order.get(1).modifyFood(+1);
                    break;
                };
            }
        }
        order.getLast().modifyFood(-1);
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }


}
