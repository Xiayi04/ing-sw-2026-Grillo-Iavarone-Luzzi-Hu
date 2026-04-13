package it.polimi.ingsw.Game;
//cambiato il tipo di ID
public class OfferCard {
    private int ID;
    private int UpArrow;
    private int DownArrow;
    private boolean Food;
    private boolean IsOccupied;
    private Totem OccupiedBy;

    public OfferCard(char ID, int UpArrow, int DownArrow, boolean Food) {
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


        // VERIFICO SE LA POSIZONE è LIBERA
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
