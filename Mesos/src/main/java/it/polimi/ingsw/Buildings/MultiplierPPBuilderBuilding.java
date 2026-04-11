package it.polimi.ingsw.Buildings;

import it.polimi.ingsw.Game.Player;

public class MultiplierPPBuilderBuilding extends EndGameBuilding{

    public MultiplierPPBuilderBuilding(int era, int price, int pp, String name, Icons typeIcons) {
        super(era, price, pp,"MultiplierPPBuilderBuilding", typeIcons);
    }
    @Override
    public int countPP(Player player){
        return player.builderBonus(); /*devo creare un metodo in player che calcola builderbonus(?)*/
    }





}


