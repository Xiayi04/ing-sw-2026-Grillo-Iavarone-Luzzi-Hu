package it.polimi.ingsw.Game;

import it.polimi.ingsw.UI.Printer;

import java.io.Serial;
import java.io.Serializable;

//cambiato il tipo di ID
public class OfferCard implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int ID;
    private int UpArrow;
    private int DownArrow;
    private boolean Food;
    private boolean IsOccupied;
    private Player OccupiedBy;

    public OfferCard(int ID, int UpArrow, int DownArrow, boolean Food) {
        this.ID = ID;
        this.UpArrow = UpArrow;
        this.DownArrow = DownArrow;
        this.Food = Food;
        this.IsOccupied = false; //di dafaul la posiz. è libera
        this.OccupiedBy = null;
    }

    public int getID() {
        return ID;
    }

    public int getUpArrow() {
        return UpArrow;
    }

    public int getDownArrow() {
        return DownArrow;
    }

    public boolean isFood() {
        return Food;
    }


    /**
     * The method lets a player occupy an offer card
     * @param player
     */
    public void setOccupiedBy(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (!IsOccupied) {
                this.OccupiedBy = player;
                this.IsOccupied = true;
            }
        }

    public boolean isOccupied() {
        return IsOccupied;
    }

    public Player getOccupiedBy() {
        return OccupiedBy;
    }

    /**
     * the method is used to release an offerCard
     * when a totem is removed from it and moved to the TOC
     */
    public void release(){
        this.OccupiedBy = null;
        this.IsOccupied = false;
    }

    public String[] print(Printer printer){
        return printer.print(this);
    }


}
