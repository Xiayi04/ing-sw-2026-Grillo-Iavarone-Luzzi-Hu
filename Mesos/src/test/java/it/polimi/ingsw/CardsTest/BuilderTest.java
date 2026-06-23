package it.polimi.ingsw.CardsTest;

import com.sun.source.tree.BreakTree;
import it.polimi.ingsw.Model.Cards.Characters.*;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.AbstractGuiVisitor;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.CharacterVisitor;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.GuiCharacterVisitor;
import it.polimi.ingsw.Model.Cards.Characters.CharacterVisitor.GuiVisitor;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuilderTest {
      @Test
      void addCardTest(){
          Builder builder = new Builder(1, "CHARACTER",2, "BUILDER", 3, 5);
          Player player = new Player("p1", Totem.BLACK, 0, null);

          final boolean [] visited = {false};
          CharacterVisitor visitor = new CharacterVisitor() {
              @Override
              public String visit(Inventor inventor) {
                  return "";
              }

              @Override
              public String visit(Builder builder) {
                  return "";
              }

              @Override
              public String visit(Shaman shaman) {
                  return "";
              }

              @Override
              public String visit(Picker picker) {
                  return "";
              }

              @Override
              public String visit(Hunter hunter) {
                  return "";
              }

              @Override
              public String visit(Painter painter) {
                  return "";
              }

              @Override
              public boolean visit(Inventor inventor, Player player) {
                  return false;
              }

              @Override
              public boolean visit(Builder builder, Player player) {
                  visited[0] = true;
                  return false;
              }

              @Override
              public boolean visit(Shaman shaman, Player player) {
                  return false;
              }

              @Override
              public boolean visit(Picker picker, Player player) {
                  return false;
              }

              @Override
              public boolean visit(Hunter hunter, Player player) {
                  return false;
              }

              @Override
              public boolean visit(Painter painter, Player player) {
                  return false;
              }
          };
          builder.addCard(visitor,player);
          assertTrue(visited[0]);

      }

    @Test
    void ImagePathTest() {
        Builder builder = new Builder(1, "CHARACTER", 2, "BUILDER", 3, 5);

        String result = builder.getImagePath();

        assertEquals("/images/cards/characters/builder_5_3.png", result);
    }

}
