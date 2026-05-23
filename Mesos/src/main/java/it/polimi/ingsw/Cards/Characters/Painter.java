package it.polimi.ingsw.Cards.Characters;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.GuiVisitor;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;
import javafx.scene.layout.VBox;

public class Painter extends Character implements CharacterInterface {
    public Painter(int era, String cardType, int numPlayers, String characterType){
        super( era, cardType, numPlayers, characterType);
    }

    public void printCard(){
        super.printCard();
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }
    //accepter
    @Override
    public void addCard(CharacterVisitor visitor, Player player){
        visitor.visit(this, player);
    }

    public String getImagePath(){
        return "images/cards/characters/"+getCharacterType()+".png";
    }

    public VBox findBox(GuiVisitor visitor){
        return visitor.visit(this);
    }
}
