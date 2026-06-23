package it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor;

import it.polimi.ingsw.Model.Cards.Characters.*;
import javafx.scene.layout.VBox;

import java.util.Map;

public class GuiCharacterVisitor extends AbstractGuiVisitor{

    private final Map<Class, VBox> boxes;

    public GuiCharacterVisitor(Map<Class, VBox> boxes) {
        this.boxes = boxes;
    }

    @Override
    public VBox visit(Inventor i) {
        return boxes.get(Inventor.class);
    }

    public VBox visit(Hunter h) {
        return boxes.get(Hunter.class);
    }

    public VBox visit(Painter pa) {
        return boxes.get(Painter.class);
    }

    public VBox visit(Builder b) {
        return boxes.get(Builder.class);
    }

    public VBox visit(Shaman s){
        return boxes.get(Shaman.class);
    }

    public VBox visit(Picker pi){
        return boxes.get(Picker.class);
    }
}
