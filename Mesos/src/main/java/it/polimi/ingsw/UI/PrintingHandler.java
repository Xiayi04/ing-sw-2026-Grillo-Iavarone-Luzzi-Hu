package it.polimi.ingsw.UI;

import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrintingHandler implements Runnable{
    private final BlockingQueue<String> pipeline =  new LinkedBlockingQueue<>();
    private final ExecutorService printerExec = Executors.newSingleThreadExecutor();
    private final Terminal terminal;
    private final PrintWriter writer;
    private final LineReader reader;
    private final AtomicBoolean lock = new AtomicBoolean(false);

    public PrintingHandler(Terminal terminal,PrintWriter writer, LineReader reader) {
        this.terminal = terminal;
        this.writer = writer;
        this.reader = reader;
    }

    public void addToPipeline(String input){
        pipeline.add(input);
    }

    @Override
    public void run() {
        printerExec.submit(()->{
            while(true){

                try {
                    synchronized (lock){
                        while (lock.get()) {
                            lock.wait();
                        }
                    }

                    String next = pipeline.take();

                    if (next.contains("[CLEAR]")){
                        next = next.replace("[CLEAR]","");
                        screenCleaner();
                    }

                    if(next.contains("[LOCK]")){
                        next = next.replace("[LOCK]","");
                        screenCleaner();
                        lock.set(true);
                    }

                    if(next.endsWith("\n")){
                        int lastNewline = next.lastIndexOf("\n");
                        if (lastNewline != -1) {
                            next = new StringBuilder().append(next).deleteCharAt(lastNewline).toString();
                        }
                    }

                    if (reader != null && reader.isReading()) {
                        reader.printAbove(next);
                    } else {
                        writer.println(next);
                        writer.flush();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void screenCleaner() {
        if (terminal != null) {
            terminal.puts(org.jline.utils.InfoCmp.Capability.clear_screen);
            terminal.flush();
        }
    }

    public void lockPipeline(){
        lock.set(true);
    }

    public void unlockPipeline(){
        synchronized (lock){
            lock.set(false);
            lock.notifyAll();
        }
    }

    public void close(){
        try {
            printerExec.shutdown();
        } catch (Exception ignored) {}
    }


}
