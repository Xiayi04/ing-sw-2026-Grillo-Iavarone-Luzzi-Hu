package it.polimi.ingsw.Network;

import it.polimi.ingsw.UI.GUI.MainApp;
import it.polimi.ingsw.UI.TUI;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import javafx.application.Application;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientMain {

    private static final AtomicBoolean isTerminated = new AtomicBoolean(false);
    private static ClientController clientController;
    private static Terminal terminal;
    private static GraphicInterface userInterface;


    public static void main(String[] args){
        try {
            terminal = TerminalBuilder.builder().system(true).build();
        } catch (IOException e) {
            System.out.println("Failed to create terminal");
            ClientMain.terminateClient();
            return;
        }
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        String ip = reader.readLine("Insert here the server IP please or press ENTER to use localhost :");
        ip = ip.trim();

        if(ip.isEmpty()){
            ip = "localhost";
        }
        int UI = -1;
        while (!isTerminated.get()) {
            try{
                String input = reader.readLine("Select the preferred User Interface that you want to use [0:Textual/1:Graphic]:");
                input = input.trim();
                if(input.equals("0") || input.equals("1")){
                    UI = Integer.parseInt(input);
                    break;
                }
                terminal.writer().println("Invalid input. Please try again.");
                terminal.writer().flush();
            }catch (UserInterruptException | EndOfFileException e){
                System.exit(0);
            }
        }

        clientController = new ClientController(ip);

        if(UI == 1){
            MainApp.setController(clientController);
            Application.launch(MainApp.class);
        } else {
            userInterface = new TUI(terminal ,clientController);
        }
    }

    public synchronized static void terminateClient(){
        if(isTerminated.get()){
            return;
        }
        isTerminated.set(true);
        clientController.close();
        System.out.println("Thank you for playing Mesos, we hope to see you again! :)");
        System.exit(0);
    }

}
