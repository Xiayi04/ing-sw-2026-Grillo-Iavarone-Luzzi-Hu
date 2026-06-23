package it.polimi.ingsw.Model.Cards.Characters;

import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.GuiVisitor;
import it.polimi.ingsw.Model.Game.Player;
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

    //accepter
    @Override
    public boolean addCard(CharacterVisitor visitor, Player player){
        return visitor.visit(this, player);
    }

    /**
     * The method constructs, using the card's parameters, the path to retrieve the image in the resources folder
     * @return the path to the specific image
     */
    public String getImagePath(){
        return "/images/cards/characters/"+getCharacterType().toLowerCase()+".png";
    }

    @Override
    public VBox findBox(GuiVisitor visitor) {
        return visitor.visit(this);
    }
}
