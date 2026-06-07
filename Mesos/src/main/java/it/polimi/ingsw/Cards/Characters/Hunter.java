package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.GuiVisitor;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;
import javafx.scene.layout.VBox;

public class Hunter extends Character implements CharacterInterface {
    private final boolean symbol;

    public Hunter(int era, String cardType, int numPlayers,String characterType, boolean symbol) {
        super(era, cardType, numPlayers, characterType);

        this.symbol = symbol;
    }
    //metodi getter
    public boolean getSymbol() {
        return symbol;
    }

    public void printCard(){
        super.printCard();
        System.out.println("simbolo:"+symbol);
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }

    @Override
    public boolean addCard(CharacterVisitor visitor, Player player){
        return visitor.visit(this, player);
    }

    public String getImagePath(){
        return "images/cards/characters/"+getCharacterType()+"_"+getSymbol()+".png";
    }

    public VBox findBox(GuiVisitor visitor){
        return visitor.visit(this);
    }
}
