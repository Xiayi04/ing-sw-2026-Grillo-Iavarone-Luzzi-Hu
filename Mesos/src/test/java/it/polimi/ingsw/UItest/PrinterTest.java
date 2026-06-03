package it.polimi.ingsw.UItest;

import it.polimi.ingsw.Buildings.BonusFood;
import it.polimi.ingsw.Buildings.BonusPPBuilding;
import it.polimi.ingsw.Buildings.BonusStarBuilding;
import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Characters.Hunter;
import it.polimi.ingsw.Cards.Characters.Inventor;
import it.polimi.ingsw.Cards.Characters.Shaman;
import it.polimi.ingsw.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Game.*;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.GraphicInterface;
import it.polimi.ingsw.UI.Printer;
import it.polimi.ingsw.UI.TUI;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

public class PrinterTest {
    private final Hunter hunter = new Hunter(1, "Character", 1, "hunter", true );
    private final Inventor inventor = new Inventor(1,"Character", 2,"Inventor", "tree");
    private final Shaman shaman = new Shaman(1, "Character", 1, "shaman", 3 );
    @Test
    public void testPrinter() {
        Printer printer = new Printer();
        String[] p0 =printer.print(hunter);
        String[] p1 =printer.print(inventor);
        String[] p2 =printer.print(shaman);
        for(int i=0;i<p0.length;i++){
            p0[i] = p0[i] + p1[i]+ p2[i];
        }

        for(String s: p0){
            System.out.println(s);
        }
        ArrayList<OfferCard> path = new ArrayList<>();
        int numPlayers = 5;
        path.add(new OfferCard(2, 0, 1, false));
        path.add(new OfferCard(3, 1, 0, false));
        path.add(new OfferCard(5, 1, 1, false));
        path.add(new OfferCard(6, 2, 0, false));
        if (numPlayers >= 3) {
            path.add(new OfferCard(5, 0, 2, false));
            if (numPlayers >= 4)
                path.add(new OfferCard(7, 2, 1, false));
            if (numPlayers >= 5)
                path.add(new OfferCard(1, 0, 0, true));
        }
        Collections.sort(path, (a, b) -> a.getID() - b.getID());
        ArrayList<String[]> bluePrint = new ArrayList<>();
        for(int i=0;i<path.size();i++){
            bluePrint.add(printer.print(path.get(i)));
        }
        String[] gay = new String[bluePrint.get(0).length];
        gay = printer.initialize(gay);
        for(int i=0;i<bluePrint.getFirst().length;i++){
            for(String[] s : bluePrint){
                gay[i] = gay[i] + s[i];
            }
        }
        for(int i=0;i< gay.length;i++){
            System.out.println(gay[i]);
        }
    }

    @Test
    public void testPrinter1() {
        Printer printer = new Printer();
        TurnOrderCard card = new TurnOrderCard(5);
        Player p0 = new Player("aldo", Totem.BLACK,0, null);
        Player p1 = new Player("gloria", Totem.WHITE,1, null);
        Player p2 = new Player("mattia", Totem.YELLOW,2, null);
        Player p3 = new Player("giuseppe", Totem.BLUE,3, null);
        Player p4 = new Player("xiayi",Totem.ORANGE,4, null);

        card.getOrder().add(p0);
        card.getOrder().add(p1);
        card.getOrder().add(p2);
        card.getOrder().add(p3);
        card.getOrder().add(p4);

        Board board = new Board();
        board.getTurnOrderCard();
        board.initializeBoard(5);

        //TUI tui = new TUI(new ClientController());
        //tui.boardPrinter(board);

    }

    @Test
    public void testPrinter3(){
        Printer printer = new Printer();
        Deck deck = new Deck();
        ArrayList<Building>  mazzo = new ArrayList<>();
        //mazzo = deck.buildingPerPlayers(5);

        String[] blueprint = new String[7];
        blueprint = printer.initialize(blueprint);


        int j=0;
        for(int i=0; i<mazzo.size();i++,j++){
            String[] provv = new  String[7];
            provv = printer.initialize(provv);
            provv = mazzo.get(i).print(printer);

            for(int k=0;k<provv.length;k++){
                blueprint[k] = blueprint[k] + provv[k];
            }

            if(j==4){
                for(String s: blueprint){
                    System.out.println(s);
                }
                blueprint = new String[7];
                blueprint = printer.initialize(blueprint);
                j=0;
            }

        }
        for(String s: blueprint){
            System.out.println(s);
        }

    }

    @Test
    public void testPrinter2() {
        Printer printer = new Printer();
        String[] p0 =printer.print(new HuntingEvent(1,"Event", "hunting_event", 2));
        for(String s: p0){
            System.out.println(s);
        }
        p0 = printer.print(new ShamanicEvent(1,"event", "shamanic_event",-3, +3));
        for(String s: p0){
            System.out.println(s);
        }
        p0 = printer.print(new PaintingEvent(1, "event", "painting_event", 2, 2,2));
        for(String s: p0){
            System.out.println(s);
        }
        p0 = printer.print(new SustenanceEvent(1,"event", "sustenance_event", 2));
        for(String s: p0){
            System.out.println(s);
        }
        p0 = printer.print(new BonusStarBuilding(1,5, 3));
        for(String s: p0){
            System.out.println(s);
        }
    }
}
