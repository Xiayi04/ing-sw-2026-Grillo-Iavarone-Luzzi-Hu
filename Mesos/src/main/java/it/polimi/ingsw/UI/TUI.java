package it.polimi.ingsw.UI;

import it.polimi.ingsw.Model.Cards.Buildings.Building;
import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.Cards.Characters.Character;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.ClientMain;
import it.polimi.ingsw.Network.GraphicInterface;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.UI.CommandTUI.CommandFactoryTUI;
import it.polimi.ingsw.UI.CommandTUI.CommandTUI;
import it.polimi.ingsw.UI.CommandTUI.ConnectionSelectionCommand;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class TUI implements GraphicInterface, Runnable {
    private final Object LOCK = new Object();
    private final ClientController controller;
    private final Printer printer = new Printer();
    private final Terminal terminal;
    private final LineReader reader;
    private final PrintWriter writer;
    private final Object tocLock = new Object();
    private final Object turnLock = new Object();
    private final ExecutorService printExecutor = Executors.newSingleThreadExecutor();
    private final PrintingHandler printingHandler;
    private final AtomicBoolean cardsPopUp = new AtomicBoolean(false);
    AttributedStyle communicationsStyle = AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW);
    AttributedStyle yellowStyle = AttributedStyle.DEFAULT.foreground(220).bold();
    AttributedStyle redStyle = AttributedStyle.DEFAULT.foreground(202).bold();
    AttributedStyle blackStyle = AttributedStyle.DEFAULT.foreground(242).bold();
    AttributedStyle blueStyle = AttributedStyle.DEFAULT.foreground(38).bold();
    AttributedStyle whiteStyle = AttributedStyle.DEFAULT.foreground(15).bold();
    AttributedStyle msgStyle = AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold();
    AttributedStyle errStyle = AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold();
    AttributedString writeHere = new AttributedStringBuilder()
            .append("> ", yellowStyle).toAttributedString();
    String connectionChoice = new AttributedStringBuilder()
            .append("Please choose the preferred connection protocol [0:RMI/1:Socket] :\n" + writeHere, yellowStyle).toAnsi();
    String connectingToServer = new AttributedStringBuilder()
            .append("Connecting to the server...", yellowStyle).toAnsi();
    String invalidConnectionChoice = new AttributedStringBuilder()
            .append("Invalid input. Please enter 0 or 1.", yellowStyle).toAnsi();
    String invalidConnectionInput = new AttributedStringBuilder()
            .append("Invalid input. You must enter an integer number.", yellowStyle).toAnsi();
    private Scanner sc;
    private AtomicBoolean isRunning = new AtomicBoolean(true);
    private Totem[] TOC;
    private String currentPlayer;
    private AtomicBoolean numPlayersPopUp = new AtomicBoolean(false);
    private Totem turnTotem;
    private Map<Totem, AttributedStyle> totemStylesMap = new HashMap<>();

    public TUI(Terminal terminal, LineReader reader, ClientController controller) {
        this.terminal = terminal;
        this.reader = reader;
        this.writer = terminal.writer();
        this.printingHandler = new PrintingHandler(terminal, writer, reader);
        printingHandler.run();
        this.controller = controller;
        controller.setView(this);
    }


    @Override
    public void run() {
        if (terminal != null) {
            terminal.puts(InfoCmp.Capability.clear_screen);
            terminal.flush();
        }
        //I need to know which type of connection the user wants
        int connection = -1;
        while (true) {
            try {
                String input = reader.readLine(connectionChoice);
                connection = Integer.parseInt(input.trim());

                if (connection == 0 || connection == 1) {
                    writer.println(connectingToServer);
                    terminal.flush();
                    break;
                }

                writer.println(invalidConnectionChoice);
                terminal.flush();

            } catch (NumberFormatException e) {
                writer.println(invalidConnectionInput);
                terminal.flush();
            } catch (UserInterruptException | EndOfFileException e) {
                ClientMain.terminateClient();
                return;
            }
        }

        try {
            CommandTUI c = new ConnectionSelectionCommand(connection);
            c.execute(controller, this);
        } catch (Exception e) {
            writer.println(e.getMessage());
            terminal.flush();
        }
        initializeColorsMap();
        CommandFactoryTUI factory = new CommandFactoryTUI();
        if (!numPlayersPopUp.get()) {
            startMenu();
        }
        while (isRunning.get()) {
            try {
                String content = reader.readLine(writeHere.toAnsi());

                if (content.trim().equals("quit")) {
                    ClientMain.terminateClient();
                    break;
                }

                if (content.trim().contains("cards")){
                    handleCardsRequest(content);
                    continue;
                }

                if (numPlayersPopUp.get()) {
                    handleNumPlayersInput(content);
                    continue;
                }

                if(cardsPopUp.get()){
                    cardsPopUp.set(false);
                    printingHandler.unlockPipeline();
                    boardPrinter(controller.getCurrentBoard(),"");
                    continue;
                }

                CommandTUI command = factory.getCommand(content);
                if (command == null) {
                    continue;
                }

                new Thread(() -> {
                    command.execute(controller, this);
                }).start();

            } catch (UserInterruptException | EndOfFileException e) {
                controller.close();
                break;
            } catch (Exception e) {
                ClientMain.terminateClient();
                break;
            }
        }


        close();
    }

    private void handleCardsRequest(String request) {
        if(request.contains(":")){
            String[] split = request.split(":");
            showCardsCommandTui(split[1]);
        }else{
            showCardsCommandTui("");
        }
    }

    private void handleNumPlayersInput(String input) {
        input = input.trim();

        if (!numPlayersPopUp.get()) {
            return;
        }
        int num = 0;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            showError("Invalid input. Please enter an integer number.");
            synchronized (LOCK) {
                writer.print(new AttributedStringBuilder().append("> ", msgStyle).toAnsi());
                writer.flush();
            }
            return;
        }

        if (num < 2 || num > 5) {
            showError("Invalid input. Please choose a number between 2 and 5.");
            synchronized (LOCK) {
                writer.print(new AttributedStringBuilder().append("> ", msgStyle).toAnsi());
                writer.flush();
            }
            return;
        }
        numPlayersPopUp.set(false);
        controller.setNumPlayers(num);
        printingHandler.unlockPipeline();
        startMenu();
    }

    private void initializeColorsMap() {
        totemStylesMap.clear();
        totemStylesMap.put(Totem.BLACK, blackStyle);
        totemStylesMap.put(Totem.WHITE, whiteStyle);
        totemStylesMap.put(Totem.BLUE, blueStyle);
        totemStylesMap.put(Totem.YELLOW, yellowStyle);
        totemStylesMap.put(Totem.ORANGE, redStyle);
    }

    //TOC management
    private void addInHead(Totem totem) {
        synchronized (tocLock) {
            int i = 0;
            for (; i < TOC.length; i++) {
                if (TOC[i] == null)
                    break;
            }
            TOC[i] = totem;
        }
    }

    private void removeToTOC(Totem totem) {
        synchronized (tocLock) {
            for (int i = 0; i < TOC.length; i++) {
                if (TOC[i] == totem) {
                    TOC[i] = null;
                }
            }
        }
    }

    private void resetTOC() {
        TurnOrderCard toc = controller.getCurrentBoard().getTurnOrderCard();
        synchronized (toc) {
            for (int i = 0; i < toc.getOrder().size(); i++) {
                TOC[i] = toc.getOrder().get(i).getTotem();
            }
        }
    }

    public void initializeTOC() {
        TurnOrderCard toc = controller.getCurrentBoard().getTurnOrderCard();
        Totem[] newTOC = new Totem[toc.getOrder().size()];
        synchronized (toc) {
            for (int i = 0; i < newTOC.length; i++) {
                newTOC[i] = toc.getOrder().get(i).getTotem();
            }
            TOC = newTOC;
        }
    }

    //show managers
    @Override
    public void showEndGameSuccessfully(String winner, List<PlayerScore> leaderboard) {
        if(leaderboard == null || leaderboard.isEmpty()){
            return;
        }
        int maxLenName = 0;
        int maxLenPoints = 0;
        int maxLenFood = 0;
        for (PlayerScore ps : leaderboard) {
            if(ps.username().length() > maxLenName){
                maxLenName = ps.username().length();
            }
            int ppLen = Integer.toString(ps.points()).length();
            if(ppLen > maxLenPoints){
                maxLenPoints = ppLen;
            }
            int foodLen = Integer.toString(ps.food()).length();
            if(foodLen > maxLenFood){
                maxLenFood = foodLen;
            }
        }
        AttributedStringBuilder asb = new AttributedStringBuilder();
        asb.append("The game is over, here's the LeaderBoard :\n\n", communicationsStyle);
        for(int i = 0; i < leaderboard.size(); i++){
            if(i == 0){
                asb.append(Integer.toString(1), whiteStyle).append(" ")
                        .append(leaderboard.get(i).username(), yellowStyle)
                        .append(" ".repeat(maxLenName-leaderboard.get(i).username().length()));
            }else {
                asb.append(Integer.toString(i+1), whiteStyle).append(" ")
                        .append(leaderboard.get(i).username(), whiteStyle)
                        .append(" ".repeat(maxLenName-leaderboard.get(i).username().length()));
            }

            asb.append("-> ", communicationsStyle)
                    .append("Prestige Points : ",communicationsStyle.italic())
                    .append(Integer.toString(leaderboard.get(i).points()),  whiteStyle)
                    .append(" ".repeat(maxLenPoints-Integer.toString(leaderboard.get(i).points()).length()+1))
                    .append("Food : ",communicationsStyle.italic())
                    .append(Integer.toString(leaderboard.get(i).food()),  whiteStyle)
                    .append(" \n".repeat(maxLenFood-Integer.toString(leaderboard.get(i).food()).length()+1));
        }
        printingHandler.addToPipeline(asb.toAnsi());
    }

    @Override
    public void showReturnToTOC(String playerName) {
        addInHead(controller.getPlayerByName(playerName).getTotem());
        boardPrinter(controller.getCurrentBoard(), "");
    }

    @Override
    public void showStartGame() {
        synchronized (LOCK) {
            initializeTOC();
            screenCleaner();
            turnTotem = controller.getCurrentBoard().getTurnOrderCard().getOrder().getFirst().getTotem();
            boardPrinter(controller.getCurrentBoard(), "Game Started");
        }

    }

    @Override
    public void showLeaderboardFromDB(int playerPosition, List<LeaderBoardData> updatedDB) {
        if(updatedDB == null || updatedDB.isEmpty()){
            return;
        }
        int maxLenPosition = 0;
        int maxLenName = 0;
        int maxLenScore = 0;
        int maxLenDate = 0;
        for (LeaderBoardData lbd : updatedDB) {
            int positionLen = Integer.toString(lbd.position()).length();
            if(positionLen > maxLenPosition){
                maxLenPosition = positionLen;
            }
            int scoreLen = Integer.toString(lbd.score()).length();
            if(scoreLen > maxLenScore){
                maxLenScore = scoreLen;
            }
            int nameLen = lbd.username().length();
            if(nameLen > maxLenName){
                maxLenName = nameLen;
            }
            int dateLen = lbd.date().length();
            if(dateLen > maxLenDate){
                maxLenDate = dateLen;
            }
        }
        AttributedStringBuilder asb = new AttributedStringBuilder();
        asb.append("All Time LeaderBoard of games with as many players as this game\n\n",  whiteStyle);

        for (LeaderBoardData lbd : updatedDB) {
            asb.append("#" + Integer.toString( lbd.position()),  whiteStyle)
                    .append(" ".repeat(maxLenPosition-Integer.toString(lbd.position()).length()+1))
                    .append("│")
                    .append(lbd.username(), yellowStyle)
                    .append(" ".repeat(maxLenName-lbd.username().length()+1))
                    .append("│")
                    .append(Integer.toString(lbd.score()),  whiteStyle)
                    .append(" ".repeat(maxLenScore-Integer.toString(lbd.score()).length()+1))
                    .append("│")
                    .append(lbd.date(), whiteStyle)
                    .append(" \n".repeat(maxLenDate-lbd.date().length()+1));
        }
        printingHandler.addToPipeline(asb.toAnsi());

    }

    @Override
    public void showPlayerFoodUpdate(String playerName, int food) {
        synchronized (LOCK) {
            boardPrinter(controller.getCurrentBoard(), "");
        }
    }

    @Override
    public void showPlayerPPUpdate(String playerName, int pp) {
        synchronized (LOCK) {
            boardPrinter(controller.getCurrentBoard(), "");
        }
    }

    @Override
    public void showLobbyMenu() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

