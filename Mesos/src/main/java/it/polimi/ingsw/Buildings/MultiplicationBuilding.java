package it.polimi.ingsw.Buildings;


import it.polimi.ingsw.Game.Player;

public class MultiplicationBuilding extends EndGameBuilding{
    private final Icons typeIcons;
    private final int multiplier;
    //costruttore
    public MultiplicationBuilding(int era, int price, int pp,String name, Icons typeIcons, int multiplier){
        super (era, price, pp, "MultiplicationBuilding", typeIcons);
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

    @Override
    public int countPP(Player player){
        if(typeIcons== Icons.SET){
            return player.countSet()*multiplier;
        }else{
            return player.countTribeCardsByIcon(typeIcons)*multiplier;
        }

    }


     //HO fatto due metodi in player per contare le carte di un tipo e i set completi

}



