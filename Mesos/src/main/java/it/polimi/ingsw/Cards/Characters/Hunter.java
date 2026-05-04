package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Game.Player;

import javax.xml.stream.events.Characters;

public class Hunter extends Character implements CharacterInterface {
    private final boolean symbol;

    public Hunter(int era, String cardType, int numPlayers,String characterType, boolean symbol) {
        super(era, cardType, numPlayers, characterType);

        this.symbol = symbol;
    }
    //metodi getter
    public boolean getSymbol() {return symbol;}

    public void printCard(){
        super.printCard();
        System.out.println("simbolo:"+symbol);
    }

    @Override
    public void addCard(CharacterVisitor visitor, Player player){
        visitor.visit(this, player);
    }
}
