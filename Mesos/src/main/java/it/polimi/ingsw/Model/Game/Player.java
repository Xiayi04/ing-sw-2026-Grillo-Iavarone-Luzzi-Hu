package it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Cards.Buildings.Building;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EndGame.EndGameVisitor;
import it.polimi.ingsw.Model.Cards.Buildings.BuildingVisitor.EndGame.EndGameVisitorInterface;
import it.polimi.ingsw.Model.Cards.Buildings.MultiplicationBuilding;
import it.polimi.ingsw.Model.Cards.Buildings.MultiplierPPBuilderBuilding;
import it.polimi.ingsw.Model.Cards.Characters.Builder;
import it.polimi.ingsw.Model.Cards.Characters.Character;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.InventorIconCounter;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String name;
    private final Totem totem;
    //private int food;
    //private int prestigePoints;
    private final ArrayList<Character> tribeCard;
    private final ArrayList<Building> buildings;
    private int starCounter;
    private int builderDiscount = 0;
    private int hunterCounter;
    private int builderCounter;
    private int pickerCounter;
    private int painterCounter;
    private int inventorCounter;
    private int shamanCounter;
    private transient final VirtualClientInterface virtualClient;
    public static Object countersLock = new Object();
    private AtomicInteger food = new AtomicInteger(0);
    private AtomicInteger prestigePoints = new AtomicInteger(0);

    //Constructor
    public Player(String name, Totem totem, int food, VirtualClientInterface virtualClient){
        this.name = name;
        this.totem = totem;
        this.tribeCard = new ArrayList<Character>();
        this.buildings = new ArrayList<Building>();
        this.starCounter = 0;
        this.virtualClient = virtualClient;
    }

    //metodi getter
    public String getName() {
        return name;
    }
    public int getFood() {return food.get();}
    public int getPrestigePoints() {
        return prestigePoints.get();
    }
    public Totem getTotem() {
        return totem;
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
    public int getHunterCounter(){
        return hunterCounter;
    }
    public int getBuilderCounter(){
        return builderCounter;
    }
    public int getPickerCounter(){
        return pickerCounter;
    }
    public int getPainterCounter(){
        return painterCounter;
    }
    public int getInventorCounter(){
        return inventorCounter;
    }
    public int getShamanCounter(){
        return shamanCounter;
    }
    public VirtualClientInterface getVirtualClient() {
        return virtualClient;
    }
    public int getBuilderDiscount(){
        return builderDiscount;
    }
    //setter methods
    public void setHunterCounter(int count){
        hunterCounter=count;
    }
    public void setShamanCounter(int count){
        shamanCounter=count;
    }
    public void setPickerCounter(int count){
        pickerCounter=count;
    }
    public void setPainterCounter(int count){
        painterCounter = count;
    }
    public void setInventorCounter(int count){
        inventorCounter = count;
    }
    public void setBuilderCounter(int count){
        builderCounter = count;
    }

    /**
     * The method changes the player's PP.
     * @param pp new value of PP
     */
    public synchronized void modifyPP(int pp) {
        prestigePoints.addAndGet(pp);
    }

    /**
     * The method changes the player's food adding the amount passed as a parameter ; if the food becomes negative, the difference is paid with PP points
     * (this can only happen if, returning as the last player on the turn order card, no food is available)
     * @param f the amount of food to add
     */
    public synchronized void modifyFood(int f){
        food.addAndGet(f);
        if (food.get() < 0) {
            int PPDebt = food.get();
            modifyPP(PPDebt*2);
            food.set(0);
        }
    }

    public void modifyStarCounter(int bonus){
        starCounter+= bonus ;
    }

    public void increaseBuilderDiscount(int num){
        builderDiscount += num;
    }

    /**
     * The method calculates a player's final PP taking into account the current PP, the buildings, and the
     * bonuses given by the characters
     * @return final PP
     */
    public int finalScore() {
        return prestigePoints.get() + inventorBonus() + painterBonus() + builderBonus() +
               buildingBonus() + buildingMultipliedBonus();
    }

    /**
     * the method scrolls through the player's list of characters, counts the inventors and the number of
     * different icons it finds, and finally multiplies the two values
     * @return inventors' bonus
     */
    public int inventorBonus() {
        List<String> icons = new ArrayList<>();
        CharacterVisitor v = new InventorIconCounter();
        for(Character c : tribeCard){
            boolean trovato=false;
            String currentIcon = null;
            currentIcon=c.isInventorAndGetIcon(v);
            for(int i=0; i<icons.size() && !trovato; i++){
                if(icons.get(i).toLowerCase().equals(currentIcon))
                    trovato=true;
            }
            if(!trovato)
                icons.add(currentIcon);
        }
        return inventorCounter*icons.size();
    }

    /**
     * The method uses countTribeCardsByIcon to find the number of painters in the player's character list and
     * through the formula returns the bonus
     * @return painters' bonus
     */
    public int painterBonus() {
        return (painterCounter/2)*10;
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
    public int buildingMultipliedBonus() {
        int sumPP = 0;

        EndGameVisitorInterface visitor = new EndGameVisitor();

        for (Building b : buildings) {
            sumPP += b.acceptEndGame(visitor, this);
        }

        return sumPP;
    }

    /**
     *
     * @param typeOfCharacter: Inventor's icon
     * wantedIcon is one of the 6 types of character.
     * the method goes through the entire list of the player's characters and counts how many characters of the
     * type wantedIcon are present
     * @return the number of wantedIcon-type characters
     */
    public int countTribeCardsByIcon(String typeOfCharacter){
        Map<String, Integer> cards = new HashMap<>();
        typeOfCharacter = typeOfCharacter.toLowerCase();
        Integer num = null;
        synchronized (countersLock){
            cards.put("hunter", hunterCounter);
            cards.put("builder", builderCounter);
            cards.put("painter", painterCounter);
            cards.put("inventor", inventorCounter);
            cards.put("shaman", shamanCounter);
            cards.put("picker", pickerCounter);
        }
        num = cards.get(typeOfCharacter);
        if(num == null){
            throw new IllegalArgumentException(typeOfCharacter + " is not a valid character");
        }
        return num;
    }

    /**
     * The method through countTribeCardsByIcon counts how many characters of each type are in the player's
     * character list, the number of sets will be the minimum among the numbers found previously
     * @return the number of sets
     */
    public int countSet(){

        int min = inventorCounter;
        if (builderCounter < min) {
            min = builderCounter;
        }
        if (hunterCounter < min) {
            min = hunterCounter;
        }
        if (painterCounter < min) {
            min = painterCounter;
        }
        if (pickerCounter < min) {
            min = pickerCounter;
        }
        if (shamanCounter < min) {
            min = shamanCounter;
        }
        return min;
    }
}
