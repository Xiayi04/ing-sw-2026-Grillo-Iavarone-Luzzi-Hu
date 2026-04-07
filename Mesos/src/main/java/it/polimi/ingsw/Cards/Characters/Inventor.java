package it.polimi.ingsw.Cards.Characters;
import it.polimi.ingsw.Cards.CardType;
import javax.swing.*;

public class Inventor extends Character{
    private final InventorIcon inventorIcon;

    public Inventor(int era, CardType cardType, int numPlayers, CharacterType characterType, InventorIcon icon) {
        super(era, cardType, numPlayers, characterType);

        this.inventorIcon = icon;
    }

    public String getInventorIcon() {
        return inventorIcon.toString();
    }
}
