package it.polimi.ingsw.Game;

import java.util.ArrayList;
import java.util.List;

public class TurnOrderCard {
    private List<Totem> order;
    private int NumPlayers;

    public TurnOrderCard(int NumPlayers) {
        this.NumPlayers = NumPlayers;
        this.order = new ArrayList<>();
    }

    public int getNumPlayers() {
        return NumPlayers;
    }

    public Totem getHead(){  //restituice il primo totem della fila
        if(!order.isEmpty()){
            return order.get(0);
        }
        return null;

    }

    public void giveFood(){
        for(int i = 0; i < order.size(); i++){
            Totem t = order.get(i);
        }

    }

    public void getOrder(){

    }
}
