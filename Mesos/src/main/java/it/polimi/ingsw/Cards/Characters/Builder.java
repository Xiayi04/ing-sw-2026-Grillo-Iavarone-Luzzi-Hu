package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Game.Player;

import javax.xml.stream.events.Characters;

public class Builder extends Character implements CharacterInterface {
    private final Integer builderDiscount;
    private final Integer PP;

    public Builder(int era, String cardType, int numPlayers, String characterType, Integer discount, Integer PP) {
        super(era, cardType, numPlayers, characterType);

        this.builderDiscount = discount;
        this.PP = PP;
    }
// metodi getter
    public Integer getBuilderDiscount() { return builderDiscount;}
    public Integer getPP() { return PP; }

    public void printCard(){
        super.printCard();
        System.out.println("sconto:"+builderDiscount);
        System.out.println("pp:"+PP);
    }
    //accepter
    @Override
    public void addCard(CharacterVisitor visitor, Player player){
        visitor.visit(this, player);
    }
}
