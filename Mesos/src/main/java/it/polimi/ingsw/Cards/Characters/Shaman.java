package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.CardType;

import java.util.ArrayList;

public class Shaman extends Character{
    private final Integer shamanStars;

    public Shaman(int era, CardType cardType, int numPlayers, CharacterType characterType, Integer shamanStars) {
        super(era, cardType, numPlayers, characterType);
        this.shamanStars = shamanStars;
    }
    //metodo getter
    public Integer getShamanStars() { return this.shamanStars; }

    public void printCard(){
        super.printCard();
        System.out.println("stelle:"+shamanStars);
    }

    /**
     * @param players is an ArrayList which contains al the players' informations
     * @return  the highest number of stars among the players
     */


}
