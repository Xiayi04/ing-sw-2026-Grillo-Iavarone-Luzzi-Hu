package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Cards.Characters.Inventor;

public class InventorIconCounter extends AbstractCharacterVisitor {
    @Override
    public String visit(Inventor inventor){
        return inventor.getInventorIcon().toLowerCase();
    }
}
