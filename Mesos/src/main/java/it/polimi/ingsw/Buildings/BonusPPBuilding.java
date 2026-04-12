package it.polimi.ingsw.Buildings;


public class BonusPPBuilding extends Building {
    public BonusPPBuilding(int era, int price) {
        super(era, price, 25, "BonusPPBuilding");                      //lo gestisco come pp finali dell'edificio(non da sommare subito)
    }

    public void printCard(){
        super.printCard();
    }
}


