package it.polimi.ingsw.Network.Socket.Client.Command;



import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.Socket.Server.Command.Pick;
import it.polimi.ingsw.Network.Socket.Server.MessageFromServer;
import it.polimi.ingsw.Network.Socket.Server.SocketVirtualClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandFactoryClientSide {
    private static final Map<String, Function<Object, ClientCommand>> commands = new HashMap<>();
    public CommandFactoryClientSide() {
        //Updates
        commands.put("moved_totem",p->new MovedTotemCommand((SocketVirtualClient.TotemPosition) p));
        commands.put("picked_card",p->new PickedCardCommand((Pick) p));
        commands.put("game_started",data->new StartGameCommand((SocketVirtualClient.GameStartData)  data));
        commands.put("turn_order_card_position", p-> new TurnOrderPositionCommand((SocketVirtualClient.TotemPosition) p));
        commands.put("update_era",e->new UpdateEraCommand((Integer)e));

        //login
        commands.put("setnumplayers", payload -> new NumPlayersCommand());
        commands.put("msg", payload-> new ShowMSGCommand((String[]) payload));
        commands.put("colors",c->new ShowAvailableColorsCommand((ArrayList<Totem>) c));
        //Confirms
        commands.put("confirm_username", u-> new ConfirmUsernameCommand((String)u));
        commands.put("confirm_totem", t-> new ConfirmTotemCommand((Totem)t));
        commands.put("confirm_numplayers", p-> new ConfirmNumPlayers((Integer)p));
        //Errors
        commands.put("refuseconnection", payload-> new RefusedConnectionCommand());
        commands.put("setnumplayers_error", e-> new SetNumPlayersError());
        commands.put("totem_error",e->new TotemError((ArrayList<Totem>)e));
        commands.put("pick_error", e->new PickError());
    }

    public synchronized ClientCommand getCommand(MessageFromServer msg) {
        if(!commands.containsKey(msg.getHeader())){
            return new NotFoundCommand(msg.getHeader());
        }
        return commands.get(msg.getHeader()).apply(msg.getPayload());
    }
}
