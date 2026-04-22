package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Game.Player;

public abstract class InventorIconAbstractCounter implements CharacterVisitor{
    public void visit(Inventor inventor, Player player){}
    public void visit(Builder builder, Player player){}
    public void visit(Shaman shaman, Player player){}
    public void visit(Picker picker, Player player){}
    public void visit(Hunter hunter, Player player){}
    public void visit(Painter painter, Player player){}
    public String visit(Inventor inventor){return inventor.getInventorIcon();}
}
