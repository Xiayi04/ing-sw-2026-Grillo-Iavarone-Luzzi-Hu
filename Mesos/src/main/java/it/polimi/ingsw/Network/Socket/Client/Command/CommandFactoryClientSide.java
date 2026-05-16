package it.polimi.ingsw.Network.Socket.Client.Command;



import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.Socket.Server.MessageFromServer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandFactoryClientSide {
    private static Map<String, Function<Object, ClientCommand>> commands = new HashMap<>();
    public CommandFactoryClientSide() {
        commands.put("setnumplayers", payload -> new NumPlayersCommand());
        commands.put("login", payload -> new ClientLoginCommand());
        commands.put("msg", payload-> new ShowMSGCommand((String[]) payload));
        commands.put("refuseconnection", payload-> new RefusedConnectionCommand());
        commands.put("newplayer", payload-> new NewPlayerCommand((Player)payload));
    }

    public synchronized ClientCommand getCommand(MessageFromServer msg) {
        if(!commands.keySet().contains(msg.getHeader())){
            return new NotFoundCommand(msg.getHeader());
        }
        return commands.get(msg.getHeader()).apply(msg.getPayload());
    }
}
