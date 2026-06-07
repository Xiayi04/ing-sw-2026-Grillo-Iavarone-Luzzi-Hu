package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.GuiVisitor;
import it.polimi.ingsw.Game.Player;
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

    public void printCard(){
        super.printCard();
        System.out.println("stelle:"+shamanStars);
    }
    @Override
    public boolean addCard(CharacterVisitor visitor, Player player){
        return visitor.visit(this, player);
    }

//    /**
//     * @param players is an ArrayList which contains al the players' informations
//     * @return  the highest number of stars among the players
//     */

    public String getImagePath(){
        return "images/cards/characters/"+getCharacterType()+"_"+getShamanStars()+"star.png";
    }

    @Override
    public VBox findBox(GuiVisitor visitor) {
        return visitor.visit(this);
    }
}
