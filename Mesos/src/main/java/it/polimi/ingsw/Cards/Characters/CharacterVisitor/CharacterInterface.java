package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Game.Player;
import javafx.scene.layout.VBox;

public interface CharacterInterface {
    public boolean addCard(CharacterVisitor visitor, Player player);
    public VBox findBox(GuiVisitor visitor);
}
