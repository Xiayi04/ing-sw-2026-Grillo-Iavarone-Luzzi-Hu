package it.polimi.ingsw.Cards;

import it.polimi.ingsw.Cards.Characters.CharacterType;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.UI.Printer;

import java.io.Serializable;

public abstract class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int era;
    private final String cardType;
    private boolean isFaceDown;

    public Card(int era, String cardType) {
        this.era = era;
        this.cardType = cardType;
        this.isFaceDown = true;
    }

    public int getEra() {
        return era;
    }

    public String getCardType() { return cardType; }

    public boolean isFaceDown() {
        return isFaceDown;
    }

    public void setFaceUp(){
        isFaceDown=false;
    }

    public void printCard(){
        System.out.println("era:"+era);
        System.out.println("tipo di carta:"+cardType);
    }

    public abstract String[] print(Printer printer);

    public abstract String getImagePath();
}
