package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.Inventor;
import it.polimi.ingsw.Game.Player;

import java.util.HashMap;
import java.util.Map;

public class SameIconBuilding extends Building implements BuildingInterface {
    Map<String, Integer> checkPair;

    public SameIconBuilding(int era, int price, int pp) {
        super(era, price, pp, "SameIconBuilding");
        this.checkPair = new HashMap<String, Integer>();
        checkPair.put("boat", 0);
        checkPair.put("tree",0);
        checkPair.put("hook",0);
        checkPair.put("necklace",0);
        checkPair.put("bowl",0);
        checkPair.put("knot",0);
        checkPair.put("doll",0);
        checkPair.put("flute",0);
        checkPair.put("leather",0);
        checkPair.put("bread",0);
    }
    public void giveFoodBonus(Player player, Inventor inventor){
        Integer v = checkPair.get(inventor.getInventorIcon());
        v++;
        if(v==2) {
            player.modifyFood(3);
            v = -1;
        }
        checkPair.put(inventor.getInventorIcon(), v);
    }

    @Override
    public void accept(Visitor visitor, Player player){
        visitor.visit(this, player);
    }

    public Map<String, Integer> getCheckPair(){
        return checkPair;
    }

}
