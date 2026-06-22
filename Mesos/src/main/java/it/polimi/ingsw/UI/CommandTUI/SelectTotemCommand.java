package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.UI.TUI;

public class SelectTotemCommand implements CommandTUI {
    private String totem;
    public  SelectTotemCommand(String totem){
        this.totem = totem;
    }

    @Override
    public void execute(ClientController clientController, TUI tui) {
        totem = totem.toUpperCase();
        totem = totem.trim();
        Totem t;
        try {
            t = Totem.valueOf(totem);
        } catch (IllegalArgumentException e) {
            clientController.setTmpTotem(null);
            return;
        }

        clientController.setTmpTotem(t);
    }
}
