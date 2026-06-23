package it.polimi.ingsw.UI;

import it.polimi.ingsw.Model.Cards.Buildings.*;
import it.polimi.ingsw.Model.Cards.Characters.*;
import it.polimi.ingsw.Model.Cards.Events.HuntingEvent;
import it.polimi.ingsw.Model.Cards.Events.PaintingEvent;
import it.polimi.ingsw.Model.Cards.Events.ShamanicEvent;
import it.polimi.ingsw.Model.Cards.Events.SustenanceEvent;
import it.polimi.ingsw.Model.Game.OfferCard;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Model.Game.TurnOrderCard;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class Printer  {
    private final int height = 7;
    private final char topLeftCorner = '┌';
    private final char topRightCorner = '┐';
    private final char bottomLeftCorner = '└';
    private final char bottomRightCorner = '┘';
    private final char side = '│';
    private final char top = '─';
    private final char bottom = '─';
    private final char space = ' ';
    private final char upArrow = '↑';
    private final char downArrow = '↓';
    AttributedStyle communicationsStyle = AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW);
    AttributedStyle yellowStyle = AttributedStyle.DEFAULT.foreground(220).bold();
    AttributedStyle redStyle = AttributedStyle.DEFAULT.foreground(202).bold();
    AttributedStyle blackStyle = AttributedStyle.DEFAULT.foreground(242).bold();
    AttributedStyle blueStyle = AttributedStyle.DEFAULT.foreground(38).bold();
    AttributedStyle whiteStyle = AttributedStyle.DEFAULT.foreground(15).bold();
    AttributedStyle msgStyle = AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold();
    AttributedStyle errStyle = AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold();

    public String[] encapsulator(String[] string){
        int max = 0;
        for(int i = 0; i < string.length; i++){
            if(string[i]!=null && string[i].length() > max){
                max = string[i].length();
            }
        }
        //max = max+2;
        for(int i = 0; i < string.length; i++){
            if(i==0){
                string[0] = string[0] + topLeftCorner;
                for(int j = 0; j < max; j++){
                    string[0] = string[0] + top;
                }
                string[0] = string[0] + topRightCorner;
            }else if(i == string.length-1){
                int len = string.length;
                string[len-1] = string[len-1] + bottomLeftCorner;
                for(int j = 0; j < max; j++){
                    string[len-1] = string[len-1] + bottom;
                }
                string[len-1] = string[len-1] + bottomRightCorner;
            }else{
                int length = 0;
                int left = max;
                int right = 0;
                if(string[i]!=null){
                    length = string[i].length();
                    double v = max- length;
                    left = (int)floor(v/2);
                    right = (int)ceil(v/2);
                }

                StringBuilder leftSpace = new StringBuilder();
                StringBuilder rightSpace = new StringBuilder();
                leftSpace.append(String.valueOf(space).repeat(Math.max(0, left)));
                rightSpace.append(String.valueOf(space).repeat(Math.max(0, right)));

                StringBuilder row = new StringBuilder();
                row.append(side);
                row.append(leftSpace);
                row.append(string[i]);
                row.append(rightSpace);
                row.append(side);
                string[i] = row.toString();
            }
        }
        return string;
    }

    public String[] initialize(String[] string){
        for(int i = 0; i < string.length; i++){
            string[i] = "";
        }
        return string;
    }

    public String[] print(Painter painter) {
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "PAINTER";
        return encapsulator(p);
    }

    public String[] print(Inventor inventor) {
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "INVENTOR";
        p[3] = p[3] + "SYMBOL:";
        p[4] = p[4] + inventor.getInventorIcon().toUpperCase();
        return encapsulator(p);
    }

    public String[] print(Hunter hunter) {
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "HUNTER";
        p[3] = p[3] + "SYMBOL:";
        if(hunter.getSymbol()){
            p[4] = p[4] + "YES";
        }else{
            p[4] = p[4] + "NO";
        }
        return encapsulator(p);
    }

    public String[] print(Builder builder) {
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "BUILDER";
        p[3] = p[3] + "DISCOUNT:" + builder.getBuilderDiscount();
        p[5] = p[5] + "PPs:" + builder.getPP();
        return encapsulator(p);
    }

    public String[] print(Picker picker){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "PICKER";
        p[3] = p[3] + "DISCOUNT:";
        p[4] =  p[4] + picker.getDiscount();
        return encapsulator(p);
    }

    public String[] print(Shaman shaman){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "SHAMAN";
        if(shaman.getShamanStars() == 1){
            p[3] = p[3] + shaman.getShamanStars() + " STAR";
        }else {
            p[3] = p[3] + shaman.getShamanStars() + " STARS";
        }
        return encapsulator(p);
    }

    public String[] print(OfferCard offerCard){
        String[] p = new String[height];
        p = initialize(p);
        String occupier;
        if(offerCard.isOccupied()){
            occupier = offerCard.getOccupiedBy().getTotem().toString().toUpperCase();
        }else{
            occupier = "  ";
        }
        String[] string = new String[3];
        string= initialize(string);
        string[1] = occupier;
        string = encapsulator(string);
        for(int j=0; j<string.length; j++){
            p[j+1] = string[j];
        }

        if(offerCard.getID()==1){
            p[4] = p[4] + "FOOD +3";
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            for(int j=0; j< offerCard.getDownArrow(); j++){
                stringBuilder.append(downArrow);
            }
            for(int j=0; j< offerCard.getUpArrow(); j++){
                stringBuilder.append(upArrow);
            }
            p[4] = p[4] + stringBuilder.toString();
        }
        p = encapsulator(p);
        p[2] = p[2].replace(Totem.BLACK.toString(), new AttributedStringBuilder().append(Totem.BLACK.toString(), blackStyle).toAnsi());
        p[2] = p[2].replace(Totem.WHITE.toString(), new AttributedStringBuilder().append(Totem.WHITE.toString(), whiteStyle).toAnsi());
        p[2] = p[2].replace(Totem.ORANGE.toString(), new AttributedStringBuilder().append(Totem.ORANGE.toString(), redStyle).toAnsi());
        p[2] = p[2].replace(Totem.BLUE.toString(), new AttributedStringBuilder().append(Totem.BLUE.toString(), blueStyle).toAnsi());
        p[2] = p[2].replace(Totem.YELLOW.toString(), new AttributedStringBuilder().append(Totem.YELLOW.toString(), yellowStyle).toAnsi());
        return p;
    }

    public String[] print(HuntingEvent huntingEvent){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "HUNTING";
        p[2] = p[2] + "EVENT";
        p[4] =  p[4] + "+1 FOOD AND";
        p[5] = p[5] + "+"+ huntingEvent.getHuEvePP() + "PP EACH";
        return encapsulator(p);
    }

    public String[] print(ShamanicEvent shamanicEvent){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "SHAMANIC";
        p[2] = p[2] + "EVENT";
        p[4] =  p[4] + "MAX:+"+ shamanicEvent.getShEvePrizePoints()+"PP";
        p[5] = p[5] + "MIN:"+ shamanicEvent.getShEvePenPoints()+"PP";
        return encapsulator(p);
    }

    public String[] print(PaintingEvent paintingEvent){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "PAINTING";
        p[2] = p[2] + "EVENT";
        int min = paintingEvent.getPaEveNumMinPainters()-1;
        p[4] = p[4] + "0-"+ min + ": "+ paintingEvent.getPaEvePointsLoss() +"PP";
        p[5] = p[5] + paintingEvent.getPaEveNumMinPainters()+ "+: "+ paintingEvent.getPaEveMultiplierPP() +"PP each";
        return encapsulator(p);
    }

    public String[] print(SustenanceEvent sustenanceEvent){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "SUSTENANCE";
        p[2] = p[2] + "EVENT";
        p[4] = p[4] + "-1 FOOD/";
        p[5] = p[5] + "-"+sustenanceEvent.getSuEvePointsLossMultiplier()+"PP each";
        return encapsulator(p);
    }

    public String[] print(AddCard addCard){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "ADDCARD";
        p[3] = p[3] + upArrow;
        p[4] = p[4] + addCard.getPP()+"PP";
        p[5] = p[5] + "PRICE:"+ addCard.getPrice();
        return encapsulator(p);
    }

    public String[] print(BonusFood bonusFood){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "BONUSFOOD";
        p[4] = p[4] + bonusFood.getPP()+"PP";
        p[5] = p[5] + "PRICE:"+ bonusFood.getPrice();
        return encapsulator(p);
    }

    public String[] print(BonusPPBuilding bonusPPBuilding){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "BONUSPP";
        p[4] = p[4] + bonusPPBuilding.getPP()+"PP";
        p[5] = p[5] + "PRICE:"+ bonusPPBuilding.getPrice();
        return encapsulator(p);
    }

    public String[] print(BonusStarBuilding bonusStarBuilding){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "BONUSSTAR";
        p[3] = p[3] + "+3 STARS";
        p[4] = p[4] + bonusStarBuilding.getPP()+"PP";
        p[5] = p[5] + "PRICE:"+ bonusStarBuilding.getPrice();
        return encapsulator(p);
    }

    public String[] print(DiscountBuilding discountBuilding){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "DISCOUNT";
        switch (discountBuilding.getTypeEvents()){
            case PAINTINGEVENT :
                p[2] = p[2] + "PAINTINGEVENT";
                p[4] = p[4] + "1 FOOD each";
                p[5] = p[5] +discountBuilding.getPP()+"PP / " + "PRICE:" + discountBuilding.getPrice();
                break;
            case SUSTENANCEEVENT :
                p[2] = p[2] + "SUSTENANCEEVENT";
                p[3] = p[3] + "-1 FOOD each";
                p[4] = p[4] + discountBuilding.getTypeIcons().toString().toUpperCase();
                p[5] = p[5] +discountBuilding.getPP()+"PP / " + "PRICE:" + discountBuilding.getPrice();
                break;
            case HUNTEREVENT:
                p[2] = p[2] + "HUNTEREVENT";
                p[4] = p[4] + "+1FOOD & +1PP";
                p[5] = p[5] +discountBuilding.getPP()+"PP / " + "PRICE:" + discountBuilding.getPrice();
                break;
        }
        return encapsulator(p);
    }

    public String[] print(DoubleBonusBuilding doubleBonusBuilding){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "DOUBLEBONUS";
        p[3] =  p[3] + "BONUS x 2";
        p[4] = p[4] + doubleBonusBuilding.getPP()+"PP";
        p[5] = p[5] + "PRICE:"+ doubleBonusBuilding.getPrice();
        return encapsulator(p);
    }

    public String[] print(MultiplicationBuilding multiplicationBuilding){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "MULTIPLICATION";
        p[3]  =  p[3] + multiplicationBuilding.getMultiplier()+"PP x";
        p[4] = p[4] + multiplicationBuilding.getTypeIcons().toUpperCase();
        p[5] = p[5] +multiplicationBuilding.getPP()+"PP / " + "PRICE:" + multiplicationBuilding.getPrice();
        return encapsulator(p);
    }

    public String[] print(MultiplierPPBuilderBuilding building){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "BUILDERPP";
        p[2] = p[2] + "MULTIPLIER";
        p[4] = p[4] + "PP x2 each" ;
        p[5] = p[5] +building.getPP()+"PP / " + "PRICE:" + building.getPrice();
        return encapsulator(p);
    }

    public String[] print(NoMalusBuilding building){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "NO MALUS";
        p[2] =  p[2] + "SHAMANIC";
        p[3] =  p[3] + "EVENT";
        p[5] = p[5] +building.getPP()+"PP / " + "PRICE:" + building.getPrice();
        return encapsulator(p);
    }

    public String[] print(SameIconBuilding building){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "SAME ICON";
        p[3] =  p[3] + "+3FOOD x";
        p[4] = p[4] + "PAIR";
        p[5] = p[5] +building.getPP()+"PP / " + "PRICE:" + building.getPrice();
        return encapsulator(p);
    }

    public String [] print(SetBonus building){
        String[] p = new String[height];
        p= initialize(p);
        p[1] = p[1] + "SET BONUS";
        p[3] =  p[3] + "+6FOOD x";
        p[4] = p[4] + "SET";
        p[5] = p[5] +building.getPP()+"PP / " + "PRICE:" + building.getPrice();
        return encapsulator(p);
    }

    public String[] print(TurnOrderCard turnOrderCard){
        String[] p = new String[height];
        p = initialize(p);

        if(turnOrderCard.getOrder().size() == turnOrderCard.getNumPlayers()){
            for(int i = 0; i < turnOrderCard.getNumPlayers(); i++){
                p[i+1] = p[i+1] + "="+turnOrderCard.getOrder().get(i).getTotem().toString().toUpperCase()+"=";
            }
        }else{
            int delta =  turnOrderCard.getNumPlayers()-turnOrderCard.getOrder().size();

            for(int j=0;j<delta;j++){
                p[j+1] = p[j+1] + "=   =";
            }
            for(int i = 0; i < turnOrderCard.getOrder().size(); i++){
                p[i+delta+1] = p[i+delta+1] + "="+turnOrderCard.getOrder().get(i).getTotem().toString().toUpperCase()+"=";
            }
        }
        return encapsulator(p);
    }

    public String[] printTOC(Totem[] toc){
        String[] p = new String[height];
        p = initialize(p);
        for(int i = 0; i < toc.length; i++){
            if(toc[i] == null){
                p[i+1] = p[i+1] + "=   =";
            }else{
                p[i+1] = p[i+1] + "=" +  toc[i].toString().toUpperCase() + "=";
            }
        }
        p = encapsulator(p);

        for(int i = 0; i < toc.length; i++){
            p[i+1] = p[i+1].replace(Totem.BLACK.toString(), new AttributedStringBuilder().append(Totem.BLACK.toString(), blackStyle).toAnsi());
            p[i+1] = p[i+1].replace(Totem.WHITE.toString(), new AttributedStringBuilder().append(Totem.WHITE.toString(), whiteStyle).toAnsi());
            p[i+1] = p[i+1].replace(Totem.ORANGE.toString(), new AttributedStringBuilder().append(Totem.ORANGE.toString(), redStyle).toAnsi());
            p[i+1] = p[i+1].replace(Totem.BLUE.toString(), new AttributedStringBuilder().append(Totem.BLUE.toString(), blueStyle).toAnsi());
            p[i+1] = p[i+1].replace(Totem.YELLOW.toString(), new AttributedStringBuilder().append(Totem.YELLOW.toString(), yellowStyle).toAnsi());
        }
        return p;
    }



}
