package it.polimi.ingsw.Network.Socket.Client.Command;



import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.Socket.Server.Command.*;
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
        commands.put("moved_totem",p->new MovedTotemCommand((TotemPosition) p));
        commands.put("picked_card",p->new PickedCardCommand((Pick) p));
        commands.put("game_started",data->new StartGameCommand((SocketVirtualClient.GameStartData)  data));
        commands.put("turn_order_card_position", p-> new TurnOrderPositionCommand((TotemPosition) p));
        commands.put("update_era",e->new UpdateEraCommand((Integer)e));
        commands.put("update_food", u-> new UpdateFoodCommand((UpdateFood) u));
        commands.put("update_pp", u-> new UpdatePPCommand((UpdatePP) u));
        commands.put("player_turn", u-> new PlayerTurnCommand((String) u));
        commands.put("next_round", u-> new NextRoundCommand((NextRoundData) u));
        commands.put("ping", _ ->new PingCommand());
        commands.put("end_game", d->new EndGameCommand((EndGameData) d));
        commands.put("resolving_event", e->new ResolvingEventCommand((Event) e));
        commands.put("db_leaderboard", d-> new DBLeaderBoardCommand((DBData) d));
        commands.put("skip", _ -> new SkipCommand());

        //login
        commands.put("setnumplayers", _ -> new NumPlayersCommand());
        commands.put("msg", payload-> new ShowMSGCommand((String[]) payload));
        commands.put("colors",c->new ShowAvailableColorsCommand((ArrayList<Totem>) c));
        //Confirms
        commands.put("confirm_username", u-> new ConfirmUsernameCommand((String)u));
        commands.put("confirm_totem", t-> new ConfirmTotemCommand((Totem)t));
        commands.put("chosen_num_players", p-> new ConfirmNumPlayers((Integer)p));
        commands.put("confirm_numplayers", n->new ConfirmNumPlayers((Integer)n));
        //Errors
        commands.put("skip_error", _ ->new SkipErrorCommand());
        commands.put("refuse_connection", _ -> new RefusedConnectionCommand());
        commands.put("setnumplayers_error", _ -> new SetNumPlayersError());
        commands.put("totem_error",e->new TotemError((ArrayList<Totem>)e));
        commands.put("pick_error", _ ->new PickError());
        commands.put("moved_totem_error", _ -> new TotemPositionError());
        commands.put("close_connection", _ ->new CloseConnectionCommand());
        commands.put("username_error", _ ->new UsernameErrorCommand());

    }

    public synchronized ClientCommand getCommand(MessageFromServer msg) {
        if(!commands.containsKey(msg.getHeader())){
            return new NotFoundCommand(msg.getHeader());
        }
        return commands.get(msg.getHeader()).apply(msg.getPayload());
    }
}
