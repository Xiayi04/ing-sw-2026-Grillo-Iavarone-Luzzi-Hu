package it.polimi.ingsw.Visitors.CharacterVisitor;

import it.polimi.ingsw.Model.Game.Player;
import javafx.scene.layout.VBox;

public interface CharacterInterface {
    public boolean addCard(CharacterVisitor visitor, Player player);
    public VBox findBox(GuiVisitor visitor);
}
