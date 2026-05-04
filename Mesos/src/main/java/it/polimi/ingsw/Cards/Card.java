package it.polimi.ingsw.Cards;

import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Game.Totem;

import java.io.Serializable;

public abstract class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int era;
    private final String cardType;

    public Card(int era, String cardType) {
        this.era = era;
        this.cardType = cardType;
    }

    public int getEra() {
        return era;
    }
    public String getCardType() { return cardType; }

    public void printCard(){
        System.out.println("era:"+era);
        System.out.println("tipo di carta:"+cardType);
    }
}
