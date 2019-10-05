
public class Config {

	public static final int MAXBET = 100;
	public static final int MINBET = 10;
	
	public static final int HITACTION = 1;
	public static final int STANDACTION = 2;
	public static final int SPLITACTION = 3;
	public static final int DOUBLEACTION = 4;
	
	public static final int PLAYERWIN = 1;
	public static final int DEAL = 0;
	public static final int DEALERWIN = -1;
	public static final int BUST = 2;

	public static final int MAXPLAYER = 10;
	public static final int MINPLAYER = 2;

	public static final int CARDSET = 2;
	public static final int CARDNUM = 52;
	public static final int CARDRANGE = 13;
	public static final String[] SUITS = {"Diamond", "Plum", "Heart", "Spade"};
	public static final String[] NUMBERS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	
	public static final int PLAYER = 0;
	public static final int BANKER = 1;
	
	public static final int NOENOUGHMONEY = 0;
	public static final int NOTMAKEBET = 1;
	public static final int MAKEBET = 2;

	public static final int DEFAULTMONEY = 200;
	public static final int DEALERDEFAULTMONEY = 600;
}
