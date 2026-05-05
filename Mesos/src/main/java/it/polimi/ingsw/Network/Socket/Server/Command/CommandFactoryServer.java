package it.polimi.ingsw.Network.Socket.Server.Command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandFactoryServer {
    private static Map<String, Function<String[], ServerCommand>> commands = new HashMap<>();
    public CommandFactoryServer() {
        commands.put("login", payload ->new LoginCommand(payload[0],payload[1]));
        commands.put("setnumplayers", payload -> new SetNumPlayersCommand(payload));
    }



    public ServerCommand getCommand(String command) {
        command = command.toLowerCase();
        String[] split = command.split("#");

        String[] payload = null;
        if(split.length>1){
            payload = split[1].split(",");
        }

        ServerCommand cmd = commands.get(split[0]).apply(payload);
        return  cmd;
    }

}
