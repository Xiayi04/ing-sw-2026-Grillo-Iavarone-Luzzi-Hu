package it.polimi.ingsw.Game;

public class OfferCard {
    private char ID;
    private int UpArrow;
    private int DownArrow;
    private boolean Food;
    private boolean IsOccupied;
    private Totem OccupiedBy;
    private Board board;

    public OfferCard(char ID, int UpArrow, int DownArrow, boolean Food, Board board) {
        this.ID = ID;
        this.UpArrow = UpArrow;
        this.DownArrow = DownArrow;
        this.Food = Food;
        this.board = board;
        this.IsOccupied = false; //di dafaul la posiz. è libera
        this.OccupiedBy = null;
    }

    public char getID() {
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


        // Questo metodo SERVE PER OCCUPARE la carta
        public boolean isOccupied(Totem totem) {   //occupo la posizione
            if (!IsOccupied) {
                this.OccupiedBy = totem; // se non è occupata ci metto il mio totem
                this.IsOccupied = true;  // Dico a tutti che ora è occupata
                return true;             // per dire che l'operazione è riuscita
            }
            return false;                // altrimenti se è gia occupata mi viene detto che qui non posso metterlo
        }

    public void release(){ //metodo per liberare l posizione quando il totem viene rimosso
        this.OccupiedBy = null;
        this.IsOccupied = false;
    }

    public Totem getOccupiedBy() {
        return OccupiedBy;
    }
}
