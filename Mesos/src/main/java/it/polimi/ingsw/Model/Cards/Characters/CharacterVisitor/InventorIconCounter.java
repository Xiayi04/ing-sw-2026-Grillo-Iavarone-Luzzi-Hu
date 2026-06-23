package it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Model.Cards.Characters.Inventor;

public class InventorIconCounter extends AbstractCharacterVisitor {
    @Override
    public String visit(Inventor inventor){
        return inventor.getInventorIcon().toLowerCase();
    }
}
