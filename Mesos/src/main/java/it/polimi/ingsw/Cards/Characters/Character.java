package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.CardType;

public abstract class Character extends Card {
    private final int NumPlayers;
    private final CharacterType characterType;

    public Character(int era, CardType cardType, int numPlayers, CharacterType characterType) {
        super( era, cardType);
        this.NumPlayers = numPlayers;
        this.characterType = characterType;
    }

    public int getNumPlayers() {
        return NumPlayers;
    }
    public CharacterType getCharacterType() {
        return this.characterType;
    }

}
