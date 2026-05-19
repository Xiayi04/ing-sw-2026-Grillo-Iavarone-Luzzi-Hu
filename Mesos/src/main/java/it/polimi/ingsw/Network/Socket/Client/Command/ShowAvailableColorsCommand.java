package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ShowAvailableColorsCommand implements ClientCommand{
    ArrayList<Totem> totems;
    public ShowAvailableColorsCommand(ArrayList<Totem> totems){
        this.totems = totems;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        if(totems==null ||totems.isEmpty()){
            clientController.showErrorMessage("No more available totems :( ");
            return;
        }
        StringBuilder totemsString = new StringBuilder();
        for(int i=0;i<totems.size();i++){
            if(i== totems.size()-1){
                totemsString.append(totems.get(i).toString()).append(".");
                continue;
            }
            totemsString.append(totems.get(i).toString()).append(", ");
        }
        clientController.showMessage("These are the available colors at the moment:"+  totemsString.toString());
    }
}
