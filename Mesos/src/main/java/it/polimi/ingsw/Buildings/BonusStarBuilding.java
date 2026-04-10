package it.polimi.ingsw.Buildings;
import  it.polimi.ingsw.Game.Player;
public class BonusStarBuilding extends Building {
    public BonusStarBuilding(int era, int price, int pp) {
        super(era, price, pp);
    }
        public void addStar(Player player){
            player.modifyStarCounter(3);
        }

    }
