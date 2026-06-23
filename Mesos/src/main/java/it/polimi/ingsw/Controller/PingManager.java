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
    private final int delay = 5;

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

    /**
     * Schedules a periodic task to send ping signals to all connected clients at a fixed rate.
     */
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

    /**
     *Iterates through all connected clients to send a heartbeat ping signal.
     * It thread-safely handles any connection failures by triggering a disconnection
     * cleanup and closing resources.
     */
    private void sendPingToClients(){
        synchronized (clients) {

            for (VirtualClientInterface client : clients) {
                try {
                    client.ping();
                } catch (ClientDisconnectedException e) {
                    Server.closeConnection(client);
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
