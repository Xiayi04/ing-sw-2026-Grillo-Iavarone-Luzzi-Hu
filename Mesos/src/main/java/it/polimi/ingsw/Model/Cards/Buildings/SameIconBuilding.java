package it.polimi.ingsw.Model.Cards.Buildings;

import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SetAndIconVisitor.SetAndIconVisitor;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.UI.Printer;

import java.util.HashMap;
import java.util.Map;

public class SameIconBuilding extends Building implements BuildingInterface {
    Map<String, Integer> checkPair;

    public SameIconBuilding(int era, int price, int pp) {
        super(era, price, pp, "SameIconBuilding");
        this.checkPair = new HashMap<String, Integer>();
        checkPair.put("boat", 0);
        checkPair.put("tree", 0);
        checkPair.put("hook", 0);
        checkPair.put("necklace", 0);
        checkPair.put("bowl", 0);
        checkPair.put("knot", 0);
        checkPair.put("doll", 0);
        checkPair.put("flute", 0);
        checkPair.put("leather", 0);
        checkPair.put("bread", 0);
    }

    /**
     * update the icon map every time an inventor is drawn and, if a pair is found, give 3 food to the player
     * @param player
     * @param icon specific icon of the inventor
     * @return
     */
    public boolean giveFoodBonus(Player player, String icon) {
        boolean bonus = false;
        Integer v = checkPair.get(icon);
        v++;
        if (v == 2) {
            player.modifyFood(3);
            v = -1;
            bonus = true;
        }
        checkPair.put(icon, v);
        return bonus;
    }

    /**
     * The method is responsible for initializing the map that keeps track of how many icons a player has.
     * It is called when the building is drawn.
     * @param icon specific icon of the inventor
     */
    public void addInventorIconToMap(String icon) {
        if (icon.isEmpty())
            return;
        Integer v = checkPair.get(icon);

        if (v == null)
            throw new RuntimeException("The map is not initialized correctly");
        v++;
        if (v == 2)
            v = -1;
        checkPair.put(icon, v);
    }

    public String[] print(Printer printer) {
        return printer.print(this);
    }

    @Override
    public void acceptActivation(ActivationVisitor activationVisitor, Player player) {
        activationVisitor.visit(this, player);
    }

    public Map<String, Integer> getCheckPair() {
        return checkPair;
    }

    @Override
    public void acceptSameIconBonus(SetAndIconVisitor visitor, Player player, String icon) {
        visitor.visit(this,player, icon);
    }
}
