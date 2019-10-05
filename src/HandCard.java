import java.util.ArrayList;
import java.util.List;

public class HandCard {
    private List<Card> cards;

    public HandCard() {
        cards = new ArrayList<>();
    }

    public int[] getValue() {
        // get all the possible results of the hand cards total value (take ace = 1 or take ace = 11)
        int total = 0;
        int ace = 0;
        for (Card card : cards) {
            if (card.getValue() < 10 && card.getValue() != 1)
                total += card.getValue();
            if (card.getValue() >= 10)
                total += 10;
            if (card.getValue() == 1)
                ace += 1;
        }
        List<Integer> rst = new ArrayList<>();
        if (ace == 0)
            rst.add(total);
        if (ace == 1) {
            rst.add(total + 1);
            rst.add(total + 11);
        }
        if (ace > 1) {
            int left = ace - 1;
            rst.add(total + 1 + 11 * left);
            rst.add(total + 11 + 11 * left);
        }
        int[] temp = new int[rst.size()];
        for (int i = 0; i < rst.size(); i++)
            temp[i] = rst.get(i);
        return temp;
    }

    public boolean isBlackJack() {
        //test whether the hand cards is black jack or not
        if (cards.size() != 2)
            return false;
        Card a = cards.get(0);
        Card b = cards.get(1);
        if (a.getValue() > 1 && a.getValue() <= 10)
            return false;
        if (a.getValue() == 1 && b.getValue() > 10)
            return true;
        return a.getValue() > 10 && b.getValue() == 1;
    }
    
    public boolean isTriantaEna() {
        //test whether the hand cards is black jack or not
        if (cards.size() != 3)
            return false;
        Card a = cards.get(0);
        Card b = cards.get(1);
        Card c = cards.get(2);
        if (a.getValue() > 1 && a.getValue() <= 10)
            return false;
        if (a.getValue() == 1 && b.getValue() > 10 && c.getValue() > 10)
            return true;
        if (a.getValue() > 10 && b.getValue() == 1 && c.getValue() > 10)
            return true;
        if (a.getValue() > 10 && b.getValue() > 10 && c.getValue() == 1)
            return true;
        return false;
    }
    
    public boolean isTianHu() {
    	int total = 0;
    	String temp = new String("");
    	String empty = new String("");
    	for (Card card : cards) {
            total = total + card.getValue();
            if (temp.equals(empty)) {
            	temp = card.getSuit();
            }
            else if(card.getSuit().equals(temp)){
            	temp = card.getSuit();
            }
            else {
            	return false;
            }
        }
    	if (total==14 && !temp.equals(empty)) {
    		return true;
    	}
    	return false;
    }

    public List<Card> getCards() {
        return cards;
    }
}
