import java.util.List;

public class BlackJackDealer extends CardPlayer{
    public BlackJackDealer(String name) {
        super(name);
    }

    public BlackJackDealer() {
        super("Computer");
    }
    
    public void printDealerHandCard() {
		List<Card> card = handCard.get(0).getCards();
		System.out.print(super.getName() + "'s handcards: " + "(" + card.get(0).getSuit() + ")" + card.get(0).getNumber() + " ");
		System.out.println("*");
	}
}
