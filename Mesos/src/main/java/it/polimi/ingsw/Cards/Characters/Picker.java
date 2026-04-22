package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Game.Player;

public class Picker extends Character implements CharacterInterface {
    private final int discount = -3;

    public Picker(int era, CardType cardType, int numPlayers, CharacterType characterType) {
        super(era, cardType, numPlayers, characterType);
    }

    public int getDiscount() {
        return discount;
    }

    public void printCard(){
        super.printCard();
        System.out.println("sconto:"+discount);
    }
    //accepter
    @Override
    public void addCard(CharacterVisitor visitor, Player player){
        visitor.visit(this, player);
    }
}
