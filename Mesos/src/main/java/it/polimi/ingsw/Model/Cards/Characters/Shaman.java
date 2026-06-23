package it.polimi.ingsw.Model.Cards.Characters;

import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.GuiVisitor;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;
import javafx.scene.layout.VBox;

public class Shaman extends Character implements CharacterInterface {
    private final Integer shamanStars;

    public Shaman(int era, String cardType, int numPlayers, String characterType, Integer shamanStars) {
        super(era, cardType, numPlayers, characterType);
        this.shamanStars = shamanStars;
    }
    //metodo getter
    public Integer getShamanStars() {
        return this.shamanStars;
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }

    @Override
    public boolean addCard(CharacterVisitor visitor, Player player){
        return visitor.visit(this, player);
    }

//    /**
//     * @param players is an ArrayList which contains al the players' informations
//     * @return  the highest number of stars among the players
//     */

    /**
     * The method constructs, using the card's parameters, the path to retrieve the image in the resources folder
     * @return the path to the specific image
     */
    public String getImagePath(){
        return "/images/cards/characters/"+getCharacterType().toLowerCase()+"_"+getShamanStars()+"star.png";
    }

    @Override
    public VBox findBox(GuiVisitor visitor) {
        return visitor.visit(this);
    }
}
