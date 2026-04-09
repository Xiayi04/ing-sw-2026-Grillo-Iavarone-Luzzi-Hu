package it.polimi.ingsw.Cards.Characters;
import it.polimi.ingsw.Cards.CardType;
import javax.swing.*;

public class Inventor extends Character{
    private final String inventorIcon;

    //getinventoricon di CharacterDTO restituisce una string, uno dei due va cambiato
    public Inventor(int era, CardType cardType, int numPlayers, CharacterType characterType, String icon) {
        super(era, cardType, numPlayers, characterType);

        this.inventorIcon = icon;
    }

    public String getInventorIcon() {
        return inventorIcon;
    }
}
