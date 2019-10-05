import java.util.ArrayList;
import java.util.List;

public class BlackJackPlayer extends CardPlayer{

	private int which = 0;
	private int total = 1;
	private List<Bet> bet = new ArrayList<Bet>();
	private Wallet wallet = new Wallet();
	
	
	public BlackJackPlayer() {}
	
	public BlackJackPlayer(String name) {
		super(name);
	}
	
	public BlackJackPlayer(String name, int money) {
		super(name);
		wallet.setWallet(money);
	}
	
	public int getWhich() {
		return this.which;
	}
	
	public void increaseWhich() {
		this.which++;
	}
	
	public void initWhich() {
		this.which = 0;
	}
	
	public int getTotal() {
		return this.total;
	}
	
	public void initTotal() {
		this.total = 1;
	}
	
	public boolean isOver() {
		return total == which;
	}

	public List<Bet> getBet() {
		return this.bet;
	}
	
	public void printBet() {
		for(Bet b : bet) {
			System.out.print(b.getBet());
		}
		System.out.println();
	}
	
	public Wallet getWallet() {
		return this.wallet;
	}
	
	public void printWallet() {
		System.out.println(wallet.getMoney());
	}
	
	public int takeAction(){
		//for the index hand of card, take the action(hit, stand, split, double up)
		System.out.println("========================================");
		if(handCard.size() > 1) {
			System.out.println(super.getName() + ", please select action for your " + (which+1) + " hand cards: ");
		}
		else {
			System.out.println(super.getName() + ", please select action for your hand cards: ");
		}

		System.out.println(Config.HITACTION + " Hit.");
		System.out.println(Config.STANDACTION+ " Stand.");
		System.out.println(Config.SPLITACTION + " Split.");
		System.out.println(Config.DOUBLEACTION + " Double up.");
		
		int currentMoney = wallet.getMoney();
		
		while(true) {
			int action = Utils.getNumberFromPlayer();
			while (action != Config.HITACTION && action != Config.STANDACTION
					&& action != Config.SPLITACTION && action != Config.DOUBLEACTION) {
				System.out.print("Please choose a correct action: ");
				action = Utils.getNumberFromPlayer();
			}
			
			switch (action) {
				case Config.HITACTION:
					return Config.HITACTION;
				case Config.STANDACTION: {
					which++;
					return Config.STANDACTION;
				}
				case Config.SPLITACTION:{
					if(bet.size() != 1 || currentMoney < bet.get(0).getBet()
							|| handCard.size() != 1
							|| handCard.get(0).getCards().size() != 2
							|| !(handCard.get(0).getCards().get(0).getValue() == handCard.get(0).getCards().get(1).getValue())){
						System.out.print(super.getName() + " cannot take this action, please choose again:");
						break;
					}
					else {
						Bet bet1 = bet.get(0);
						Bet bet2 = new Bet(bet1.getBet());
						bet.add(bet2);
						HandCard handCardList1 = handCard.get(0);
						Card handCard2 = handCardList1.getCards().get(1);
						HandCard handCardList2 = new HandCard();
						handCardList2.getCards().add(handCard2);
						handCardList1.getCards().remove(1);
						handCard.add(handCardList2);
						wallet.setWallet(currentMoney - bet2.getBet());
						total++;
						return Config.SPLITACTION;
					}
				}
				case Config.DOUBLEACTION: {
					int size = bet.size();
					//this shouldn't happen!!!
					if(!(which >= 0 && which < size)){
						System.out.println("wrong index1");
						return -1;
					}
					
					if(currentMoney < bet.get(which).getBet()) {
						System.out.print(super.getName() + " cannot take this action, please choose again:");
						break;
					}
					else {
						int betNum = bet.get(which).getBet();
						bet.get(which).setBet(2 * betNum);
						wallet.setWallet(currentMoney - betNum);
						return Config.DOUBLEACTION;
					}
				}
				default: {
					break;
				}
			}
		}
	}
	
	public boolean makeBet(){
		//make bet at the beginning of the game
		bet.clear();
		int currentMoney = wallet.getMoney();
		if(currentMoney < Config.MINBET) {
			System.out.println(super.getName() + " don't have enough money!!");
			return false;
		}
		System.out.print(super.getName() + ", please input the money you want to bet: ");
		
		int money = Utils.getNumberFromPlayer();
		while(money > currentMoney || money > Config.MAXBET || money < Config.MINBET) {
			if(money > currentMoney) {
				System.out.print("The bet should be less than player's total money, input again:");
			}
			else {
				System.out.println("Error: The bet should be more than " + Config.MINBET + " and less than " + Config.MAXBET);
				System.out.print("Please input again:");
			}
			
			money = Utils.getNumberFromPlayer();
		}
		
		bet.add(new Bet(money));
		wallet.setWallet(currentMoney - money);

		System.out.print(super.getName() + "'s bet:\t");
		printBet();
		System.out.print(super.getName() + "'s wallet:\t");
		System.out.println(wallet.getMoney());
		return true;
		
	}
	
	public void endGame(int result) {
		//get the result of the index hand of card
		switch(result) {
			case Config.PLAYERWIN: {
				if(handCard.size() > 1) {
					System.out.println(super.getName() + "'s handcards " + (which+1) + ": Win!");
				}
				else {
					System.out.println(super.getName() + ": Win!");
				}
				wallet.winMoney(2*bet.get(which).getBet());
				break;
			}
			case Config.DEAL: {
				if(handCard.size() > 1) {
					System.out.println(super.getName() + "'s handcards " + (which+1) + ": End in a tie!");
				}
				else {
					System.out.println(super.getName() + ": End in a tie!");
				}
				wallet.winMoney(bet.get(which).getBet());
				break;
			}
			case Config.DEALERWIN: {
				if(handCard.size() > 1) {
					System.out.println(super.getName() + "'s handcards " + (which+1) + ": Lose!");
				}
				else {
					System.out.println(super.getName() + ": Lose!");
				}
				break;
			}
			case Config.BUST: {
				System.out.println("BUST!!!!");
				break;
			}
			//this shouldn't happen!!!
			default: {
				System.out.println("wrong result!!");
				break;
			}
		}
		which++;
	}
}

