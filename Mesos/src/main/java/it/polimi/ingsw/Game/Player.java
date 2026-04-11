package it.polimi.ingsw.Game;
import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Buildings.Icons;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.Cards.Characters.Character;

import java.util.ArrayList;



public class Player {
    private String name;
    private Totem totem;
    private int food;
    private int prestigePoints;
    private ArrayList<Card> tribeCard;
    private ArrayList<Building> building;
    private int starCounter;
    //metodo cpstruttore
    public Player(String name, Totem totem, int food, int prestigePoints,  ArrayList<Card> tribeCard,ArrayList<Building> Building, int starCounter ){
        this.name = name;
        this.totem = totem;
        this.food = food;
        this.prestigePoints = prestigePoints;
        this.tribeCard = tribeCard;
        this.building = building;
        this.starCounter = starCounter;

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
    public ArrayList<Card> getTribeCard(){
        return tribeCard;
    }
    public ArrayList<Building> getBuilding(){
        return building;
    }
    public int getStaCounter(){
        return starCounter;
    }

/*


    public void modifyPP() {

    }


    // su uml non abbiamo scritto niente
    public void modifyFood() {

    }

    public void finalScore() {  //devo scrivere ancora da cosa è costituito

    }

    public int inventorBonus() {


    }

    public int painterBonus() {

    }

    public int builderBonus() {

    }

    public void modifyStarCounter(int bonus ){
        this.starCounter += bonus ;



    }



    public int buildingBonus(){

    }

    public int buildingMultipliedBonus(){

    }

    public int countTribeCardsByIcon(Icons wantedIcon){
        int count = 0;

        for (Card c : tribeCard) {
            if (c.getCardType() == CardType.CHARACTER) {
                Character character = (Character) c;

                if (wantedIcon.name().equals(character.getCharacterType().name())) {
                    count++;
                }
            }
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


















*/

}
