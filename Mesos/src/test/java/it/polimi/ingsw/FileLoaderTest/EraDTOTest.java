package it.polimi.ingsw.FileLoaderTest;

import it.polimi.ingsw.FileLoader.CharacterDTO;
import it.polimi.ingsw.FileLoader.EraDTO;
import it.polimi.ingsw.FileLoader.EventDTO;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.FileLoader.Loader;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EraDTOTest {
    @Test
    void threeShouldReturnEra() {
        Loader loader = new Loader();
        EraDTO eraDTO = loader.loadFile(3);
        assertNotNull(eraDTO);
        assertEquals(3, eraDTO.getEra());
    }
    @Test
    void zeroShouldReturnIllegalArgumentException(){
        EraDTO eraDTO = new EraDTO();
        assertThrows(IllegalArgumentException.class, ()->{
            eraDTO.getEra();
        });
    }
    @Test
       void characterShouldBeInList(){
        Loader loader = new Loader();
        EraDTO eraDTO = loader.loadFile(2);
        ArrayList<CharacterDTO> characters = eraDTO.getCharactersArray();
        assertNotNull(characters);
        assertEquals(28, characters.size());
    }
    @Test
    void eventsShouldBeInList(){
        Loader loader = new Loader();
        EraDTO eraDTO = loader.loadFile(3);
        ArrayList<EventDTO> event = eraDTO.getEventsArray();
        assertNotNull(event);
        assertEquals(2, event.size());
    }


}
