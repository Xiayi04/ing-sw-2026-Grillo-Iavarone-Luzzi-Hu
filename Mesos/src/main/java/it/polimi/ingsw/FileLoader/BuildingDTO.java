package it.polimi.ingsw.FileLoader;

import it.polimi.ingsw.Buildings.Events;
import it.polimi.ingsw.Buildings.Icons;

public class BuildingDTO {
    int era;
    int price;
    int pp;
    String buildingName;
    Integer foodBonus;
    Integer ppBonus;
    Icons typeIcons;
    Events typeEvents;
    Integer multiplier;
    int setCompleted;

    public int getBuildingDTOEra() {return era;}
    public int getBuildingDTOPrice() {return  price;}
    public int getPP() {return pp;}
    public String getBuildingName() {return buildingName.toString();}
    public Integer getFoodBonus() {return foodBonus;}
    public Integer getPPBonus() {return ppBonus;}
    public Icons getTypeIcons() {return typeIcons;}
    public Events getTypeEvents() {return typeEvents;}
    public Integer getMultiplier() {return multiplier;}
    public int getSetCompleted() {return setCompleted;}
}
