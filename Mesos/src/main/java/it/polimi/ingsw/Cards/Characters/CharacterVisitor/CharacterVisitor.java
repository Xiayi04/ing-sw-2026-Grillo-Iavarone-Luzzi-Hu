package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Game.Player;

public interface CharacterVisitor {
//    void visit(Inventor inventor, Player player);
//    void visit(Builder builder, Player player);
//    void visit(Shaman shaman, Player player);
//    void visit(Picker picker, Player player);
//    void visit(Hunter hunter, Player player);
//    void visit(Painter painter, Player player);
    String visit(Inventor inventor);
    String visit(Builder builder);
    String visit(Shaman shaman);
    String visit(Picker picker);
    String visit(Hunter hunter);
    String visit(Painter painter);
    boolean visit(Inventor inventor, Player player);
    boolean visit(Builder builder, Player player);
    boolean visit(Shaman shaman, Player player);
    boolean visit(Picker picker, Player player);
    boolean visit(Hunter hunter, Player player);
    boolean visit(Painter painter, Player player);
}
