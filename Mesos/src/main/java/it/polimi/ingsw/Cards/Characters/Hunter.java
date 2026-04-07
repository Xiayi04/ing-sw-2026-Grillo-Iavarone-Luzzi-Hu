package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.CardType;
import javax.xml.stream.events.Characters;

public class Hunter extends Character {
    private final boolean symbol;

    public Hunter(int era, CardType cardType, int numPlayers,CharacterType characterType, boolean symbol) {
        super(era, cardType, numPlayers, characterType);

        this.symbol = symbol;
    }
    //metodi getter
    public boolean getSymbol() {return symbol;}
}
