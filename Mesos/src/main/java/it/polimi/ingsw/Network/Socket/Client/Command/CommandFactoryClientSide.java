package it.polimi.ingsw.Network.Socket.Client.Command;



import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandFactoryClientSide {
    private static Map<String, Function<String[], ClientCommand>> commands = new HashMap<>();
    public CommandFactoryClientSide() {
        commands.put("numplayers", payload -> new NumPlayersCommand( Arrays.toString(payload)));
        commands.put("login", payload -> new ClientLoginCommand(payload));
    }

    public static ClientCommand getCommand(String command) {
    command = command.toLowerCase();
    String[] split = command.split("#");
    String[] payload = split[1].split(",");

    ClientCommand cmd = commands.get(split[0]).apply(payload);
    return cmd;
    }
}
