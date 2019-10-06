import java.util.ArrayList;
import java.util.List;

public class HandCard {
    protected List<Card> cards;

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

    public List<Card> getCards() {
        return cards;
    }
}
