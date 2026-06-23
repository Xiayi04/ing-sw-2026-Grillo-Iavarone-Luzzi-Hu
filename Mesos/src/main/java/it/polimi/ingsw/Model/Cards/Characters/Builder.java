package it.polimi.ingsw.Model.Cards.Characters;

import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.GuiVisitor;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;
import javafx.scene.layout.VBox;

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
    @Override
    public String[] print(Printer printer){
        return printer.print(this);
    }

    //accepter
    @Override
    public boolean addCard(CharacterVisitor visitor, Player player){
        return visitor.visit(this, player);
    }

    public String getImagePath(){
        return "/images/cards/characters/"+getCharacterType().toLowerCase()+"_"+getPP()+"_"+getBuilderDiscount()+".png";
    }

    @Override
    public VBox findBox(GuiVisitor visitor) {
        return visitor.visit(this);
    }
}
