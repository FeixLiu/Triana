import java.util.List;

public class BlackJackRules implements Rules{
	public BlackJackRules() {
		
	}
	public int checkWin() {
		return 0;
	}
	
	public boolean checkBust(CardPlayer P, int which) {
        // check if the hand cards of each person is greater than 21 or not 
		List<HandCard> hc = P.getHandCard();
		int[] v = hc.get(which).getValue();
		for (int i : v) {
			if (i <= 21)
				return false;
		}
		return true;
	}
	
	private int checkTotal(CardPlayer P, int which) {
		// calculate the total value of a set of hand cards
		List<HandCard> hc = P.getHandCard();
		int[] v = hc.get(which).getValue();
		int max = 0;
		for (int total : v) {
			if (total > max && total <= 21)
				max = total;
		}
		return max == 0 ? 22 : max;
	}
	
	public int checkWin(BlackJackPlayer P, CardPlayer D, int which) {
		// check who wins
		if (!checkBust(P, which) && !checkBust(D, 0)) {
			if (checkTotal(P, which)>checkTotal(D, 0)) {
				return Config.PLAYERWIN;
			}
			if (checkTotal(P, which)==checkTotal(D, 0)) {
				if (checkTotal(P, which) < 21)
					return Config.DEAL;
				if (P.getHandCard().get(which).isBlackJack() && D.getHandCard().get(0).isBlackJack())
					return Config.DEAL;
				if (P.getHandCard().get(which).isBlackJack())
					return Config.PLAYERWIN;
				if (D.getHandCard().get(0).isBlackJack())
					return Config.DEALERWIN;
				return Config.DEAL;
			}
			else return Config.DEALERWIN;
		}
		else {
			if (checkBust(P, which))
				return Config.DEALERWIN;
			if (checkBust(D, which))
				return Config.PLAYERWIN;
			return Config.DEAL;
		}
	}

	
}
