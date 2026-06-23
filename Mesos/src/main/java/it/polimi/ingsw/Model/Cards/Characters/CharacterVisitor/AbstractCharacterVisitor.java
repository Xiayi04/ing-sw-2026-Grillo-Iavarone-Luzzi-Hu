package it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Model.Cards.Characters.*;
import it.polimi.ingsw.Model.Game.Player;

public abstract class AbstractCharacterVisitor implements CharacterVisitor{


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
    public boolean visit(Inventor inventor, Player player) {
        return false;
    }
    public boolean visit(Builder builder, Player player) {
        return false;
    }
    public boolean visit(Shaman shaman, Player player) {
        return false;
    }
    public boolean visit(Picker picker, Player player) {
        return false;
    }
    public boolean visit(Hunter hunter, Player player) {
        return false;
    }
    public boolean visit(Painter painter, Player player) {
        return false;
    }
    @Override
    public String visit(Inventor inventor) {
        return "";
    }
}
