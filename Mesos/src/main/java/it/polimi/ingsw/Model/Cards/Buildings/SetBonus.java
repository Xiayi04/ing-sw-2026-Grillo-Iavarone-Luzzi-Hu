package it.polimi.ingsw.Model.Cards.Buildings;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor.AddedFoodException;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor.SetAndIconVisitor;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;

public class SetBonus extends Building implements BuildingInterface {
    private int fullSetCounter;

    public SetBonus(int era, int price, int pp) {
        super(era, price, pp, "SetBonus");
        this.fullSetCounter = 0;
    }

    //aggiungere classe per questa roba
    public boolean giveExtraFoodSet(Player player) {
        boolean bonus = false;
        if (fullSetCounter != player.countSet()) {
            //fullSetCounter = player.countSet(); se facciamo questa riga non entriamo mai nell'if
            for (; fullSetCounter < player.countSet(); fullSetCounter++) {
                player.modifyFood(5);
            }
            bonus = true;
        }
        return bonus;
    }

    public String[] print(Printer printer) {
        return printer.print(this);
    }

    @Override
    public void acceptActivation(ActivationVisitor activationVisitor, Player player) {
        activationVisitor.visit(this, player);
    }

    @Override
    public void acceptSetBonus(SetAndIconVisitor visitor, Player player) {
        try {
            visitor.visit(this, player, null);
        } catch (AddedFoodException e) {
            throw new AddedFoodException("");
        }
    }

    public int getFullSetCounter() {
        return fullSetCounter;
    }

    public void setFullSetCounter(int fullSetCounter) {
        this.fullSetCounter = fullSetCounter;
    }
}
