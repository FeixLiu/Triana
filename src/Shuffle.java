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

    public Shuffle() {
        this(1);
    }

    public void newShuffle() {
        int sum = 0;
        for (int value : mask)
            sum += value;
        if (sum >= Config.CARDNUM)
            IntStream.range(0, mask.length).forEach(i -> mask[i] = 0);
    }

    public void giveNewCard(CardPlayer p) {
        // at the start of the game, give the dealer and player two cards
        giveOneCard(p, 0);
        giveOneCard(p, 0);
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

    public void keepGive(TriantaEnaPlayer dealer) {
        // keep give dealer cards if dealer's hand cards' value is less than 17
        while (max(dealer.getHandCard().get(0)) < 27)
            giveOneCard(dealer, 0);
    }

    private int max(HandCard a) {
        // get the max value of the possible value of the hand cards
        int[] value = a.getValue();
        if (value.length == 1)
            return value[0];
        else {
            int b = value[0];
            int c = value[1];
            if (c <= 31)
                return c;
            else
                return b;
        }
    }
}
