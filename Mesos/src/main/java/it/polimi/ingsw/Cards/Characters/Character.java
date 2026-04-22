package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.InventorInterface;

public abstract class Character extends Card implements CharacterInterface, InventorInterface {
    private final int numPlayers;
    private final CharacterType characterType;

    public Character(int era, CardType cardType, int numPlayers, CharacterType characterType) {
        super( era, cardType);
        this.numPlayers = numPlayers;
        this.characterType = characterType;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public CharacterType getCharacterType() {
        return this.characterType;
    }

    public void printCard(){
        super.printCard();
        System.out.println("numero giocatori:"+numPlayers);
        System.out.println(("tipo di personaggio:"+characterType.toString()));
    }


}
