package it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor;

import it.polimi.ingsw.Model.Cards.Buildings.AddCard;
import it.polimi.ingsw.Model.Cards.Buildings.BonusStarBuilding;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.SpecialBuildings.AddCardException;
import it.polimi.ingsw.Model.Cards.Buildings.SameIconBuilding;
import it.polimi.ingsw.Model.Cards.Buildings.SetBonus;
import it.polimi.ingsw.Model.Cards.Characters.Character;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.InventorIconCounter;
import it.polimi.ingsw.Model.Game.Player;

public class ConcreteBuildingActivation extends BuildingActivation{
    public void visit(BonusStarBuilding visitorBonusStarBuilding, Player player){
        player.modifyStarCounter(3);
    }
    //TOGLIERE ISTANCE OF
    public void visit(SameIconBuilding sameIconBuilding, Player player){
        CharacterVisitor cv = new InventorIconCounter();
        synchronized (player.getTribeCard()) {
            for (Character c : player.getTribeCard()) {
                sameIconBuilding.addInventorIconToMap(c.isInventorAndGetIcon(cv));
            }
        }
    }

    public void visit(SetBonus setBonus, Player player){
        int fullSetCounter;
        synchronized (player.getTribeCard()) {
            fullSetCounter = player.countSet();
        }
        setBonus.setFullSetCounter(fullSetCounter);
    }

    @Override
    public void visit(AddCard visitorAddCard, Player player){
        throw new AddCardException("");
    }
}
