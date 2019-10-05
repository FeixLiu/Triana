import java.util.List;

public class TriantaEnaRules implements Rules{
	public TriantaEnaRules() {
		
	}
	public int checkWin() {
		return 0;
	}
	
	public boolean checkBust(CardPlayer P, int which) {
        // check if the hand cards of each person is greater than 21 or not 
		List<HandCard> hc = P.getHandCard();
		int[] v = hc.get(which).getValue();
		for (int i : v) {
			if (i <= 31)
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
			if (total > max && total <= 31)
				max = total;
		}
		return max == 0 ? 32 : max;
	}
	
	public int checkWin(TriantaEnaPlayer P, CardPlayer D, int which) {
		// check who wins
		if (!checkBust(P, which) && !checkBust(D, 0)) {
			if (checkTotal(P, which)>checkTotal(D, 0)) {
				return Config.PLAYERWIN;
			}
			if (checkTotal(P, which)==checkTotal(D, 0)) {
				if (checkTotal(P, which) < 31)
					return Config.DEALERWIN;
				if (P.getHandCard().get(which).isTianHu() && D.getHandCard().get(0).isTianHu())
					return Config.DEALERWIN;
				if (P.getHandCard().get(which).isTianHu())
					return Config.PLAYERWIN;
				if (D.getHandCard().get(0).isTianHu())
					return Config.DEALERWIN;
				if (P.getHandCard().get(which).isTriantaEna() && D.getHandCard().get(0).isTriantaEna())
					return Config.DEALERWIN;
				if (P.getHandCard().get(which).isTriantaEna())
					return Config.PLAYERWIN;
				if (D.getHandCard().get(0).isTriantaEna())
					return Config.DEALERWIN;
				return Config.DEALERWIN;
			}
			else return Config.DEALERWIN;
		}
		else {
			if (checkBust(P, which))
				return Config.DEALERWIN;
			if (checkBust(D, which))
				return Config.PLAYERWIN;
			return Config.DEALERWIN;
		}
	}

	
}
