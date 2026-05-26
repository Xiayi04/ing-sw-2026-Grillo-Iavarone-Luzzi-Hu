package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.ClientDisconnectedException;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Network.VirtualClientInterface;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PingManager implements AutoCloseable {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    final ArrayList<VirtualClientInterface> clients = new ArrayList<>();
    private final int delay = 2;

    public synchronized void addClientToPingList(VirtualClientInterface client) {
        synchronized (clients) {
            if(clients.isEmpty()) {
                clients.add(client);
                startPingTask();
                return;
            }
            clients.add(client);
        }
    }

    public void startPingTask(){
        Runnable pingTask = ()->{
            try {
                sendPingToClients();
            } catch (Exception e) {
                System.out.println("Error while sending ping to clients:"+e.getMessage());
            }
        };
        scheduler.scheduleAtFixedRate(pingTask, 0, delay, TimeUnit.SECONDS);
    }

    private void sendPingToClients(){
        synchronized (clients) {

            for (VirtualClientInterface client : clients) {
                try {
                    client.ping();
                } catch (ClientDisconnectedException e) {
                    Server.terminate();
                    close();
                }
            }
        }
    }

    @Override
    public void close(){
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}
