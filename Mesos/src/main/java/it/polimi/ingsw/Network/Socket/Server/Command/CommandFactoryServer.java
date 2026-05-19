package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.Socket.Client.MessageFromClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandFactoryServer {
    private static Map<String, Function<Object, ServerCommand>> commands = new HashMap<>();
    public CommandFactoryServer() {
        commands.put("username", u-> new SetUsernameCommand((String) u));
        commands.put("totem", t-> new SetTotemCommand((Totem) t));
        commands.put("setnumplayers", num-> new SetNumPlayersCommand((int)num));
        commands.put("pick", pick->new PickCommand((Pick)pick));
        commands.put("position", i->new MoveTotemCommand((int)i));
        commands.put("available_colors",k-> new AvailableColorsCommand());
    }



    public ServerCommand getCommand(MessageFromClient msg) {
        if(!commands.keySet().contains(msg.getHeader())){
            return new UnknownCommand();
        }

        ServerCommand cmd = commands.get(msg.getHeader()).apply(msg.getPayload());
        return  cmd;
    }

}
