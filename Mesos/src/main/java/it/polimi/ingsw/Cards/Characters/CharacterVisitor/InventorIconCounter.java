package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Cards.Characters.Inventor;
import it.polimi.ingsw.Game.Player;

public class InventorIconCounter extends InventorIconAbstractCounter{
    @Override
    public String visit(Inventor inventor){
        return inventor.getInventorIcon().toLowerCase();
    }
}
