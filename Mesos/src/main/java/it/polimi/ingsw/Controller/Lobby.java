package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientDisconnectedException;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Network.VirtualClientInterface;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Lobby {
    private final ArrayList<TempPlayer> tempPlayers = new ArrayList<>();
    public static final AtomicBoolean isNumPlayersSet = new AtomicBoolean(false);
    public final AtomicInteger numPlayers = new AtomicInteger(0);
    public final LobbyManager serverController;

    public Lobby(LobbyManager serverController) {
        this.serverController = serverController;
    }

    public AtomicInteger getNumPlayers() {
        return numPlayers;
    }
    public AtomicBoolean IsNumPlayersSet() {
        return isNumPlayersSet;
    }

    public void addClient(VirtualClientInterface client){
        synchronized (tempPlayers){
            if(isNumPlayersSet.get() && tempPlayers.size() >= numPlayers.get()){
                try {
                    client.refuseConnection();
                } catch (ClientDisconnectedException ignored) {}
                return;
            }

            boolean present = tempPlayers.stream()
                    .map(TempPlayer::getClient)
                    .anyMatch(c -> c.equals(client));

            if(!present) {
                tempPlayers.add(new TempPlayer(client));
                if(tempPlayers.size()==1){
                    try {
                        client.updateFirstPlayer();
                    } catch (ClientDisconnectedException e) {
                        Server.terminate();
                    }
                }
                serverController.connectionInitializer(client);
            }else
                System.out.println("Error:Client already present in lobby");
        }
    }

    public void addUsername(String username, VirtualClientInterface client){
            serverController.checkUsername(username, client);
    }

    public void addTotem(Totem totem, VirtualClientInterface client){
            serverController.checkTotem(totem, client);
    }

    public void setNumPlayers(Integer numPlayers, VirtualClientInterface client){
            serverController.checkSetNumPlayers(numPlayers, client);
    }

    public synchronized ArrayList<Totem> getAvailableTotems(){
        ArrayList<Totem> totems = new ArrayList<>();
        totems.add(Totem.BLACK);
        totems.add(Totem.BLUE);
        totems.add(Totem.WHITE);
        totems.add(Totem.YELLOW);
        totems.add(Totem.ORANGE);

        ArrayList<Totem> takenTotems = new ArrayList<>();

        synchronized (tempPlayers){
            for (TempPlayer p : tempPlayers){
                if(p.getTempPlayerTotem() != null){
                    takenTotems.add(p.getTempPlayerTotem());
                }
            }
        }
        totems.removeAll(takenTotems);
        return totems;
    }

    public ArrayList<TempPlayer> getTempPlayers(){
        return tempPlayers;
    }

    public TempPlayer getTempPlayerByClient(VirtualClientInterface client){
        synchronized (tempPlayers){
            for (TempPlayer t : tempPlayers){
                if(t.getClient()!=null && t.getClient().equals(client))
                    return t;
            }
        }
        return null;
    }

    public void sendAvailableColors(VirtualClientInterface client) {
        synchronized (tempPlayers){
            client.updateAvailableTotems(getAvailableTotems());
        }
    }

    public void checkMoreThenEnoughPlayers(){
        synchronized (tempPlayers){
            if(!isNumPlayersSet.get()){
                System.out.println("Error: num of players not set");
                return;
            }

            if(tempPlayers.size() <= numPlayers.get()){ //the number is right or we wait for more players
                return;
            }

            for(int i = numPlayers.get(); i < tempPlayers.size(); i++){
                try {
                    tempPlayers.get(i).getClient().refuseConnection();
                } catch (ClientDisconnectedException ignored) {}
                //we ignore this exception because we only care about disconnections of active players
            }
        }
    }
}
