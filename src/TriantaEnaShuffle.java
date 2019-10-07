public class TriantaEnaShuffle extends Shuffle {
	//special shuffle for Trianta Ena game
    private int dealerMin;
    private int upbound;

    public TriantaEnaShuffle(int cardSet, int dealerMin, int upbound) {
        super(cardSet);
        this.dealerMin = dealerMin;
        this.upbound = upbound;
    }

    public void keepGive(TriantaEnaPlayer dealer) {
        // keep give dealer cards if dealer's hand cards' value is less than 27
        while (max(dealer.getHandCard().get(0)) <= dealerMin)
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
            if (c <= upbound)
                return c;
            else
                return b;
        }
    }
}
