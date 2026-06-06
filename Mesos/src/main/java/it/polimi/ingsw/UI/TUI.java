package it.polimi.ingsw.UI;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Game.TurnOrderCard;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.ClientMain;
import it.polimi.ingsw.Network.GraphicInterface;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.UI.CommandTUI.CommandFactoryTUI;
import it.polimi.ingsw.UI.CommandTUI.CommandTUI;
import it.polimi.ingsw.UI.CommandTUI.ConnectionSelectionCommand;
import it.polimi.ingsw.UI.CommandTUI.SetNumPlayersRequestCommand;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStyle;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TUI implements GraphicInterface, Runnable {
    private final Object LOCK = new Object();
    private final ClientController controller;
    private final Printer printer = new Printer();
    private final Terminal terminal;
    private Scanner sc;
    private AtomicBoolean isRunning = new AtomicBoolean(true);
    private Totem[] TOC;


    public TUI(Terminal terminal, ClientController controller) {
        this.terminal = terminal;
        this.controller = controller;
        controller.setView(this);
        this.run();
    }

    private void initializeTOC(){
        TurnOrderCard toc = controller.getCurrentBoard().getTurnOrderCard();
        Totem[] newTOC = new Totem[toc.getOrder().size()];
        synchronized (toc){
            for (int i = 0; i < newTOC.length; i++){
                newTOC[i] = toc.getOrder().get(i).getTotem();
            }
        }
    }

    @Override
    public void showEndGameSuccessfully(String winner, List<PlayerScore> leaderboard) {
        synchronized (LOCK) {
            System.out.println("da realizzare");
        }
    }

    @Override
    public void showReturnToTOC(String playerName) {
        synchronized (LOCK) {
            System.out.println(playerName + " returned to TOC");
        }
    }

    @Override
    public void showStartGame() {
        synchronized (LOCK) {
            boardPrinter(controller.getCurrentBoard(), "Game Started");
        }
    }

    @Override
    public void showLeaderboardFromDB(int playerPosition, List<LeaderBoardData> updatedDB) {

    }

    @Override
    public void showPlayerFoodUpdate(String playerName, int food) {
        synchronized (LOCK) {
            StringBuilder update = new StringBuilder();
            update.append((playerName.equals(controller.getLocalPlayer().getName()) ? "You now have " : playerName.toUpperCase() + " now has "));
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
            update.append((playerName.equals(controller.getLocalPlayer().getName()) ? "You now have " : (playerName.toUpperCase() + " now has ")));
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
        synchronized (LOCK) {
            StringBuilder stringBuilder = new StringBuilder();

            if (controller.getLocalPlayer().getName().equals(name)) {
                stringBuilder.append("You selected ");
            } else
                stringBuilder.append(name.toUpperCase()).append(" has selected ");
            stringBuilder.append(isBuilding ? "a Building from the " : "a Character from the ");
            stringBuilder.append(isUpper ? "Upper" : "Lower");
            stringBuilder.append(" row");
            synchronized (controller.getCurrentBoard()) {
                screenCleaner();
                boardPrinter(controller.getCurrentBoard(), stringBuilder.toString());
            }
        }
    }

    @Override
    public void moveTotem(String username, int index) {
        synchronized (LOCK) {
            StringBuilder stringBuilder = new StringBuilder();
            if (controller.getLocalPlayer().getName().equals(username)) {
                stringBuilder.append("You moved your totem in position " + index + ".");
            } else {
                stringBuilder.append(username.toUpperCase()).append(" has moved his totem in position ").append(index).append(".");
            }
            screenCleaner();
            synchronized (controller.getCurrentBoard()) {
                boardPrinter(controller.getCurrentBoard(), stringBuilder.toString());
            }
        }
    }

    @Override
    public void askNumToPlayer() {
//        synchronized (LOCK){
//            int numPlayers;
//            while (true){
//                System.out.println("You are the first player, please select the number of players that will play the game (between 2 and 5)");
//                System.out.print(">");
//                Scanner sc = new Scanner(System.in);
//                numPlayers = sc.nextInt();
//                if (numPlayers >= 2 &&  numPlayers <= 5) {
//                    System.out.println("<Choice confirmed");
//                    break;
//                }
//                System.out.println("<Invalid choice");
//            }
//            CommandTUI cmd = new SetNumPlayersRequestCommand(numPlayers);
//
//
//        }
        System.out.println("First Player (pup-up work in progress)");
    }

    @Override
    public void showNextRound() {
        synchronized (LOCK) {
            screenCleaner();
            System.out.println("===NEXT ROUND===");
            synchronized (controller.getCurrentBoard()) {
                boardPrinter(controller.getCurrentBoard(), "");
            }
        }
    }

    @Override
    public void showAvailableTotems(ArrayList<Totem> availableTotems) {
        synchronized (LOCK) {
            String[] provv = new String[availableTotems.size()];
            for (int i = 0; i < availableTotems.size(); i++) {
                provv[i] = availableTotems.get(i).toString();
            }
            String totems;
            totems = String.join(", ", provv);

            System.out.println("These are the totems available at the moment: " + totems);
        }
    }

    @Override
    public void showError(String message) {
        synchronized (LOCK) {
            System.out.println(">Error: " + message);
        }
    }


    @Override
    public void showMessage(String message) {
        System.out.println("<:" + message);
    }


    @Override
    public void showCurrentPlayer(String username) {
        synchronized (LOCK) {
            //boardPrinter(controller.getCurrentBoard(),"");
            if (username.equals(controller.getLocalPlayer().getName()))
                System.out.println("It's your turn!");
            else
                System.out.println("It's " + username + "'s turn!");
        }

    }


    public void startMenu() {

        synchronized (LOCK) {
            screenCleaner();
            System.out.println(" === MESOS === ");
            System.out.println("Command List:");
            startMenuCommands();
        }

    }

    public void screenCleaner() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void startMenuCommands() {
        System.out.println("*Insert your username using -> username: 'your username'");
        System.out.println("*Find the available totem colors at the moment using -> colors");
        System.out.println("*Select the preferred totem's color using -> totem: 'chosen color'");
        System.out.println("*To see the Command List use -> help");
        System.out.println("*To quit the game use -> quit");
        System.out.println("Totem's colors : WHITE,ORANGE,BLACK,YELLOW,BLUE");
        System.out.println("NOTE: The game cannot start until the player selects a correct username and totem");
    }

    @Override
    public void run() {
        //I need to know which type of connection the user wants
        sc = new Scanner(System.in);
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
        while (isRunning.get()) {
            try {
                String content = sc.nextLine();
                if (content.equals("quit")) {
                    controller.close();
                    break;
                }
                CommandTUI command = factory.getCommand(content);

                if (command == null) {
                    continue;
                }

                new Thread(() -> {
                    command.execute(controller);
                }).start();
            } catch (IllegalStateException | NoSuchElementException e) {
                ClientMain.terminateClient();
                break;
            }
        }
        close();
    }

    //Errori

    //Board printing management
    public String[] printBuildingRow(ArrayList<Card> row) {
        Printer printer = new Printer();
        if (row.isEmpty()) {
            return printer.initialize(new String[0]);
        }

        String[] result = new String[7];
        result = printer.initialize(result);
        for (Card building : row) {
            String[] provv = building.print(printer);
            for (int i = 0; i < provv.length; i++) {
                result[i] = result[i] + provv[i];
            }
        }

        return cardMerger(result);
    }

    public String[] printCardsRow(ArrayList<Card> row) {
        Printer printer = new Printer();
        String[] result = new String[7];
        result = printer.initialize(result);
        int index = 0;
        for (Card card : row) {
            String[] provv = card.print(printer);
            for (int i = 0; i < provv.length; i++) {
                result[i] = result[i] + provv[i];
            }
        }
        return cardMerger(result);
    }

    public String[] printPath(TurnOrderCard turnOrderCard, ArrayList<OfferCard> path) {

        Printer printer = new Printer();
        String[] result = new String[7];
        result = printer.initialize(result);

        String[] provv = turnOrderCard.print(printer);
        for (int i = 0; i < provv.length; i++) {
            result[i] = result[i] + provv[i];
        }

        for (OfferCard offerCard : path) {
            String[] printedOC = offerCard.print(printer);
            for (int i = 0; i < printedOC.length; i++) {
                result[i] = result[i] + printedOC[i];
            }
        }

        String lowerRow = result[result.length - 1];
        List<Integer> junctions = new ArrayList<>();
        for (int i = 0; i < lowerRow.length(); i++) {
            char c = lowerRow.charAt(i);
            if (c == '└' || c == '┘') {
                junctions.add(i);
            }
        }
        int j = 1;
        StringBuilder sb = new StringBuilder(lowerRow);
        for (int i = 2; i < junctions.size() - 1; i = i + 2) {
            int distance = junctions.get(i + 1) - junctions.get(i);
            String index = "<" + j + ">";
            int left = (distance - index.length()) / 2;
            int end = junctions.get(i) + left + index.length();
            sb.replace(junctions.get(i) + left, end, index);
            j++;
        }
        result[result.length - 1] = sb.toString();

        return result;
    }

    public void boardPrinter(Board board, String update) {
        ArrayList<String> printedBoard = new ArrayList<String>();


        StringBuilder separator = new StringBuilder();
        separator.append("=".repeat(143));
        printedBoard.add(separator.toString());
        //printedBoard.add("BUILDINGS:");
        String[] buildingUpperRow = printBuildingRow(board.getUpperBuildingRow());
        //printedBoard.addAll(Arrays.asList(buildingUpperRow));
        //printedBoard.add("CARDS:");
        String[] cardsUpperRow = printCardsRow(board.getUpperCardRow());
        for (int i = 0; i < buildingUpperRow.length; i++) {
            cardsUpperRow[i] = cardsUpperRow[i] + " " + buildingUpperRow[i];
        }
        printedBoard.addAll(Arrays.asList(cardsUpperRow));
        //printedBoard.add("PATH:");
        String[] path = printPath(board.getTurnOrderCard(), board.getPath());
        printedBoard.addAll(Arrays.asList(path));
        //printedBoard.add("CARDS:");
        String[] cardsLowerRow = printCardsRow(board.getLowerCardsRow());
        printedBoard.addAll(Arrays.asList(cardsLowerRow));
        //printedBoard.add("BUILDINGS:");
        String[] buildingLowerRow = printBuildingRow(board.getLowerBuildingRow());
        printedBoard.addAll(Arrays.asList(buildingLowerRow));
        printedBoard.add("UPDATE:");
        printedBoard.add(update);

        for (String s : printedBoard) {
            System.out.println(s);
        }
    }

    public String[] cardMerger(String[] cards) {
        for (int i = 0; i < cards.length; i++) {
            if (i == 0) {
                cards[i] = cards[i].replace("┐┌", "┬");
            } else if (i == cards.length - 1) {
                cards[i] = cards[i].replace("┘└", "┴");
            } else {
                cards[i] = cards[i].replace("││", "│");
            }
        }
        String lowerRow = cards[cards.length - 1];
        List<Integer> junctions = new ArrayList<>();
        for (int i = 0; i < lowerRow.length(); i++) {
            char c = lowerRow.charAt(i);
            if (c == '└' || c == '┴' || c == '┘') {
                junctions.add(i);
            }
        }

        StringBuilder provvIdx = new StringBuilder(lowerRow);
        for (int i = 0; i < junctions.size() - 1; i++) {
            int distance = junctions.get(i + 1) - junctions.get(i);
            int j = i + 1;
            String index = "<" + j + ">";
            int left = (distance - index.length()) / 2;
            int end = junctions.get(i) + left + index.length();
            provvIdx.replace(junctions.get(i) + left, end, index);
        }

        cards[cards.length - 1] = provvIdx.toString();

        return cards;
    }

    public void pathPrinter(Board board, String update) {
        ArrayList<String> bluePrint = new ArrayList<>();

        bluePrint.add("PATH:");
        String[] path = printPath(board.getTurnOrderCard(), board.getPath());
        bluePrint.addAll(Arrays.asList(path));
        bluePrint.add("UPDATE:");
        bluePrint.add(update);
        for (String s : bluePrint) {
            System.out.println(s);
        }

    }


    @Override
    public synchronized void close() {
        if (!isRunning.get()) {
            return;
        }
        isRunning.set(false);

        sc.close();
        IOException ex = sc.ioException();
        if (ex != null) {
            System.err.println(ex.getMessage());
        }
    }
}
