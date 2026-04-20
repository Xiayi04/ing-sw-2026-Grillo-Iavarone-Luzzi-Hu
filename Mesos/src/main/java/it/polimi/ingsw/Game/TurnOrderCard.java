package it.polimi.ingsw.Game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.ceil;

public class TurnOrderCard {
    private ArrayList<Player> order;
    private int NumPlayers;

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
        if(NumPlayers >= 4)
            order.get(1).modifyFood(1);
        order.get(order.size()).modifyFood(-1);
    }


}
