package it.polimi.ingsw.Buildings;
import it.polimi.ingsw.Game.Player;

public class SetBonus extends Building implements ActivationInterface {
    private int setCounter;

    public SetBonus(int era, int price, int pp) {
        super(era, price, pp, "SetBonus");
        this.setCounter = 0;
    }

   public void giveExtraFoodSet(Player player){
        if(setCounter != player.countSet()) {
            setCounter = player.countSet();
            player.modifyFood(5);
        }
    }

    @Override
    public void buildingActivation(Player p) {
        setCounter = p.countSet();
    }
}
