package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.CardType;
import javax.xml.stream.events.Characters;

public class Builder extends Character{
    private final Integer builderDiscount;
    private final Integer PP;

    public Builder(int era, CardType cardType, int numPlayers, CharacterType characterType, Integer discount, Integer PP) {
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
}
