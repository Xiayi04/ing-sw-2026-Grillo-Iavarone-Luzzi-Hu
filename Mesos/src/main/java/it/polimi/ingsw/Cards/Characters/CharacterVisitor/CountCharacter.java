package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Game.Player;

public class CountCharacter implements CharacterVisitor {

    public void visit(Inventor inventor, Player player){
        player.setInventorCounter((player.getInventorCounter())+1);
        player.getTribeCard().add(inventor);
    }

    public void visit(Shaman shaman, Player player){
        player.setShamanCounter((player.getShamanCounter())+1);
        player.getTribeCard().add(shaman);
    }

    public void visit(Picker picker, Player player){
        player.setPickerCounter((player.getPickerCounter())+1);
        player.getTribeCard().add(picker);
    }

    public void visit(Hunter hunter, Player player){
        player.setHunterCounter((player.getHunterCounter())+1);
        player.getTribeCard().add(hunter);
    }

    public void visit(Builder builder, Player player){
        player.setBuilderCounter((player.getBuilderCounter()+1));
        player.getTribeCard().add(builder);
    }

    public void visit(Painter painter, Player player){
        player.setPainterCounter((player.getPainterCounter())+1);
        player.getTribeCard().add(painter);
    }
}
