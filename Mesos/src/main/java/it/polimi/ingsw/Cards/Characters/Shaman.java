package it.polimi.ingsw.Cards.Characters;

import it.polimi.ingsw.Cards.CardType;

import java.util.ArrayList;

public class Shaman extends Character{
    private final Integer shamanStars;

    public Shaman(int era, CardType cardType, int numPlayers, CharacterType characterType, Integer shamanStars) {
        super(era, cardType, numPlayers, characterType);
        this.shamanStars = shamanStars;
    }
    //metodo getter
    public Integer getShamanStars() { return this.shamanStars; }

    /**
     * @param players is an ArrayList which contains al the players' informations
     * @return  the highest number of stars among the players
     */

    /*public int getMaxStars(ArrayList<Player> players){
        if(players.size()<2 || players == null)
            throw new IllegalArgumentException("players ArrayList is not valid");
        int maxStars = 0;

        for(Player p : players){
            int starCounter = p.getStarCounter();
            if(starCounter> maxStars)
                maxStars = starCounter;
        }
        return maxStars;
    }

    public int getMinStars(ArrayList<Player> players){
        if(players.size()<2 || players == null)
            throw new IllegalArgumentException("players ArrayList is not valid");

        int minStars = 1000;
        int starCounter = 0;
        for(Player p : players){
            starCounter = getStarCounter();
            if(starCounter<minStars)
                minStars = starCounter;
        }
        return minStars;
    }

    public void deliverStars(ArrayList<Player> players){
        if(players.size()<2 || players == null)
            throw new IllegalArgumentException("players ArrayList is not valid");

        int maxStars = getMaxStars(players);
        int minStars = getMinStars(players);
        
        for(Player p : players){
            
            if(p.getStarCounter()==maxStars){
                
            } else if (p.getStarCounter()==minStars) {
                
            }
        }
    }*/

}
