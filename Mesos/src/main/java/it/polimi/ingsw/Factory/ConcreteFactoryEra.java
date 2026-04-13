package it.polimi.ingsw.Factory;

import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Cards.Events.*;
import it.polimi.ingsw.Cards.CardType;
import it.polimi.ingsw.FileLoader.*;

import java.util.ArrayList;
import java.util.List;

public class ConcreteFactoryEra implements Factory{

    private final int era;

    public ConcreteFactoryEra(int era){
        this.era=era;
    }

    public Character createInventor(int era, int numPlayers, String typeIcon){
        return new Inventor(era, CardType.CHARACTER, numPlayers, CharacterType.INVENTOR, typeIcon);
    }

    public Character createHunter(int era, int numPlayers, boolean symbol){
        return new Hunter(era, CardType.CHARACTER, numPlayers, CharacterType.HUNTER, symbol);
    }

    public Character createBuilder(int era, int numPlayers, int discount, int pp){
        return new Builder(era, CardType.CHARACTER, numPlayers, CharacterType.BUILDER, discount, pp);
    }

    public Character createPainter(int era, int numPlayers){
        return new Painter(era, CardType.CHARACTER, numPlayers, CharacterType.PAINTER);
    }

    public Character createShaman(int era, int numPlayers, int shamanStars){
        return new Shaman(era, CardType.CHARACTER, numPlayers, CharacterType.SHAMAN, shamanStars);
    }

    public Character createPicker(int era, int numPlayers){
        return new Picker(era, CardType.CHARACTER, numPlayers, CharacterType.PICKER);
    }

    public Event createShamanicEvent(int era, int penPoints, int prizePoints){
        return new ShamanicEvent(era, CardType.EVENT, EventName.SHAMANIC_EVENT, penPoints, prizePoints);
    }

    public Event createHuntingEvent(int era, int HuEvePP){
        return new HuntingEvent(era, CardType.EVENT, EventName.HUNTING_EVENT, HuEvePP);
    }

    public Event createPaintingEvent(int era, int minPainters, int multiplierPP, int pointsLoss){
        return new PaintingEvent(era, CardType.EVENT, EventName.PAINTING_EVENT, minPainters, multiplierPP, pointsLoss);
    }

    public Event createSustenanceEvent(int era,  int pointsLossMultiplier){
        return new SustenanceEvent(era, CardType.EVENT, EventName.SUSTENANCE_EVENT, pointsLossMultiplier);
    }

    /**
     * the method through the loader and the getter methods of EraDTO derives the array composed of elements of
     * type CharacterDTO. through the getter methods of CharacterDTO it obtains the useful values to create all
     * types of character.
     * @return a list of characters, all from a specific era
     */
    public ArrayList<Character> createCharacterList(){

        Loader loader = new Loader();
        EraDTO eraDTO = loader.loadFile(era);
        ArrayList<Character> characters = new ArrayList<>();

        for(CharacterDTO c : eraDTO.getCharactersArray()){
            int numPlayer = c.getNumPlayers();
            switch(c.getCharacterType().toLowerCase()) {
                case "inventor":
                    String icon = c.getInventorIcon();
                    characters.add(createInventor(era, numPlayer, icon));
                    break;
                case "hunter":
                    boolean symbol = c.getHunterSymbol();
                    characters.add(createHunter(era, numPlayer, symbol));
                    break;
                case "builder":
                    int discount = c.getBuilderDiscount();
                    int pp = c.getBuilderPP();
                    characters.add(createBuilder(era, numPlayer, discount, pp));
                    break;
                case "painter":
                    characters.add(createPainter(era, numPlayer));
                    break;
                case "shaman":
                    int shamanStars = c.getShamanStars();
                    characters.add(createShaman(era, numPlayer, shamanStars));
                    break;
                case "picker":
                    characters.add(createPicker(era, numPlayer));
                    break;
            }
        }
        return characters;
    }

    /**
     * the method through the loader and the getter methods of EraDTO derives the array composed of elements of
     * type EventDTO. through the getter methods of EventDTO it obtains the useful values to create all
     * types of events.
     * @return a list of events, all from a specific era
     */
    public ArrayList<Event> createEventList(){

        Loader loader = new Loader();
        EraDTO eraDTO = loader.loadFile(era);
        ArrayList<Event> events = new ArrayList<>();

        for(EventDTO e : eraDTO.getEventsArray()){
            switch(e.getEventName().toLowerCase()){
                case "shamanic_event":
                    int penPoints = e.getShEvePenPoints();
                    int prizePoints = e.getShEvePrizePoints();
                    events.add(createShamanicEvent(era, penPoints, prizePoints));
                    break;
                case "hunting_event":
                    int huEvePP = e.getHuEvePP();
                    events.add(createHuntingEvent(era, huEvePP));
                    break;
                case "painting_event":
                    int minPainters = e.getPaEveNumMinPainters();
                    int multiplierPP = e.getPaEveMultiplierPP();
                    int pointLossPP = e.getPaEvePointsLoss();
                    events.add(createPaintingEvent(era, minPainters, multiplierPP, pointLossPP));
                    break;
                case "sustenance_event":
                    int pointLossMultiplier = e.getSuEvePointsLossMultiplier();
                    events.add(createSustenanceEvent(era, pointLossMultiplier));
                    break;
            }
        }
        return events;
    }
}
