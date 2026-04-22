package it.polimi.ingsw.Buildings.BuildingVisitor;

import it.polimi.ingsw.Buildings.BonusStarBuilding;
import it.polimi.ingsw.Buildings.SameIconBuilding;
import it.polimi.ingsw.Buildings.SetBonus;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.Inventor;
import it.polimi.ingsw.Game.Player;
import java.util.Map;

public class ConcreteBuildingActivation extends BuildingActivation{
    public void visit(BonusStarBuilding visitorBonusStarBuilding, Player player){
        player.modifyStarCounter(3);
    }
    //TOGLIERE ISTANCE OF
    public void visit(SameIconBuilding sameIconBuilding, Player player){
        for(Character c : player.getTribeCard()){
            Map<String, Integer> checkPair = sameIconBuilding.getCheckPair();

            if(c instanceof Inventor){
                Integer v = checkPair.get(((Inventor)c).getInventorIcon());
                v++;
                if(v==2)
                    v=-1;
                checkPair.put(((Inventor)c).getInventorIcon(), v);
            }
        }
    }

    public void visit(SetBonus setBonus, Player player){
        int fullSetCounter = player.countSet();
        setBonus.setFullSetCounter(fullSetCounter);
    }
}
