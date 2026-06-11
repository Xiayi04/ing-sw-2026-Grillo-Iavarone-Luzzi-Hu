package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;

public class PickCommand implements CommandTUI {

    private  boolean first;
    private  boolean second;
    private  int third;
    boolean success = true;

    public PickCommand(String payload) {

        String[] args = payload.split("\\s+");

        if(args.length != 3){
            success = false;
        }

        try {
            this.first = parseBoolean(args[0]);
            this.second = parseBoolean(args[1]);
        } catch (IllegalArgumentException e) {
            success = false;
        }

        try {
            this.third = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            success = false;
        }
        if(this.third<=0)
            success = false;

    }

    private boolean parseBoolean(String s){
        return switch (s.toLowerCase()) {
            case "true", "1" -> true;
            case "false", "0" -> false;
            default -> throw new IllegalArgumentException("Booleani validi: true/false oppure 1/0");
        };
    }

    @Override
    public void execute(ClientController clientController) {
        if(!success){
            clientController.showError("Invalid pick format, please try again.");
            return;
        }
        clientController.requestLocalPickCard(first, second, third-1);
    }
}