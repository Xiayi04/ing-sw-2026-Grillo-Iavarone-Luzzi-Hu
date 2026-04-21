package it.polimi.ingsw.FileLoaderTest;

import it.polimi.ingsw.FileLoader.BuildingsByEraDTO;
import it.polimi.ingsw.FileLoader.EraDTO;
import it.polimi.ingsw.FileLoader.Loader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoaderTest {

    @Test
    void SearchEra() {
        Loader loader = new Loader();
        EraDTO eraDto = loader.loadFile(1);
        assertEquals(1, eraDto.getEra());
    }

    @Test
    void LoadBuildingsIsRight() {
        Loader loader = new Loader();
        BuildingsByEraDTO buildingsByEraDTO = loader.loadBuildingsByEra();
        assertNotNull(buildingsByEraDTO);
        assertEquals(6, buildingsByEraDTO.getEra1().size());
        assertEquals(7,buildingsByEraDTO.getEra2().size());
        assertEquals(8,buildingsByEraDTO.getEra3().size());
    }

}

