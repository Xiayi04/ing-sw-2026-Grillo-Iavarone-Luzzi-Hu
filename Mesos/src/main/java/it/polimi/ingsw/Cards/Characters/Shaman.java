package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.CardType;

public class Shaman extends Character{
    private final Integer shamanStars;

    public Shaman(int era, CardType cardType, int numPlayers, CharacterType characterType, Integer shamanStars) {
        super(era, cardType, numPlayers, characterType);
        this.shamanStars = shamanStars;
    }
    //metodo getter
    public Integer getShamanStars() { return this.shamanStars; }


}
