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
				List<Card> p = P.getHandCard().get(which).getCards();
				List<Card> d = P.getHandCard().get(which).getCards();
				boolean pt = isTongHuaShun(p);
				boolean dt = isTongHuaShun(d);
				boolean pn = isNatualTriantaEna(p);
				boolean dn = isNatualTriantaEna(d);
				if (pt && dt)
					return Config.DEALERWIN;
				if (pt)
					return Config.PLAYERWIN;
				if (dt)
					return Config.DEALERWIN;
				if (pn && dn)
					return Config.DEALERWIN;
				if (pn)
					return Config.PLAYERWIN;
				if (dn)
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

	public boolean isNatualTriantaEna(List<Card> cards) {
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
		return a.getValue() > 10 && b.getValue() > 10 && c.getValue() == 1;
	}

	public boolean isTongHuaShun(List<Card> cards) {
		int total = 0;
		String temp = "";
		for (Card card : cards) {
			total = total + card.getValue();
			if (temp.equals("")) {
				temp = card.getSuit();
			}
			else if(!card.getSuit().equals(temp)) {
				return false;
			}
		}
		return total == 14;
	}
}
