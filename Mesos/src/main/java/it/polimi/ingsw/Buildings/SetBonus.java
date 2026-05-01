package it.polimi.ingsw.Buildings;
import it.polimi.ingsw.Buildings.BuildingVisitor.BuildingInterface;
import it.polimi.ingsw.Buildings.BuildingVisitor.Visitor;
import it.polimi.ingsw.Game.Player;

public class SetBonus extends Building implements BuildingInterface {
    private int fullSetCounter;

    public SetBonus(int era, int price, int pp) {
        super(era, price, pp, "SetBonus");
        this.fullSetCounter = 0;
    }
    //aggiungere classe per questa roba
   public void giveExtraFoodSet(Player player){
        if(fullSetCounter != player.countSet()) {
            fullSetCounter = player.countSet();
            for(;fullSetCounter < player.countSet();fullSetCounter++){
                player.modifyFood(5);
            }
        }
    }

    @Override
    public void acceptActivation(Visitor visitor, Player player){
        visitor.visit(this, player);
    }

    public int getFullSetCounter(){
        return fullSetCounter;
    }

    public void setFullSetCounter(int fullSetCounter){
        this.fullSetCounter = fullSetCounter;
    }

    public void setUpdater(Player player){
        synchronized (player.getTribeCard()){
            while(player.countSet() != fullSetCounter){
                try{
                    player.getTribeCard().wait();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            giveExtraFoodSet(player);
        }
    }
}
