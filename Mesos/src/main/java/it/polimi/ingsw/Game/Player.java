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
    private ArrayList<tribeCard> card;
    private ArrayList<building> Building;
    private int starCounter;

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
    public ArrayList<tribeCard> getCard(){
        return card;
    }
    public ArrayList<building> getBuilding(){
        return building;
    }

    public int getStaCounter(){
        return starCounter;
    }


    public void modifyPP() {

    }

    // su uml non abbiamo scritto niente
    public void modifyFood() {

    }

    public int finalScore() {  //devo scrivere ancora da cosa è costituito
        return finalScore;
    }

    public int inventorBonus() {


    }

    public int painterBonus() {

    }

    public int builderBonus() {

    }
    //metodo per contare icon



    public int buildingBonus(){

    }

    public int buildingMultipliedBonus(){

    }

    public int countTribeCardsByIcon(Icons wantedIcon){
        int count = 0;

        if (card.getCardType() == CardType.CHARACTER) {
            Character character = (Character) card;

            if (wantedIcon.name().equals(character.getCharacterType().name())) {
                count++;
            }
        }
        return count;
    }



    }





    //mi servono tutti i metodi getter







}
