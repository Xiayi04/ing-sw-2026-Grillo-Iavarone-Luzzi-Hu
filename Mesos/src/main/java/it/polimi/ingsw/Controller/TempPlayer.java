package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

public class TempPlayer {
    private final VirtualClientInterface client;
    private String name= null;
    private Totem totem = null;

    public TempPlayer(VirtualClientInterface client){
        this.client = client;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Totem getTempPlayerTotem() {
        return totem;
    }

    public void setTempPlayerTotem(Totem totem) {
        this.totem = totem;
    }

    public VirtualClientInterface getClient() {
        return client;
    }
}
