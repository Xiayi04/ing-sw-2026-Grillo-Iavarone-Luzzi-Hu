package it.polimi.ingsw.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Game.Player;

import static java.lang.Math.abs;

public class AddAndCountCharacter extends AbstractCharacterVisitor {

    public void visit(Inventor inventor, Player player) {
        synchronized (player.getTribeCard()) {
            player.setInventorCounter((player.getInventorCounter()) + 1);
            player.getTribeCard().add(inventor);
            player.getTribeCard().notifyAll();
        }
    }

    public void visit(Shaman shaman, Player player) {
        synchronized (player.getTribeCard()) {
            player.setShamanCounter((player.getShamanCounter()) + 1);
            player.getTribeCard().add(shaman);
            player.modifyStarCounter(shaman.getShamanStars());
        }
    }

    public void visit(Picker picker, Player player) {
        synchronized (player.getTribeCard()) {
            player.setPickerCounter((player.getPickerCounter()) + 1);
            player.getTribeCard().add(picker);
        }
    }

    public void visit(Hunter hunter, Player player) {
        synchronized (player.getTribeCard()) {
            player.setHunterCounter((player.getHunterCounter()) + 1);
            player.getTribeCard().add(hunter);
            if (hunter.getSymbol()) {
                player.modifyFood(abs(player.getHunterCounter()));
            }
        }
    }

    public void visit(Builder builder, Player player) {
        synchronized (player.getTribeCard()) {
            player.setBuilderCounter((player.getBuilderCounter() + 1));
            player.getTribeCard().add(builder);
            player.increaseBuilderDiscount(builder.getBuilderDiscount());
        }
    }

    public void visit(Painter painter, Player player) {
        synchronized (player.getTribeCard()) {
            player.setPainterCounter((player.getPainterCounter()) + 1);
            player.getTribeCard().add(painter);
        }

    }

}