//    @Override
//    public void showErrorMessage(String message) {
//        synchronized (LOCK) {
//            System.out.println("Error: " + message);
//        }
//    }

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
        removeToTOC(controller.getPlayerByName(username).getTotem());
        synchronized (LOCK) {
            StringBuilder stringBuilder = new StringBuilder();
            index++;
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
        synchronized (LOCK) {
            screenCleaner();
            AttributedStringBuilder stringBuilder = new AttributedStringBuilder();
            stringBuilder
                    .append("[LOCK]Attention: action required!\n", errStyle)
                    .append("Welcome, you are the first player to join the game,\n", msgStyle)
                    .append("please insert the number of players that will participate (between 2 and 5)", msgStyle);
                    //.append("> ", msgStyle);
            printingHandler.addToPipeline(stringBuilder.toAnsi());
            numPlayersPopUp.set(true);
        }
    }

    @Override
    public void showNextRound() {
        resetTOC();
        synchronized (LOCK) {
            screenCleaner();
            synchronized (controller.getCurrentBoard()) {
                boardPrinter(controller.getCurrentBoard(), "Next Round is started");
            }
        }
    }

    @Override
    public void showAvailableTotems(ArrayList<Totem> availableTotems) {
        synchronized (LOCK) {
            AttributedStringBuilder sb = new AttributedStringBuilder()
                    .append("These are the totems available at the moment: ", communicationsStyle);
            for (int i = 0; i < availableTotems.size(); i++) {
                sb.append(availableTotems.get(i).toString().toUpperCase(), totemStylesMap.get(availableTotems.get(i)));
                if (i == availableTotems.size() - 1) {
                    sb.append(".\n");
                    break;
                }
                sb.append(", ");
            }
            writer.print(sb.toAnsi());
            writer.flush();
        }
    }

    @Override
    public void showError(String message) {
        synchronized (LOCK) {
            AttributedStringBuilder sb = new AttributedStringBuilder().append("<:", communicationsStyle)
                    .append(message, errStyle);

            printingHandler.addToPipeline(sb.toAnsi());
        }
    }


    @Override
    public void showMessage(String message) {
        synchronized (LOCK) {
            String mex = new AttributedStringBuilder()
                    .append("<: ", communicationsStyle)
                    .append(message, msgStyle).toAnsi();

            printingHandler.addToPipeline(mex);
        }
    }


    @Override
    public void showCurrentPlayer(String username) {
        synchronized (turnLock) {
            turnTotem = controller.getPlayerByName(username).getTotem();
        }
        synchronized (LOCK) {
            boardPrinter(controller.getCurrentBoard(), "");
        }

    }


    public void startMenu() {
        synchronized (LOCK) {
            screenCleaner();
            AttributedStringBuilder startMenuSB = new AttributedStringBuilder();
            startMenuSB.append("====MESOS====\n", errStyle);
            startMenuSB.append("Command List :\n", communicationsStyle);
            menuFormatter(startMenuSB, "Set your username->", "username", "<your_username>");
            menuFormatter(startMenuSB, "Select your totem color-> ", "totem", "<color>");
            menuFormatter(startMenuSB, "View available colors-> ", "colors", "");
            menuFormatter(startMenuSB, "Leave the game-> ", "quit", "");
            startMenuSB.append("You can choose among these colors: ", communicationsStyle)
                    .append(Totem.BLACK.toString(), blackStyle).append(", ", communicationsStyle)
                    .append(Totem.BLUE.toString(), blueStyle).append(", ", communicationsStyle)
                    .append(Totem.WHITE.toString(), whiteStyle).append(", ", communicationsStyle)
                    .append(Totem.YELLOW.toString(), yellowStyle).append(", ", communicationsStyle)
                    .append(Totem.ORANGE.toString(), redStyle).append(".\n", communicationsStyle);
            startMenuSB.append("NOTE", yellowStyle)
                    .append(": Both username and totem must be set correctly to start the game.", communicationsStyle);

            printingHandler.addToPipeline(startMenuSB.toAnsi());
        }

    }

    private void menuFormatter(AttributedStringBuilder asb, String pre_cmd, String cmd, String in_cmd) {
        int length = 30;
        int delta = length - pre_cmd.length();
        asb.append("* ", whiteStyle)
                .append(pre_cmd + " ".repeat(delta), communicationsStyle)
                .append(cmd, yellowStyle);

        if (in_cmd.isEmpty()) {
            asb.append("\n");
            return;
        }

        asb.append(": ", communicationsStyle)
                .append(in_cmd, communicationsStyle.italic())
                .append("\n");
    }

    public void screenCleaner() {
        if (terminal != null) {
            terminal.puts(InfoCmp.Capability.clear_screen);
            terminal.flush();
        }
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

        String[] provv = printer.printTOC(TOC);
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
        result[0] = result[0] + new AttributedStringBuilder().append(" ERA: ", communicationsStyle)
                .append(Integer.toString(controller.getCurrentBoard().getEra()), yellowStyle).toAnsi();
        synchronized (turnLock) {
            if (turnTotem == null) {
                result[1] = result[1] + " " + new AttributedStringBuilder().append("TURN: ", communicationsStyle)
                        .append(" Waiting for turn...", communicationsStyle.italic()).toAnsi();
            } else {
                result[1] = result[1] + " " + new AttributedStringBuilder().append("TURN: ", communicationsStyle)
                        .append(turnTotem.toString(), totemStylesMap.get(turnTotem)).toAnsi();
            }
        }
        result[2] += new AttributedStringBuilder().append(" * ", whiteStyle)
                .append("To move your totem-> ", communicationsStyle)
                .append("move", yellowStyle)
                .append(": <index>", communicationsStyle.italic()).toAnsi();
        result[3] += new AttributedStringBuilder().append(" * ", whiteStyle)
                .append("To pick a card-> ", communicationsStyle)
                .append("pick", yellowStyle)
                .append(": <up:1/down:0> <building:1/card:0> <index>", communicationsStyle.italic()).toAnsi();
        result[4] += new AttributedStringBuilder().append(" * ", whiteStyle)
                .append("To skip when allowed-> ", communicationsStyle)
                .append("skip", yellowStyle).toAnsi();
        result[5] += new AttributedStringBuilder().append(" * ", whiteStyle)
                .append("To see the players cards-> ", communicationsStyle)
                .append("cards", yellowStyle)
                .append(": <player's totem> ", communicationsStyle.italic()).toAnsi();
        result[6] += new AttributedStringBuilder().append(" * ", whiteStyle)
                .append("If you need help with the game-> ", communicationsStyle)
                .append("help", yellowStyle).toAnsi();
        return result;
    }

    public void boardPrinter(Board board, String update) {
        ArrayList<String> printedBoard = new ArrayList<String>();
        String[] players = printPlayers(board.getPlayers());
        printedBoard.addAll(Arrays.asList(players));

        String[] buildingUpperRow = printBuildingRow(board.getUpperBuildingRow());
        String[] cardsUpperRow = printCardsRow(board.getUpperCardRow());
        for (int i = 0; i < buildingUpperRow.length; i++) {
            cardsUpperRow[i] = cardsUpperRow[i] + " " + buildingUpperRow[i];
        }
        printedBoard.addAll(Arrays.asList(cardsUpperRow));
        String[] path = printPath(board.getTurnOrderCard(), board.getPath());
        printedBoard.addAll(Arrays.asList(path));
        String[] cardsLowerRow = printCardsRow(board.getLowerCardsRow());
        String[] buildingLowerRow = printBuildingRow(board.getLowerBuildingRow());
        if (buildingLowerRow.length!=0) {
            for (int i = 0; i < cardsLowerRow.length; i++) {
                cardsLowerRow[i] = cardsLowerRow[i] + " " + buildingLowerRow[i];
            }
        }

        printedBoard.addAll(Arrays.asList(cardsLowerRow));
        if (!update.isEmpty()) {
            printedBoard.add(new AttributedStringBuilder().append("<: "+ update, communicationsStyle).toAnsi());
        }

        AttributedStringBuilder finalOutput = new AttributedStringBuilder();
        finalOutput.append("[CLEAR]");
        for (String s : printedBoard) {
            finalOutput.append(s).append("\n");
        }

        printingHandler.addToPipeline(finalOutput.toAnsi());
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

    public String[] printPlayers(ArrayList<Player> players) {

        String[] result = new String[3];
        result = printer.initialize(result);
        result[0] = result[0] + "│";
        result[1] = result[1] + "│";
        result[2] = result[2] + "│";
        int maxL = 0;
        for (Player p : players) {
            String name = new AttributedStringBuilder().append(p.getName(), totemStylesMap.get(p.getTotem())).toAnsi();
            String food = new AttributedStringBuilder().append("Food: ", communicationsStyle).append(Integer.toString(p.getFood())).toAnsi();
            String pp = new AttributedStringBuilder().append("PPs: ", communicationsStyle).append(Integer.toString(p.getPrestigePoints())).toAnsi();

            maxL = Math.max(Integer.toString(p.getFood()).length() + 6, Integer.toString(p.getPrestigePoints()).length() + 5);
            maxL = Math.max(maxL, p.getName().length());
            maxL++;

            String[] tmp = new String[3];
            tmp[0] = name;
            tmp[1] = food;
            tmp[2] = pp;

            int delta = maxL - p.getName().length();
            tmp[0] += " ".repeat(delta);
            delta = maxL - (Integer.toString(p.getFood()).length() + 6);
            tmp[1] += " ".repeat(delta);
            delta = maxL - (Integer.toString(p.getPrestigePoints()).length() + 5);
            tmp[2] += " ".repeat(delta);
            for (int i = 0; i < tmp.length; i++) {
                result[i] += tmp[i] + "│";
            }
        }
        return result;
    }


    public void showCardsCommandTui(String totem){
        Board currentBoard = controller.getCurrentBoard();
        if(currentBoard == null){
            showError("Command not available at the moment");
            return;
        }

        Totem cardsOwner;
        if(totem.isEmpty()){
            cardsOwner = controller.getLocalTotem();
        }else{
            try {
                cardsOwner = Totem.valueOf(totem.toUpperCase());
            } catch (IllegalArgumentException e) {
                showError("Invalid totem");
                return;
            }

            boolean isPresent = false;
            try {
                for (Player p : currentBoard.getPlayers()){
                    if(p.getTotem().equals(cardsOwner)){
                        isPresent = true;
                        break;
                    }
                }
            } catch (NullPointerException e) {
                showError("Command not available at the moment");
                return;
            }
            if(!isPresent){
                showError("Player not in the game");
            }
        }

        Player p = null;
        for(Player player : controller.getCurrentBoard().getPlayers()){
            if(player.getTotem().equals(cardsOwner)){
                p = player;
                break;
            }
        }
        if(p == null){
            showError("Player not in the game");
            return;
        }
        this.cardsPopUp.set(true);
        printPlayerCards(p);

    }

    public void printPlayerCards(Player player){
        if(player.getTribeCard().isEmpty()){
            showMessage("The player has no cards");
            return;
        }
        AttributedStringBuilder cards = new AttributedStringBuilder();
        cards.append("[LOCK]");
        Printer printer = new Printer();

        ArrayList<Character> tribeCards = player.getTribeCard();
        int totalCards = tribeCards.size();

        for (int i = 0; i < totalCards; i += 7) {

            int end = Math.min(i + 7, totalCards);
            List<Character> currentBlock = tribeCards.subList(i, end);
            int cardHeight = 7;
            for (int r = 0; r < cardHeight; r++) {
                for (Card c : currentBlock) {
                    String[] cardRepresentation = c.print(printer);
                    cards.append(cardRepresentation[r]);
                    cards.append(" ");
                }
                cards.append("\n");
            }
            //cards.append("\n");
        }
        ArrayList<Building> buildings = player.getBuilding();
        totalCards = player.getBuilding().size();
        for (int i = 0; i < totalCards; i += 7) {

            int end = Math.min(i + 7, totalCards);
            List<Building> currentBlock = buildings.subList(i, end);
            int cardHeight = 7;
            for (int r = 0; r < cardHeight; r++) {
                for (Card c : currentBlock) {
                    String[] cardRepresentation = c.print(printer);
                    cards.append(cardRepresentation[r]);
                    cards.append(" ");
                }
                cards.append("\n");
            }
            //cards.append("\n");
        }

        cards.append("* Press any key to go back to the board");
        printingHandler.addToPipeline(cards.toString());
    }



    @Override
    public synchronized void close() {
        if (!isRunning.get()) {
            return;
        }
        isRunning.set(false);

        if(terminal!=null){
            try {
                terminal.close();
            } catch (IOException e) {
                System.out.println("Error while closing the terminal");
            }
        }
    }
}
