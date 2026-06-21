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
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientMain {

    private static final AtomicBoolean isTerminated = new AtomicBoolean(false);
    private static ClientController clientController;
    private static Terminal terminal;
    private static TUI tui;
    static AttributedStyle yellowStyle = AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW);
    static AttributedStyle yellowBoldStyle = AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).bold();

    public static void main(String[] args){
        try {
            terminal = TerminalBuilder.builder().system(true).jansi(true).type("windows-256color").build();
        } catch (IOException e) {
            System.out.println("Failed to create terminal");
            ClientMain.terminateClient();
            return;
        }
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
        PrintWriter writer = terminal.writer();
        writer.print(new AttributedStringBuilder()
                .append("Insert here the server IP please or press ", yellowStyle)
                .append("ENTER ",yellowBoldStyle)
                .append("to use localhost :\n" +
                        "> ", yellowStyle).toAnsi());
        writer.flush();
        String ip = reader.readLine();
        ip = ip.trim();

        if(ip.isEmpty()){
            ip = "localhost";
        }
        if (terminal != null) {
            terminal.puts(InfoCmp.Capability.clear_screen);
            terminal.flush();
        }

        int UI = -1;
        while (!isTerminated.get()) {
            try{
                writer.print(new AttributedStringBuilder()
                        .append("Select the preferred User Interface that you want to use [0:Textual/1:Graphic]:\n" +
                                "> ", yellowStyle).toAnsi());
                writer.flush();
                String input = reader.readLine();
                input = input.trim();
                if(input.equals("0") || input.equals("1")){
                    UI = Integer.parseInt(input);
                    break;
                }
                writer.println(new AttributedStringBuilder()
                        .append("Invalid input. Please try again.", yellowStyle).toAnsi());
                writer.flush();
            }catch (UserInterruptException | EndOfFileException e){
                System.exit(0);
            }
        }

        if (terminal != null) {
            terminal.puts(InfoCmp.Capability.clear_screen);
            terminal.flush();
        }
        clientController = new ClientController(ip);

        if(UI == 1){
            MainApp.setController(clientController);
            Application.launch(MainApp.class);
        } else {
            tui = new TUI(terminal , reader, clientController);
            tui.run();
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
