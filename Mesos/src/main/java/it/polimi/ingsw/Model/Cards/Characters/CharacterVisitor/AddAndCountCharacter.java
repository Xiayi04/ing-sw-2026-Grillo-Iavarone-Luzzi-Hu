package it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Model.Cards.Buildings.Building;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor.AddedFoodException;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor.ConcreteSetAndIconVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor.SetAndIconVisitor;
import it.polimi.ingsw.Model.Cards.Characters.*;
import it.polimi.ingsw.Model.Game.Player;

import static java.lang.Math.abs;

public class AddAndCountCharacter extends AbstractCharacterVisitor {

    private boolean setBonusCalculator(Player player){
        SetAndIconVisitor visitor = new ConcreteSetAndIconVisitor();
        for(Building b : player.getBuilding()){
            try {
                b.acceptSetBonus(visitor,player);
            } catch (AddedFoodException e) {
                return true;
            }
        }
        return false;
    }

    public boolean visit(Inventor inventor, Player player) {
        synchronized (player.getTribeCard()) {
            player.setInventorCounter((player.getInventorCounter()) + 1);
            player.getTribeCard().add(inventor);
            player.getTribeCard().notifyAll();
            SetAndIconVisitor setAndIconVisitor = new ConcreteSetAndIconVisitor();
            boolean isFoodIncreased = false;
            for(Building b : player.getBuilding()){
                try {
                    b.acceptSameIconBonus(setAndIconVisitor, player, inventor.getInventorIcon());
                } catch (AddedFoodException e) {
                    isFoodIncreased = true;
                }
            }
            if(isFoodIncreased || setBonusCalculator(player)){
                return true;
            }
        }
        return false;
    }

    public boolean visit(Shaman shaman, Player player) {
        synchronized (player.getTribeCard()) {
            player.setShamanCounter((player.getShamanCounter()) + 1);
            player.getTribeCard().add(shaman);
            player.modifyStarCounter(shaman.getShamanStars());
        }
        return setBonusCalculator(player);
    }

    public boolean visit(Picker picker, Player player) {
        synchronized (player.getTribeCard()) {
            player.setPickerCounter((player.getPickerCounter()) + 1);
            player.getTribeCard().add(picker);
        }
        return setBonusCalculator(player);
    }

    public boolean visit(Hunter hunter, Player player) {
        synchronized (player.getTribeCard()) {
            player.setHunterCounter((player.getHunterCounter()) + 1);
            player.getTribeCard().add(hunter);
            if (hunter.getSymbol()) {
                player.modifyFood(abs(player.getHunterCounter()));
                return setBonusCalculator(player);
            }
        }
        return false;
    }

    public boolean visit(Builder builder, Player player) {
        synchronized (player.getTribeCard()) {
            player.setBuilderCounter((player.getBuilderCounter() + 1));
            player.getTribeCard().add(builder);
            player.increaseBuilderDiscount(builder.getBuilderDiscount());
        }
        return setBonusCalculator(player);
    }

    public boolean visit(Painter painter, Player player) {
        synchronized (player.getTribeCard()) {
            player.setPainterCounter((player.getPainterCounter()) + 1);
            player.getTribeCard().add(painter);
        }
        return setBonusCalculator(player);
    }

}
