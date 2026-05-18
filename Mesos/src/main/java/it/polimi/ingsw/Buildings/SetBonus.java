package it.polimi.ingsw.Buildings;
import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;

public class SetBonus extends Building implements BuildingInterface {
    private int fullSetCounter;

    public SetBonus(int era, int price, int pp) {
        super(era, price, pp, "SetBonus");
        this.fullSetCounter = 0;
    }

    //aggiungere classe per questa roba
    public void giveExtraFoodSet(Player player) {
        if (fullSetCounter != player.countSet()) {
            fullSetCounter = player.countSet();
            for (; fullSetCounter < player.countSet(); fullSetCounter++) {
                player.modifyFood(5);
            }
        }
    }

    public String[] print(Printer printer) {
        return printer.print(this);
    }

    @Override
    public void acceptActivation(ActivationVisitor activationVisitor, Player player) {
        activationVisitor.visit(this, player);
    }

    public int getFullSetCounter() {
        return fullSetCounter;
    }

    public void setFullSetCounter(int fullSetCounter) {
        this.fullSetCounter = fullSetCounter;
    }

    public void setUpdater(Player player) {
        synchronized (player.getTribeCard()) {
            while (player.countSet() != fullSetCounter) {
                try {
                    player.getTribeCard().wait();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            giveExtraFoodSet(player);
        }
    }
}
