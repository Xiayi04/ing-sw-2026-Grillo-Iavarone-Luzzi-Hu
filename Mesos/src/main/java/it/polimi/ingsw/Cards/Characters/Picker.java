package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.CardType;

public class Picker extends Character{
    private final int discount = -3;

    public Picker(int era, CardType cardType, int numPlayers, CharacterType characterType) {
        super(era, cardType, numPlayers, characterType);
    }

    public int getDiscount() {
        return discount;
    }
}
