package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Game.Player;

public interface CharacterInterface {
    public void addCard(CharacterVisitor visitor, Player player);
    }
