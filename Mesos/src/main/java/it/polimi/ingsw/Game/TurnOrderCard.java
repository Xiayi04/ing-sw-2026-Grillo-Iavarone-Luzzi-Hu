package it.polimi.ingsw.Game;

import it.polimi.ingsw.Buildings.*;
import it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings.BonusFoodVisitor;
import it.polimi.ingsw.Buildings.BuildingVisitor.SpecialBuildings.TurnOrderCardFoodBonus;
import it.polimi.ingsw.UI.Printer;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.ceil;

public class TurnOrderCard implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ArrayList<Player> order;
    private final int NumPlayers;
    private Integer[] foodContainer;

    public TurnOrderCard(int NumPlayers) {
        this.NumPlayers = NumPlayers;
        this.order = new ArrayList<Player>();
        initializeTOC(NumPlayers);
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
//    public void giveFood(){
//        if(order.isEmpty() || order.size() != NumPlayers){
//            throw  new IllegalArgumentException("Order is not valid");
//        }
//
//        int foodFirst = (int) ceil(NumPlayers / 2.0);
//        order.getFirst().modifyFood(foodFirst);
//        TurnOrderCardFoodBonus visitor = new BonusFoodVisitor();
//        for(Building b : order.getFirst().getBuilding()) {
//            if (b.acceptFoodBonus(visitor, order.getFirst()) == 1) {
//                order.getFirst().modifyFood(+1);
//                break;
//            }
//        }
//        if(NumPlayers >= 4){
//            order.get(1).modifyFood(1);
//            for(Building b : order.get(1).getBuilding()) {
//                if(b.acceptFoodBonus(visitor,order.get(1))==1){
//                    order.get(1).modifyFood(+1);
//                    break;
//                };
//            }
//        }
//        order.getLast().modifyFood(-1);
//    }

    public String[] print(Printer printer){
        return printer.print(this);
    }

    public void initializeTOC(int numPlayers){
        Map<Integer,Integer[]> map = new HashMap<>();
        map.put(2,makeTwoPlayers());
        map.put(3,makeThreePlayers());
        map.put(4,makeFourPlayers());
        map.put(5,makeFivePlayers());
        foodContainer = map.get(numPlayers);
    }

    private Integer[] makeTwoPlayers(){
        Integer[] twoPlayers = new Integer[2];
        twoPlayers[0] = 1;
        twoPlayers[1] = -1;
        return twoPlayers;
    }

    private Integer[] makeThreePlayers(){
        Integer[] threePlayers = new Integer[3];
        threePlayers[0] = 1;
        threePlayers[1] = 0;
        threePlayers[2] = -1;
        return threePlayers;
    }

    private Integer[] makeFourPlayers(){
        Integer[] fourPlayers = new Integer[4];
        fourPlayers[0] = 2;
        fourPlayers[1] = 1;
        fourPlayers[2] = 0;
        fourPlayers[3] = -1;
        return fourPlayers;
    }

    private Integer[] makeFivePlayers(){
        Integer[] fivePlayers = new Integer[5];
        fivePlayers[0] = 3;
        fivePlayers[1] = 1;
        fivePlayers[2] = 0;
        fivePlayers[3] = 0;
        fivePlayers[4] = -1;
        return fivePlayers;
    }

    public int getFoodByIndex(int idx){
        if(idx<0 || idx > foodContainer.length){
            return -2;
        }
        return foodContainer[idx];
    }

}
