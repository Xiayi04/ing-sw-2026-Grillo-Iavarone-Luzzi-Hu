package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.UI.Printer;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public String[] print(Printer printer){
        return printer.print(this);
    }

    /**
     * The method chooses an array of values that represents the food that every single
     * placement of TOC has, based on the number of the players
     * @param numPlayers
     */
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
        threePlayers[0] =  2;
        threePlayers[1] =  0;
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
        if(idx<0 || idx >= foodContainer.length){
            return -2;
        }
        return foodContainer[idx];
    }

}
