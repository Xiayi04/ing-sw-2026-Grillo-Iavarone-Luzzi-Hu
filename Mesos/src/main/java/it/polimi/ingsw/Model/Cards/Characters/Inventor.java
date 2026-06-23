package it.polimi.ingsw.Model.Cards.Characters;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.GuiVisitor;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.InventorInterface;
import it.polimi.ingsw.Model.Game.Player;
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

    @Override
    public String isInventorAndGetIcon(CharacterVisitor visitor){
        return visitor.visit(this);
    }

    @Override
    public boolean addCard(CharacterVisitor visitor, Player player){
        return visitor.visit(this, player);
    }

    /**
     * The method constructs, using the card's parameters, the path to retrieve the image in the resources folder
     * @return the path to the specific image
     */
    public String getImagePath(){
        return "/images/cards/characters/"+getCharacterType().toLowerCase()+"_"+getInventorIcon()+".png";
    }

    public VBox findBox(GuiVisitor visitor){
        return visitor.visit(this);
    }
}
