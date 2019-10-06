import java.util.stream.IntStream;

public class Shuffle {
    private Card[] cards;
    private int[] mask; //mask for the cards have already been given to the dealer or player

    public Shuffle(int n) {
        cards = new Card[n * Config.CARDNUM];
        for (int num = 0; num < n; num++) {
            for (int suit = 0; suit < Config.SUITS.length; suit++) {
                for (int number = 1; number <= Config.CARDRANGE; number++) {
                    int index = num * Config.CARDNUM + suit * Config.CARDRANGE + number;
                    cards[index - 1] = new Card(Config.SUITS[suit], Config.NUMBERS[number - 1], number);
                }
            }
        }
        mask = new int[n * Config.CARDNUM];
        for (int i = 0; i < n * Config.CARDNUM; i++)
            mask[i] = 0;
    }

    public void newShuffle() {
        int sum = 0;
        for (int value : mask)
            sum += value;
        if (sum >= Config.CARDNUM - 1){
            for (int i = 0; i < mask.length; i++)
                mask[i] = 0;
        }
    }

    public void giveNewCard(CardPlayer p, int times) {
        // at the start of the game, give the dealer and player two cards
        while (times > 0) {
            times--;
            giveOneCard(p, 0);
        }
    }

    public void giveOneCard(CardPlayer p, int which) {
        // give one card to p
        int a = (int)(Math.random() * (Config.CARDNUM));
        while (mask[a] == 1) {
            a = (int)(Math.random() * (Config.CARDNUM));
        }
        mask[a] = 1;
        p.giveCard(cards[a], which);
    }
}
