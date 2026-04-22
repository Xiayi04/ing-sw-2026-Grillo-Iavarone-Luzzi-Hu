package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Game.Player;

import java.util.ArrayList;

public class Shaman extends Character implements CharacterInterface {
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
    @Override
    public void addCard(CharacterVisitor visitor, Player player){
        visitor.visit(this, player);
    }

    /**
     * @param players is an ArrayList which contains al the players' informations
     * @return  the highest number of stars among the players
     */


}
