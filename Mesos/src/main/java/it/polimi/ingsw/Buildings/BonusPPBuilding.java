package it.polimi.ingsw.Buildings;


import it.polimi.ingsw.Game.Player;

public class BonusPPBuilding extends Building {
    public BonusPPBuilding(int era, int price) {
        super(era, price, 25, "BonusPPBuilding");                      //lo gestisco come pp finali dell'edificio(non da sommare subito)
    }

    @Override
    public void buildingActivation(Player player) {}

    public void printCard(){
        super.printCard();
    }
}


