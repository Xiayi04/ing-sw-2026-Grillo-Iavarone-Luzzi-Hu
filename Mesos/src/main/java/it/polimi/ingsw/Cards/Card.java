package it.polimi.ingsw.Cards;

public abstract class Card {
    private final int era;
    private final CardType cardType;

    public Card(int era, CardType cardType) {
        this.era = era;
        this.cardType = cardType;
    }

    public int getEra() {
        return era;
    }
    public String getCardType() { return cardType.toString(); }
}
