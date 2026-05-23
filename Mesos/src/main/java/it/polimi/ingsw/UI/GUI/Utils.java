package it.polimi.ingsw.UI.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Utils {
    public static ImageView createImageView(String path, double height){
        Image image = new Image(new File(path).toURI().toString());
        if(image.isError()){
            System.out.println("errore caricamento immagine "+path);
            image.getException().printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        return imageView;
    }
}
