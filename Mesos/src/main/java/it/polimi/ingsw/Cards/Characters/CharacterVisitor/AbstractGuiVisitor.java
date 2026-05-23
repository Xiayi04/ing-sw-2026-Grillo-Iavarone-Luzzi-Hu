package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Cards.Characters.*;
import javafx.scene.layout.VBox;

public abstract class AbstractGuiVisitor implements GuiVisitor {
    public VBox visit(Inventor inventor){return null;}
    public VBox visit(Shaman shaman){return null;}
    public VBox visit(Painter painter){return null;}
    public VBox visit(Hunter hunter){return null;}
    public VBox visit(Picker picker){return null;}
    public VBox visit(Builder builder){return null;}
}
