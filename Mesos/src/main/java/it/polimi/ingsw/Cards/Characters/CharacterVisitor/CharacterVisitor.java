package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Game.Player;

public interface CharacterVisitor {
    void visit(Inventor inventor, Player player);
    void visit(Builder builder, Player player);
    void visit(Shaman shaman, Player player);
    void visit(Picker picker, Player player);
    void visit(Hunter hunter, Player player);
    void visit(Painter painter, Player player);
    String visit(Inventor inventor);
}
