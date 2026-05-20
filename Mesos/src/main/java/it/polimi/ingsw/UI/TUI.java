package it.polimi.ingsw.UI;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Game.TurnOrderCard;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.GraphicInterface;
import it.polimi.ingsw.UI.CommandTUI.CommandFactoryTUI;
import it.polimi.ingsw.UI.CommandTUI.CommandTUI;
import it.polimi.ingsw.UI.CommandTUI.ConnectionSelectionCommand;

import java.util.*;

public class TUI implements GraphicInterface,Runnable {
private final Object LOCK = new Object();
private final ClientController controller;
private final Printer printer = new Printer();

public TUI(ClientController controller) {
    this.controller = controller;
    controller.setView(this);
    this.run();
}

    @Override
    public void showPlayerFoodUpdate(String playerName, int food) {
        synchronized (LOCK) {
            StringBuilder update = new StringBuilder();
            update.append((playerName.equals(controller.getLocalPlayer().getName()) ? "You now have " : playerName.toUpperCase()+" now has " ));
            update.append(food).append("food");
            synchronized (controller.getLocalPlayer()) {
                boardPrinter(controller.getCurrentBoard(), update.toString());
            }
        }
    }

    @Override
    public void showPlayerPPUpdate(String playerName, int pp) {
        synchronized (LOCK) {
            StringBuilder update = new StringBuilder();
            update.append((playerName.equals(controller.getLocalPlayer().getName()) ? "You now have " : (playerName.toUpperCase()+" now has ") ));
            update.append(pp).append("Prestige Points");
        }
    }

    @Override
    public void showLobbyMenu() {

    }

    @Override
    public void showErrorMessage(String message) {
        synchronized (LOCK) {
            System.out.println("Error: " + message);
        }
    }

    @Override
    public void pickCard(String name, boolean isUpper, boolean isBuilding, int index) {
        synchronized (LOCK){
            StringBuilder stringBuilder = new StringBuilder();

            if(controller.getLocalPlayer().getName().equals(name)){
                stringBuilder.append("You selected ");
            }else
                stringBuilder.append(name.toUpperCase()).append(" has selected ");
            stringBuilder.append(isBuilding ? "a Building from the " : "a Character from the ");
            stringBuilder.append(isUpper ? "Upper" : "Lower");
            stringBuilder.append(" row");
            synchronized (controller.getLocalPlayer()) {
                boardPrinter(controller.getCurrentBoard(), stringBuilder.toString());
            }
        }
    }

    @Override
    public void moveTotem(String username, int index) {

    }

    @Override
    public void showAvailableTotems(ArrayList<Totem> availableTotems) {
        String[] provv = new String [availableTotems.size()];
        for (int i = 0; i < availableTotems.size(); i++) {
            Totem totem = availableTotems.get(i);
        }
        String totems;
        totems = String.join(", ", provv);
        synchronized (LOCK) {
            System.out.println("These are the totems available at the moment: "+totems);
        }
    }

    @Override
    public void showError(String message) {
        synchronized (LOCK) {
            System.out.println("Error: " + message);
        }
    }


    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }


    @Override
    public void showCurrentPlayer(String username) {

    }


    public void startMenu(){

            synchronized (LOCK){
                System.out.println(" === MESOS === ");
                System.out.println("Command List:");
                startMenuCommands();
            }

    }

    public void startMenuCommands(){
        System.out.println("*Insert your username using -> username: 'your username'");
        System.out.println("*Find the available totem colors at the moment using -> colors");
        System.out.println("*Select the preferred totem's color using -> totem: 'chosen color'");
        System.out.println("*To see the Command List use -> help");
        System.out.println("*To quit the game use -> quit");
        System.out.println("Totem's colors : WHITE,ORANGE,BLACK,YELLOW,BLUE");
        System.out.println("NOTE: The game cannot start until the player selects a correct username and totem");
    }

    @Override
    public void run(){
        //I need to know which type of connection the user wants
        Scanner sc = new Scanner(System.in);
        int connection = -1;

        while (true) {
            System.out.print("Please choose the preferred connection protocol (0:RMI/1:Socket) :");
            try {
                connection = sc.nextInt();

                if (connection == 0 || connection == 1) {
                    System.out.println("Connecting to the server...");
                    break;
                }

                System.out.println("Invalid input. Please enter 0 or 1.");

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. You must enter an integer number.");
                sc.nextLine();
            }
        }

        try {
            CommandTUI c = new ConnectionSelectionCommand(connection);
            c.execute(controller);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        CommandFactoryTUI factory = new CommandFactoryTUI();
        startMenu();
        sc.nextLine();
        while(true){
            String content = sc.nextLine();
            if(content.equals("quit")){
                break;
            }
            CommandTUI command = factory.getCommand(content);

            if(command == null){
                continue;
            }

            new Thread(()->{
                command.execute(controller);
            }).start();
        }
    }

    //Errori

    //Board printing management
    public String[] printBuildingRow(ArrayList<Card> row) {
        Printer printer = new Printer();
        if(row.isEmpty()){
            return printer.initialize(new String[0]);
        }

        String[] result = new String[7];
        result = printer.initialize(result);
        for(Card building : row){
            String[] provv = building.print(printer);
            for(int i = 0; i < provv.length; i++){
                result[i] = result[i]+provv[i];
            }
        }
        return result;
    }

    public String[] printCardsRow(ArrayList<Card> row) {
        Printer printer = new Printer();
        String[] result = new String[7];
        result = printer.initialize(result);
        for(Card card : row){
            String[] provv = card.print(printer);
            for(int i = 0; i < provv.length; i++){
                result[i] = result[i]+provv[i];
            }
        }
        return result;
    }

    public String[] printPath(TurnOrderCard turnOrderCard, ArrayList<OfferCard> path) {

        Printer printer = new Printer();
        String[]  result = new String[7];
        result = printer.initialize(result);

        String[] provv = turnOrderCard.print(printer);
        for(int i = 0; i < provv.length; i++){
            result[i] = result[i]+provv[i];
        }

        for(OfferCard offerCard : path){
            String[] printedOC =  offerCard.print(printer);
            for(int i = 0; i < printedOC.length; i++){
                result[i] = result[i]+printedOC[i];
            }
        }

        return result;
    }

    public void boardPrinter(Board board, String update){
        ArrayList<String> printedBoard =  new ArrayList<String>();

        printedBoard.add("BUILDINGS:");
        String[] buildingUpperRow = printBuildingRow(board.getUpperBuildingRow());
        printedBoard.addAll(Arrays.asList(buildingUpperRow));
        printedBoard.add("CARDS:");
        String[] cardsUpperRow = printCardsRow(board.getUpperCardRow());
        printedBoard.addAll(Arrays.asList(cardsUpperRow));
        printedBoard.add("PATH:");
        String[] path = printPath(board.getTurnOrderCard(), board.getPath());
        printedBoard.addAll(Arrays.asList(path));
        printedBoard.add("CARDS:");
        String[] cardsLowerRow = printCardsRow(board.getLowerCardsRow());
        printedBoard.addAll(Arrays.asList(cardsLowerRow));
        printedBoard.add("BUILDINGS:");
        String[] buildingLowerRow = printBuildingRow(board.getLowerBuildingRow());
        printedBoard.addAll(Arrays.asList(buildingLowerRow));
        printedBoard.add("UPDATE:");
        printedBoard.add(update);

        for(String s : printedBoard){
            System.out.println(s);
        }
    }



}
