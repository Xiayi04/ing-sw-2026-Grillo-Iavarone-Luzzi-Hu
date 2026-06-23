package it.polimi.ingsw.UI.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public class Utils {
    /**
     * The method retrieves the correct image from the resources folder and displays it
     * @param path path where the image to show is located
     * @param height image height
     * @return the imageview already done
     */
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
