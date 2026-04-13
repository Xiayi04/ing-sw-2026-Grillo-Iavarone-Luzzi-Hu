package it.polimi.ingsw.Game;
import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Buildings.MultiplicationBuilding;
import it.polimi.ingsw.Buildings.MultiplierPPBuilderBuilding;
import it.polimi.ingsw.Cards.Characters.Builder;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.Inventor;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private final String name;
    private final Totem totem;
    private int food;
    private int prestigePoints;
    private final ArrayList<Character> tribeCard;
    private final ArrayList<Building> buildings;
    private int starCounter;

    //metodo costruttore
    public Player(String name, Totem totem, int food){
        this.name = name;
        this.totem = totem;
        this.food = food;
        this.prestigePoints = 0;
        this.tribeCard = new ArrayList<Character>();
        this.buildings = new ArrayList<Building>();
        this.starCounter = 0;

    }

    //metodi getter
    public String getName() {
        return name;
    }
    public int getFood() {
        return food;
    }
    public Totem getTotem() {
        return totem;
    }
    public int getPrestigePoints(){
        return prestigePoints;
    }
    public ArrayList<Character> getTribeCard(){
        return tribeCard;
    }
    public ArrayList<Building> getBuilding(){
        return buildings;
    }
    public int getStarCounter(){
        return starCounter;
    }

    public void modifyPP(int pp){
        prestigePoints+=pp;
    }

    public void modifyFood(int f){
        food+=f;
        /*if(food<0){
            int PPDebt = -food;
            modifyPP(PPDebt);
            food = 0;
        }*/
    }

    public void modifyStarCounter(int bonus){
        starCounter+= bonus ;
    }

    /**
     * The method calculates a player's final PP taking into account the current PP, the buildings, and the
     * bonuses given by the characters
     * @return final PP
     */
    public int finalScore() {
        return prestigePoints + inventorBonus() + painterBonus() + builderBonus() +
               buildingBonus() + buildingMultipliedBonus();
    }

    /**
     * the method scrolls through the player's list of characters, counts the inventors and the number of
     * different icons it finds, and finally multiplies the two values
     * @return the inventors' bonus
     */
    public int inventorBonus() {
        List<String> icons = new ArrayList<>();
        int numInventor = 0;
        boolean trovato=false;
        for(Character c : tribeCard){
            if(c instanceof Inventor inventor){
                numInventor++;
                String currentIcon = inventor.getInventorIcon().toLowerCase();//per confrontare le stringhe
                for(int i=0; i<icons.size() && !trovato; i++){
                    if(icons.get(i).toLowerCase().equals(currentIcon))
                        trovato=true;
                }
                if(!trovato)
                    icons.add(currentIcon);
            }
        }
        return numInventor*icons.size();
    }

    /**
     * The method uses countTribeCardsByIcon to find the number of painters in the player's character list and
     * through the formula returns the bonus
     * @return the painters' bonus
     */
    public int painterBonus() {
        return (countTribeCardsByIcon(Icons.PAINTER)/2)*10;
    }

    /**
     * the method goes through the player's list of characters and sums all the PP points given by the builders.
     * if in the player's list of buildings there is a building of the type MultiplierPPBuilderBuilding the
     * builders give double PP points.
     * @return sum of all the PP points given by the builders
     */
    public int builderBonus() {
        int sumPP = 0;
        for(Character c : tribeCard){
            if(c instanceof Builder builder){
                int PP = builder.getPP();
                sumPP+=PP;
            }
        }
        if(buildings.stream().anyMatch(b -> b instanceof MultiplierPPBuilderBuilding)){
            sumPP = sumPP *2;
        }
        return sumPP;
    }

    /**
     * the method goes through the player's list of building and sums all the PP points given by the building
     * @return sum of all the PP points given by the building
     */
    public int buildingBonus(){
        int sumPP = 0;
        for(Building b : buildings){
            sumPP+=b.getPP();
        }
        return sumPP;
    }

    /**
     * The method checks if there are any MultiplicationBuildings in the player's list of buildings, if so it
     * calls the building's method and adds up the bonus points given by each building of that type
     * @return the sum of the bonus points given by the MultiplicationBuilding
     */
    public int buildingMultipliedBonus(){
        int sumPP = 0;
        for(Building b : buildings){
            if(b instanceof MultiplicationBuilding ed)
                sumPP += ed.countPP(this);
        }
        return sumPP;
    }

    /**
     *
     * @param wantedIcon
     * wantedIcon is one of the 6 types of character.
     * the method goes through the entire list of the player's characters and counts how many characters of the
     * type wantedIcon are present
     * @return the number of wantedIcon-type characters
     */
    public int countTribeCardsByIcon(Icons wantedIcon){
        int count = 0;
        for(Character c : tribeCard){
            if(c.getCharacterType().toString().equals(wantedIcon.toString()))
                count++;
        }
        return count;
    }

    /**
     * The method through countTribeCardsByIcon counts how many characters of each type are in the player's
     * character list, the number of sets will be the minimum among the numbers found previously
     * @return the number of sets
     */
    public int countSet(){
        int inventor = countTribeCardsByIcon(Icons.INVENTOR);
        int builder = countTribeCardsByIcon(Icons.BUILDER);
        int hunter = countTribeCardsByIcon(Icons.HUNTER);
        int painter = countTribeCardsByIcon(Icons.PAINTER);
        int picker = countTribeCardsByIcon(Icons.PICKER);
        int shaman = countTribeCardsByIcon(Icons.SHAMAN);

        int min = inventor;

        if (builder < min) {
            min = builder;
        }
        if (hunter < min) {
            min = hunter;
        }
        if (painter < min) {
            min = painter;
        }
        if (picker < min) {
            min = picker;
        }
        if (shaman < min) {
            min = shaman;
        }

        return min;
    }
}
