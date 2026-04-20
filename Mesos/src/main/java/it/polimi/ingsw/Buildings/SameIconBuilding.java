package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.Inventor;
import it.polimi.ingsw.Game.Player;

import java.util.Map;

public class SameIconBuilding extends Building implements ActivationInterface {
    Map<String, Integer> checkPair;

    public SameIconBuilding(int era, int price, int pp) {
        super(era, price, pp, "SameIconBuilding");
        this.checkPair = Map.of
                ("boat", 0,
                 "tree",0,
                 "hook",0,
                 "necklace",0,
                 "bowl",0,
                 "knot",0,
                 "doll",0,
                 "flute",0,
                 "leather",0,
                 "bread",0);
    }
    public void giveFoodBonus(Player player, Inventor inventor){
        int v = checkPair.get(inventor.getInventorIcon());
        v++;
        if(v==2) {
            player.modifyFood(3);
            v = -1;
        }
        checkPair.put(inventor.getInventorIcon(), v);
    }
    @Override
    public void buildingActivation(Player p){

        for(Character c : p.getTribeCard()){

            if(c instanceof Inventor){
                int v = checkPair.get(((Inventor)c).getInventorIcon());
                v++;
                if(v==2)
                    v=-1;
                checkPair.put(((Inventor)c).getInventorIcon(), v);
            }
        }
    }
}
