package it.polimi.ingsw.Model.Factory;

import it.polimi.ingsw.FileLoader.*;
import it.polimi.ingsw.Model.Cards.Buildings.*;

import java.util.ArrayList;

public class BuildingFactory {

    public Building createBonusPPBuilding(int era, int price){
        return new BonusPPBuilding(era, price);
    }

    public Building createMultiplicationBuilding(int era, int price, int pp, String typeIcon, int multiplier){
        return new MultiplicationBuilding(era, price, pp, typeIcon, multiplier);
    }

    public Building createMultiplierPPBuilderBuilding(int era, int price, int pp){
        return new MultiplierPPBuilderBuilding(era, price, pp, "BUILDER");
    }

    public Building createDiscountBuilding(int era, int price, int pp, int foodBonus, int ppBonus, Icons typeIcons, Events typeEvents){
        return new DiscountBuilding(era, price, pp, foodBonus, ppBonus, typeIcons, typeEvents);
    }

    public Building createNoMalusBuilding(int era, int price, int pp){
        return new NoMalusBuilding(era, price, pp);
    }

    public Building createDoubleBonusBuilding(int era, int price, int pp){
        return new DoubleBonusBuilding(era, price, pp);
    }

    public Building createBonusStarsBuilding(int era, int price, int pp){
        return new BonusStarBuilding(era, price, pp);
    }

    public Building createAddCard(int era, int price, int pp){
        return new AddCard(era, price, pp);
    }

    public Building createBonusFood(int era, int price, int pp){
        return new BonusFood(era, price, pp);
    }

    public Building createSetBonus(int era, int price, int pp){
        return new SetBonus(era, price, pp);
    }

    public Building createSameIconBuilding(int era, int price, int pp){
        return new SameIconBuilding(era, price, pp);
    }

    /**
     * the method through the loader and the getter methods of BuildingByEraDTO derives the arrays composed of
     * elements of type BuildingDTO. through the getter methods of BuildingDTO it obtains the useful values to
     * create all types of buildings.
     * @return a list with all the buildings
     */
    public ArrayList<Building> createBuildingList(){

        Loader loader = new Loader();
        BuildingsByEraDTO buildingDTO = loader.loadBuildingsByEra();
        ArrayList<Building> buildings = new ArrayList<>();

        for(BuildingDTO b : buildingDTO.getEra1()){
            int era = b.getBuildingDTOEra();
            int price = b.getBuildingDTOPrice();
            int pp = b.getPP();
            switch(b.getBuildingName().toLowerCase()){
                case "discountbuilding":
                    int foodBonus = b.getFoodBonus();
                    int ppBonus = b.getPPBonus();
                    Icons typeIcon = b.getTypeIcons();
                    Events typeEvent = b.getTypeEvents();
                    buildings.add(createDiscountBuilding(era, price, pp, foodBonus, ppBonus, typeIcon, typeEvent));
                    break;
                case "nomalusbuilding":
                    buildings.add(createNoMalusBuilding(era, price, pp));
                    break;
                case "setbonus":
                    buildings.add(createSetBonus(era, price, pp));
                    break;
                case "bonusfood":
                    buildings.add(createBonusFood(era, price, pp));
                    break;
                case "sameiconbuilding":
                    buildings.add(createSameIconBuilding(era, price, pp));
                    break;
            }
        }

        for(BuildingDTO b : buildingDTO.getEra2()){
            int era = b.getBuildingDTOEra();
            int price = b.getBuildingDTOPrice();
            int pp = b.getPP();
            switch(b.getBuildingName().toLowerCase()){
                case "multiplicationbuilding":
                    Icons typeIcon = b.getTypeIcons();
                    int multiplier = b.getMultiplier();
                    buildings.add(createMultiplicationBuilding(era, price, pp, String.valueOf(typeIcon), multiplier));
                    break;
                case "multiplierppbuilderbuilding":
                    buildings.add(createMultiplierPPBuilderBuilding(era, price, pp));
                    break;
                case "discountbuilding":
                    int foodBonus = b.getFoodBonus();
                    int ppBonus = b.getPPBonus();
                    Icons typeIcon2 = b.getTypeIcons();
                    Events typeEvent = b.getTypeEvents();
                    buildings.add(createDiscountBuilding(era, price, pp, foodBonus, ppBonus, typeIcon2, typeEvent));
                    break;
                case "doublebonusbuilding":
                    buildings.add(createDoubleBonusBuilding(era, price, pp));
                    break;
                case "bonusstarbuilding":
                    buildings.add(createBonusStarsBuilding(era, price, pp));
                    break;
            }
        }

        for(BuildingDTO b : buildingDTO.getEra3()){
            int era = b.getBuildingDTOEra();
            int price = b.getBuildingDTOPrice();
            int pp = b.getPP();
            switch(b.getBuildingName().toLowerCase()){
                case "bonusppbuilding":
                    buildings.add(createBonusPPBuilding(era, price));
                    break;
                case "addcard":
                    buildings.add(createAddCard(era, price, pp));
                    break;
                case "multiplicationbuilding":
                    Icons typeIcon = b.getTypeIcons();
                    int multiplier = b.getMultiplier();
                    buildings.add(createMultiplicationBuilding(era, price, pp, String.valueOf(typeIcon), multiplier));
                    break;
            }
        }
        return buildings;
    }
}
