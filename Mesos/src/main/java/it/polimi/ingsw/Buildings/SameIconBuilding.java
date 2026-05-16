package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.ActivationVisitor;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.InventorIconCounter;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.UI.Printer;

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
    public void giveFoodBonus(Player player, String icon){
        Integer v = checkPair.get(icon);
        v++;
        if(v==2) {
            player.modifyFood(3);
            v = -1;
        }
        checkPair.put(icon, v);
    }

    public void addInventorIconToMap(String icon){
        if(icon.equals(" "))
            return;
        Integer v = checkPair.get(icon);

        if(v==null)
            throw new RuntimeException("The map is not initialized correctly");
        v++;
        if(v==2)
            v=-1;
        checkPair.put(icon, v);
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }

    @Override
    public void acceptActivation(ActivationVisitor activationVisitor, Player player){
        activationVisitor.visit(this, player);
    }

    public Map<String, Integer> getCheckPair(){
        return checkPair;
    }

    public void mapUpdater(Player player){
        Thread t = new Thread(()->{
            int i = 0;
            while(true){
                synchronized (player.getTribeCard()){
                    int prevSize =  player.getTribeCard().size();
                    while (player.getTribeCard().size() == prevSize){
                        try{
                            player.getTribeCard().wait();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    for(;i<player.getTribeCard().size();i++){
                        Character c = player.getTribeCard().get(i);
                        String icon = c.isInventorAndGetIcon(new InventorIconCounter());
                        if(icon.equals(" "))
                            continue;
                        giveFoodBonus(player, icon);
                    }

                }
            }
        });
        t.start();
    }
}
