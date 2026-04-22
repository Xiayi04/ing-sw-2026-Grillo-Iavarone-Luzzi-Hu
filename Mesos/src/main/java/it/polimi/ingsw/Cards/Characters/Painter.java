package it.polimi.ingsw.Cards.Characters;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Game.Player;

public class Painter extends Character implements CharacterInterface {
    public Painter(int era, CardType cardType, int numPlayers, CharacterType characterType){
        super( era, cardType, numPlayers, characterType);
    }

    public void printCard(){
        super.printCard();
    }
    //accepter
    @Override
    public void addCard(CharacterVisitor visitor, Player player){
        visitor.visit(this, player);
    }
}
