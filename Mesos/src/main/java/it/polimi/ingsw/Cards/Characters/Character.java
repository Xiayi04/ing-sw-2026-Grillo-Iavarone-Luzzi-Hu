package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterInterface;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.InventorInterface;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;

public abstract class Character extends Card implements CharacterInterface, InventorInterface {
    private final int numPlayers;
    private final String characterType;

    public Character(int era, String cardType, int numPlayers, String characterType) {
        super(era, "CHARACTER", true);
        this.numPlayers = numPlayers;
        this.characterType = characterType;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public String getCharacterType() {
        return this.characterType;
    }

    public void printCard(){
        super.printCard();
        System.out.println("numero giocatori:"+numPlayers);
        System.out.println(("tipo di personaggio:"+characterType));
    }


    public abstract String[] print(Printer printer);

    /**
     * @param visitor
     * @param player
     * @return
     */
    @Override
    public boolean addCard(CharacterVisitor visitor, Player player) {
        return false;
    }

    /**
     * @param visitor
     * @return
     */
    @Override
    public String isInventorAndGetIcon(CharacterVisitor visitor) {
        return "";
    }
}
