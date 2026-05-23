package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Game.Player;
import javafx.scene.layout.VBox;

public interface GuiVisitor {
    VBox visit(Inventor inventor);
    VBox visit(Shaman shaman);
    VBox visit(Painter painter);
    VBox visit(Hunter hunter);
    VBox visit(Picker picker);
    VBox visit(Builder builder);
}
