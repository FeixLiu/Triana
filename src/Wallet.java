
public class Wallet {
	// set wallet for player or dealer
	private int money;
	
	public Wallet() {
		money = Config.DEFAULTMONEY;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public void setWallet(int num) {
		this.money = num;
	}
	
	public void winMoney(int num) {
		this.money = this.money + num;
	}
	
	public void loseMoney(int num) {
		this.money = this.money - num;
	}
	
	public void printMoney() {
		System.out.println(this.money);
	}
}
