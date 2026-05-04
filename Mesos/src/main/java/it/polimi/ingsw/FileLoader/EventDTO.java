package it.polimi.ingsw.FileLoader;



public class EventDTO extends CardDTO{
    private String eventName;
    private Integer ShEvePenPoints;
    private Integer ShEvePrizePoints;
    private Integer HuEvePP;
    private Integer PaEveNumMinPainters;
    private Integer PaEveMultiplierPP;
    private Integer PaEvePointsLoss;
    private Integer SuEvePointsLossMultiplier;
    private Boolean FinalEvent;

    public String getEventName() {
            return eventName;
    }

    public Integer getShEvePenPoints() {
        if(ShEvePenPoints != null && isBetween(-7, -3, ShEvePenPoints))
            return ShEvePenPoints;
        throw new IllegalArgumentException("ShEvePenPoints is not valid");
    }

    public Integer getShEvePrizePoints() {
        if(ShEvePrizePoints != null && isBetween(5, 15, ShEvePrizePoints))
            return ShEvePrizePoints;
        throw new IllegalArgumentException("ShEvePrizePoints is not valid");
    }

    public Integer getHuEvePP() {
        if(HuEvePP != null && isBetween(1, 3, HuEvePP))
            return HuEvePP;
        throw new IllegalArgumentException("HuEvePP is not valid");
    }

    public Integer getPaEveNumMinPainters() {
        if(PaEveNumMinPainters != null && isBetween(1, 3, PaEveNumMinPainters))
            return PaEveNumMinPainters;
        throw new IllegalArgumentException("PaEveNumMinPainters is not valid");
    }

    public Integer getPaEveMultiplierPP() {
        if(PaEveMultiplierPP != null && isBetween(1, 3, PaEveMultiplierPP))
            return PaEveMultiplierPP;
        throw new IllegalArgumentException("PaEveMultiplierPP is not valid");
    }

    public Integer getPaEvePointsLoss() {
        if(PaEvePointsLoss != null && PaEvePointsLoss==-2)
            return PaEvePointsLoss;
        throw new IllegalArgumentException("PaEvePointsLoss is not valid");
    }

    public Integer getSuEvePointsLossMultiplier() {
        if(SuEvePointsLossMultiplier != null && isBetween(-3, -1, SuEvePointsLossMultiplier))
            return SuEvePointsLossMultiplier;
        throw new IllegalArgumentException("SuEvePointsLossMultiplier is not valid");
    }

    public Boolean isFinalEvent() {
        if(FinalEvent!= null)
            return FinalEvent;
        throw new IllegalArgumentException("FinalEvent is null");
    }

    /**
     *
     * @param a left endpoint
     * @param b right endpoint
     * @param c number which needs to be checked
     * @return true if c is between a and b, false otherwise
     */
    public static boolean isBetween(Integer a,  Integer b, Integer c){
        if(c>=a&&c<=b)
            return true;
        return false;
    }

}
