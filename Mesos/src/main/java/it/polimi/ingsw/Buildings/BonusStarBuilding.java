package it.polimi.ingsw.Buildings;
import  it.polimi.ingsw.Game.Player;
public class BonusStarBuilding extends Building {
    public BonusStarBuilding(int era, int price, int pp,String name) {
        super(era, price, pp, "BonusStarBuilding");
    }
        public void addStar(Player player){
            player.modifyStarCounter(3);
        }

    }
