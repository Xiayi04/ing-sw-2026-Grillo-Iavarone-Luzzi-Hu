package it.polimi.ingsw.Buildings;
import it.polimi.ingsw.Game.Player;

public class DiscountBuilding extends Building{
    private final int foodBonus;
    private final int ppBonus;
    private final Icons typeIcons;
    private final Events typeEvents;


    public DiscountBuilding(int era, int price, int pp, int foodBonus, int ppBonus, Icons typeIcons, Events typeEvents) {
        super(era, price, pp,"DiscountBuilding");
        this.foodBonus = foodBonus;
        this.ppBonus = ppBonus;
        this.typeIcons = typeIcons;
        this.typeEvents = typeEvents;
    }

    public int getFoodBonus(){
        return foodBonus;
    }

    public int getPpBonus(){
        return ppBonus;
    }

    public Icons getTypeIcons(){
        return typeIcons;
    }

    public Events getTypeEvents() {
        return typeEvents;
    }

    public int getFoodBonusForPlayer(Player player){
        return foodBonus * player.countTribeCardsByIcon(typeIcons);
    }
    public int getPpBonus(Player player) {
        return ppBonus * player.countTribeCardsByIcon(typeIcons);
    }

    @Override
    public void buildingActivation(Player player) {}
}
