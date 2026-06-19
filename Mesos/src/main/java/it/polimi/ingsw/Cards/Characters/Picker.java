package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.GuiVisitor;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;
import javafx.scene.layout.VBox;

public class Picker extends Character implements CharacterInterface {
    private final int discount = -3;

    public Picker(int era, String cardType, int numPlayers, String characterType) {
        super(era, cardType, numPlayers, characterType);
    }

    public int getDiscount() {
        return discount;
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }

    public void printCard(){
        super.printCard();
        System.out.println("sconto:"+discount);
    }
    //accepter
    @Override
    public boolean addCard(CharacterVisitor visitor, Player player){
        return visitor.visit(this, player);
    }

    public String getImagePath(){
        return "/images/cards/characters/"+getCharacterType()+".png";
    }

    @Override
    public VBox findBox(GuiVisitor visitor) {
        return visitor.visit(this);
    }
}
