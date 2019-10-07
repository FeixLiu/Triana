public class Card {
	//define a set of cards
    private String suit;
    private String number;
    private int value;

    public Card(String suit, String number, int value) {
        this.suit = suit;
        this.number = number;
        this.value = value;
    }

    public Card() {
        System.out.println("No default constructor, please call Card(String, int).");
    }

    public String getSuit() {
        return suit;
    }

    public String getNumber() {
        return number;
    }

    public int getValue() {
        return value;
    }
}
