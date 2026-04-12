package it.polimi.ingsw.FileLoader;
import com.google.gson.Gson;
import java.io.*;

public class Loader {

    /**
     *
     * @param era which deck it's needed to create
     * @return a DTO which contains all the data to use for the creation of the deck for said era
     */
    public EraDTO loadFile(int era){
        Gson gson = new Gson();

        InputStream inputStream = getClass().getResourceAsStream("/era"+era+".json" );

        if(inputStream == null){ throw new RuntimeException("file not found"); }

        Reader reader = new InputStreamReader(inputStream);
        return  gson.fromJson(reader, EraDTO.class);
    }

    /**
     *
     * @return an object which contains  all the data to use for the creation of the buildings for all the eras
     */
    public BuildingsByEraDTO loadBuildingsByEra(){
        Gson gson = new Gson();

        InputStream inputStream = getClass().getResourceAsStream("/BuildingsByEra.json" );

        if(inputStream == null){ throw new RuntimeException("file not found"); }

        Reader reader = new InputStreamReader(inputStream);
        return  gson.fromJson(reader, BuildingsByEraDTO.class);
    }
}
