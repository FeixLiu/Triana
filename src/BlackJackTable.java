import java.util.ArrayList;
import java.util.List;

public class BlackJackTable implements Table {
    private Shuffle shuffle;
    private BlackJackRules check;
    private List<BlackJackPlayer> players;
    private BlackJackPlayer dealer;
    private int playerNum;
    private boolean computer;
    private boolean flag;

    public BlackJackTable() {
    	System.out.println("Welcome to the BlackJack game.");
    	System.out.println("The objective of the game is to accumulate a hand of cards that equals 21.");
    	System.out.println("Or a hand that has a card value greater than your opponents without exceeding 21.");
        int playerNum = 11;
        while (playerNum > Config.MAXPLAYER) {
            System.out.print("How many players in the game? ");
            playerNum = Utils.getNumberFromPlayer();
            this.playerNum = playerNum;
        }
        int all = playerNum;
    	players = new ArrayList<>();
        shuffle = new Shuffle(Config.CARDSET);
        check = new BlackJackRules();
        String str;
        List<String> allName = new ArrayList<>();
        for (int i = 0; i < playerNum; i++) {
            System.out.print("Please input the name of person " + (i + 1) + ".");
            str = Utils.getName();
            allName.add(str);
        }
        // random banker
        int random = (int)(Math.random() * all) - 1;
        for (int i = 0; i < playerNum; i++) {
            if (i == random) {
                dealer = new BlackJackPlayer(allName.get(i), Config.BANKER, Config.DEFAULTMONEY);
                System.out.println(allName.get(i) + " you are the banker.");
            }
            else
                players.add(new BlackJackPlayer(allName.get(i), Config.PLAYER, Config.DEFAULTMONEY));
        }
        if (this.playerNum <= 0)
            System.out.println("See you");
        computer = false;
        flag = true;
    }

    public void playGame() {
        // the body of the game, ask the player for its choice and ask the checker check whether the player bust or not for each round
        List<BlackJackPlayer> playing = new ArrayList<>();
        List<BlackJackPlayer> delete = new ArrayList<>();
        while (flag && players.size() != 0) {
            playerNum = players.size();
            shuffle.newShuffle();
            shuffle.giveOneCard(dealer,0);
            dealer.printHandCard(true);
            for (BlackJackPlayer player : players) {
                shuffle.giveOneCard(player, 0);
            }
            for (BlackJackPlayer player : players) {
                for (BlackJackPlayer other : players) {
                    if (player.equals(other))
                        other.printHandCard(true);
                    else
                        other.printHandCard(false);
                }
                if (player.makeBet() == Config.NOENOUGHMONEY) {
                    if (!delete.contains(player))
                        delete.add(player);
                    if (delete.size() == players.size())
                        break;
                }
                else {
                    playing.add(player);
                }
            }
            for (BlackJackPlayer player : playing) {
                while (true) {
                    int action = player.takeAction();
                    if (action == Config.HITACTION) {
                        if (!hitAction(player))
                            break;
                    }
                    else if (action == Config.SPLITACTION) {
                        print(player);
                    }
                    else if (action == Config.STANDACTION) {
                        // standAction(player);
                        break;
                    }
                    else if (action == Config.DOUBLEACTION) {
                        if (!hitAction(player))
                            break;
                        player.increaseWhich();
                        if (standAction(player) == 1)
                            break;
                    }
                }
            }
            players.removeAll(delete);
            delete.clear();
            if (playing.size() != 0) {
                shuffle.keepGive(dealer);
                dealer.printHandCard(true);
                for (BlackJackPlayer player : playing)
                    player.printHandCard(true);
                for (BlackJackPlayer player : playing)
                    for (int i = 0; i < player.getHandCard().size(); i++)
                        player.endGame(check.checkWin(player, dealer, i));
                List<BlackJackPlayer> temp = new ArrayList<>();
                for (BlackJackPlayer player : playing) {
                    System.out.print(player.getNickName());
                    System.out.print("Do you want to play another game? ");
                    char c = Utils.yesOrNo();
                    if (c != 'y' && c != 'Y') {
                        temp.add(player);
                        System.out.print(player.getNickName() + "'s balance in wallet is: ");
                        System.out.println(player.getWallet().getMoney());
                    }
                }
                for (BlackJackPlayer out : temp)
                    players.remove(out);
            }
            playing.clear();
        }
        if (playerNum > 0)
            printResult();
    }

    private boolean hitAction(BlackJackPlayer player) {
        shuffle.giveOneCard(player, player.getWhich());
        print(player);
        if (check.checkBust(player, player.getWhich())) {
            player.endGame(Config.BUST);
            return !player.isOver();
        }
        return true;
    }

    public void printResult() {
        for (BlackJackPlayer player : players) {
            System.out.print(player.getNickName() + "'s balance in wallet is: ");
            System.out.println(player.getWallet().getMoney());
        }
    }

    private void print(BlackJackPlayer p) {
        //give one card to the player and then print the hand cards
        dealer.printHandCard(true);
        p.printHandCard(true);
    }

    private int standAction(BlackJackPlayer p) {
        // after the player stand, use this method to do the following work
        if (!p.isOver())
            return 0;
        shuffle.keepGive(dealer);
        computer = true;
    }
}
