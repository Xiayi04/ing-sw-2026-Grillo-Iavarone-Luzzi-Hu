package it.polimi.ingsw.FileLoader;
import com.google.gson.Gson;
import java.io.*;

public class Loader {

    public EraDTO loadFile(int era){
        Gson gson = new Gson();

        InputStream inputStream = getClass().getResourceAsStream("/era"+era+".json" );

        if(inputStream == null){ throw new RuntimeException("file not found"); }

        Reader reader = new InputStreamReader(inputStream);
        return  gson.fromJson(reader, EraDTO.class);
    }
}
