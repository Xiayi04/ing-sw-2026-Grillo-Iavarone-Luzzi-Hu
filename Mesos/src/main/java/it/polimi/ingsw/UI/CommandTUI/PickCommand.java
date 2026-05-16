package it.polimi.ingsw.UI.CommandTUI;

import it.polimi.ingsw.Network.ClientController;

public class PickCommand implements CommandTUI {

    private final boolean first;
    private final boolean second;
    private final int third;

    public PickCommand(String payload) {

        String[] args = payload.split("\\s+");

        if(args.length != 3){
            throw new IllegalArgumentException();
        }

        this.first = parseBoolean(args[0]);
        this.second = parseBoolean(args[1]);

        try {
            this.third = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Terzo parametro deve essere un numero");
        }
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
        clientController.pickRequest(first, second, third);
    }
}