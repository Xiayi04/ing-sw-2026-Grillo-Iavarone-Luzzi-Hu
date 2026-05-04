package it.polimi.ingsw.Cards.Characters;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.InventorInterface;
import it.polimi.ingsw.Game.Player;

import javax.swing.*;

public class Inventor extends Character implements CharacterInterface, InventorInterface {
    private final String inventorIcon;


    public Inventor(int era, String cardType, int numPlayers, String characterType, String icon) {
        super(era, cardType, numPlayers, characterType);

        this.inventorIcon = icon;
    }

    public String getInventorIcon() {
        return inventorIcon.toLowerCase();
    }

    public void printCard(){
        super.printCard();
        System.out.println("icona:"+inventorIcon);
    }
    @Override
    public String isInventorAndGetIcon(CharacterVisitor visitor){
        return visitor.visit(this);
    }

    @Override
    public void addCard(CharacterVisitor visitor, Player player){
        visitor.visit(this, player);
    }
}
