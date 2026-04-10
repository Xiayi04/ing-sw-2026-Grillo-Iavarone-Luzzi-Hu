package it.polimi.ingsw.Cards.Characters;
import it.polimi.ingsw.Cards.CardType;

public class Painter extends Character{
    public Painter(int era, CardType cardType, int numPlayers, CharacterType characterType){
        super( era, cardType, numPlayers, characterType);
    }

    public void printCard(){
        super.printCard();
    }
}
