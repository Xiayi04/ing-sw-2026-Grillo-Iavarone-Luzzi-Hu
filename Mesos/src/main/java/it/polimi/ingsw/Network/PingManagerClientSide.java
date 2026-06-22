package it.polimi.ingsw.Network;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PingManagerClientSide implements AutoCloseable{
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public synchronized void start(ServerConnection toServer){
        Runnable pingTask = toServer::ping;
        scheduler.scheduleAtFixedRate(pingTask, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void close(){
        if(!scheduler.isShutdown()){
            scheduler.shutdown();
        }
    }
}
