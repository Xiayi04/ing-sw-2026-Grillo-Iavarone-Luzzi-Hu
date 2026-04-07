package it.polimi.ingsw.FileLoader;

import java.util.ArrayList;

public class EraDTO {
    private int era;
    private ArrayList<CharacterDTO> characters;
    private ArrayList<EventDTO> events;

    public int getEra() {
        if(era>0 && era<=4)
            return era;
        throw new IllegalArgumentException("Era out of bounds");
    }

    public ArrayList<CharacterDTO> getCharactersArray() {
        if(characters!=null)
            return characters;
        throw new IllegalArgumentException("Characters array is null");
    }

    public ArrayList<EventDTO> getEventsArray() {
        if(events!=null)
            return events;
        throw new IllegalArgumentException("Events array is null");
    }
}
