package it.polimi.ingsw.Game;
import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Buildings.MultiplicationBuilding;
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
    public int getStaCounter(){
        return starCounter;
    }

    public void modifyPP(int pp){
        prestigePoints+=pp;
    }

    public void modifyFood(int f){
        food+=f;
    }

    public void modifyStarCounter(int bonus){
        starCounter+= bonus ;
    }

    public int finalScore() {  //devo scrivere ancora da cosa è costituito
        return prestigePoints + inventorBonus() + painterBonus() + builderBonus() +
               buildingBonus() + buildingMultipliedBonus();
    }

    public int inventorBonus() {
        List<String> icons = new ArrayList<>();
        int numInventor = 0;
        boolean trovato=false;
        for(Character c : tribeCard){
            if(c instanceof Inventor inventor){
                numInventor++;
                String currentIcon = inventor.getInventorIcon().toLowerCase();
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

    public int painterBonus() {
        return (countTribeCardsByIcon(Icons.PAINTER)/2)*10;
    }

    public int builderBonus() {
        int sumPP = 0;
        for(Character c : tribeCard){
            if(c instanceof Builder b){
                int PP = b.getPP();
                sumPP+=PP;
            }
        }
        return sumPP;
    }

    public int buildingBonus(){
        int sumPP = 0;
        for(Building b : buildings){
            sumPP+=b.getPP();
        }
        return sumPP;
    }

    public int buildingMultipliedBonus(){
        int sumPP = 0;
        for(Building b : buildings){
            if(b instanceof MultiplicationBuilding ed)
                sumPP += ed.countPP(this);
        }
        return sumPP;
    }

    public int countTribeCardsByIcon(Icons wantedIcon){
        int count = 0;
        for(Character c : tribeCard){
            if(c.getCharacterType().toString().equals(wantedIcon.toString()))
                count++;
        }
        return count;
    }

    //l'ho creato perché mi serve per il SET in building, uso il metodo sopra per calcolare ogni personaggio
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
