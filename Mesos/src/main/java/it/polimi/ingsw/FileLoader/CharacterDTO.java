package it.polimi.ingsw.FileLoader;
import it.polimi.ingsw.Model.Cards.Characters.CharacterType;

public class CharacterDTO extends CardDTO {
    private CharacterType characterType;
    private int NumPlayers;
    private String InventorIcon;
    private Integer BuilderDiscount;
    private Integer BuilderPP;
    private boolean HunterSymbol;
    private Integer ShamanStars;

    public String getCharacterType() { return characterType.toString();}
    public int getNumPlayers() { return NumPlayers; }
    public String getInventorIcon() { return InventorIcon; }
    public Integer getBuilderDiscount() { return BuilderDiscount; }
    public Integer getBuilderPP() { return BuilderPP; }
    public boolean getHunterSymbol() { return HunterSymbol; }
    public Integer getShamanStars() { return ShamanStars; }


}
