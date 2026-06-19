package it.polimi.ingsw.Cards.Characters;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.GuiVisitor;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.InventorInterface;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;
import javafx.scene.layout.VBox;

public class Inventor extends Character implements CharacterInterface, InventorInterface {
    private final String inventorIcon;


    public Inventor(int era, String cardType, int numPlayers, String characterType, String icon) {
        super(era, cardType, numPlayers, characterType);

        this.inventorIcon = icon;
    }

    public String getInventorIcon() {
        return inventorIcon.toLowerCase();
    }

    public String[] print(Printer printer){
        return printer.print(this);
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
    public boolean addCard(CharacterVisitor visitor, Player player){
        return visitor.visit(this, player);
    }

    public String getImagePath(){
        return "/images/cards/characters/"+getCharacterType()+"_"+getInventorIcon()+".png";
    }

    public VBox findBox(GuiVisitor visitor){
        return visitor.visit(this);
    }
}
