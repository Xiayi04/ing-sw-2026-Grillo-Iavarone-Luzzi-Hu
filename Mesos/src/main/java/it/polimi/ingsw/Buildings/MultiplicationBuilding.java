package it.polimi.ingsw.Buildings;

import Game.Board;


public class MultiplicationBuilding extends EndGameBuilding{
    private final Icons typeIcons;
    private final int multiplier;
    //costruttore
    public MultiplicationBuilding(int era, int price, int pp, Icons typeIcons, int multiplier){
        super (era, price, pp);
        this.typeIcons = typeIcons;
        this.multiplier = multiplier;

        }
        //getter
    public int getMultiplier(){
        return multiplier;
    }
    public Icons getTypeIcons(){
        return typeIcons;
    }

//manca
    public int countPP(){


    }




}



