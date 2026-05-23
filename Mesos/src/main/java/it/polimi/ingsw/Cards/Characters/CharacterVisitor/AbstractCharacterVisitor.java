package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Game.Player;
import javafx.scene.layout.VBox;

public abstract class AbstractCharacterVisitor implements CharacterVisitor{
    public void visit(Inventor inventor, Player player){}
    public void visit(Builder builder, Player player){}
    public void visit(Shaman shaman, Player player){}
    public void visit(Picker picker, Player player){}
    public void visit(Hunter hunter, Player player){}
    public void visit(Painter painter, Player player){}

    @Override
    public String visit(Builder builder) {
        return "";
    }
    @Override
    public String visit(Shaman shaman) {
        return "";
    }
    @Override
    public String visit(Picker picker) {
        return "";
    }
    @Override
    public String visit(Hunter hunter) {
        return "";
    }
    @Override
    public String visit(Painter painter) {
        return "";
    }
    @Override
    public String visit(Inventor inventor) {
        return "";
    }
}
