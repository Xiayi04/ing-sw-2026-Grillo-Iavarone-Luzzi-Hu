package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Game.Player;

import static java.lang.Math.abs;

public class AddAndCountCharacter extends AbstractCharacterVisitor {

    public void visit(Inventor inventor, Player player){

        new Thread(()->{
            synchronized (player.getTribeCard()) {
                player.setInventorCounter((player.getInventorCounter()) + 1);
                player.getTribeCard().add(inventor);
                player.getTribeCard().notifyAll();
            }
        }).start();
    }

    public void visit(Shaman shaman, Player player){
        new Thread(()->{
            synchronized (player.getTribeCard()){
                player.setShamanCounter((player.getShamanCounter())+1);
                player.getTribeCard().add(shaman);
            }
        }).start();

    }

    public void visit(Picker picker, Player player){
        new Thread(()->{
            synchronized (player.getTribeCard()){
                player.setPickerCounter((player.getPickerCounter()) + 1);
                player.getTribeCard().add(picker);
            }
        }).start();
    }

    public void visit(Hunter hunter, Player player){
        new Thread(()->{
            synchronized (player.getTribeCard()){
                player.setHunterCounter((player.getHunterCounter()) + 1);
                player.getTribeCard().add(hunter);
                if(hunter.getSymbol()){
                    player.modifyFood(abs(player.getHunterCounter()));
                }
            }
        }).start();
    }

    public void visit(Builder builder, Player player){
        new Thread(()->{
            synchronized (player.getTribeCard()){
                player.setBuilderCounter((player.getBuilderCounter() + 1));
                player.getTribeCard().add(builder);
            }
        }).start();
    }

    public void visit(Painter painter, Player player){
        new Thread(()->{
            synchronized (player.getTribeCard()){
                player.setPainterCounter((player.getPainterCounter()) + 1);
                player.getTribeCard().add(painter);
            }
        }).start();
    }



}
