package it.polimi.ingsw.UI.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public class Utils {
    public static ImageView createImageView(String path, double height){
        InputStream is = Utils.class.getResourceAsStream(path);
        if (is == null){
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        Image image = new Image(Objects.requireNonNull(is));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        return imageView;
    }
}
