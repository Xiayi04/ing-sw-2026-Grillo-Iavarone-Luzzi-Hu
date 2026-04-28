package it.polimi.ingsw.Game;

import it.polimi.ingsw.Buildings.*;

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
        for(Building b : order.getFirst().getBuilding())
            if(b instanceof BonusFood)
                order.getFirst().modifyFood(1);
        if(NumPlayers >= 4){
            order.get(1).modifyFood(1);
            for(Building b : order.get(1).getBuilding())
                if(b instanceof BonusFood)
                    order.get(1).modifyFood(1);
        }
        order.get(order.size()).modifyFood(-1);
    }


}
