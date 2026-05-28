package it.polimi.ingsw.UI.CommandTUI;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandFactoryTUI {
    private Map<String, Function<String, CommandTUI>> noSepCommandsMap = new HashMap<>();
    private Map<String, Function<String, CommandTUI>> commandsMap = new HashMap<>();
    private final char separator = ':';
    public CommandFactoryTUI(){
        createCommandsMap();
        createNoSepCommandsMap();
    }

    public void createNoSepCommandsMap(){
        noSepCommandsMap.put("colors",key -> new AvailableColorsCommand());

    }

    public void createCommandsMap(){
        commandsMap.put("username", SetUsernameCommand::new);
        commandsMap.put("totem", SelectTotemCommand::new);
//        commandsMap.put("connect", c-> new ConnectionSelectionCommand(c));
        commandsMap.put("players", SetNumPlayersRequestCommand::new);
        commandsMap.put("pick", PickCommand::new);
        commandsMap.put("move", MoveTotemCommand::new);
    }



    public CommandTUI getCommand(String command){
        command = command.trim().toLowerCase();

        if(command.isEmpty()){
            return new UnknownCommand();
        }
        //Commands without payload
        if(!command.contains(String.valueOf(separator))){
            if(noSepCommandsMap.containsKey(command)){
                return noSepCommandsMap.get(command).apply(command);
            }
            return new UnknownCommand();
        }

        //Commands with payload
        String[] split = command.split(String.valueOf(separator));

        if(split.length != 2){
            return new InvalidFormatCommand();
        }

        String cmd = split[0];
        String payload = split[1].trim();

        if(commandsMap.containsKey(cmd)){
            try {
                return commandsMap.get(cmd).apply(payload);
            } catch (Exception e) {
                return new InvalidFormatCommand();
            }
        }

        return new UnknownCommand();
    }
}
