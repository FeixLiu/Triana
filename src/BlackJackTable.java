import java.util.ArrayList;
import java.util.List;

public class BlackJackTable implements Table {
    private Shuffle shuffle;
    private BlackJackRules check;
    private List<BlackJackPlayer> players;
    private BlackJackDealer dealer;
    private int playerNum;
    private boolean computer;

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
        while (playerNum > 0) {
            System.out.print("The information of player " + (all - playerNum + 1) + ". ");
            str = Utils.getName("player");
            players.add(new BlackJackPlayer(str, Config.DEFAULTMONEY));
            playerNum--;
        }
        boolean isMan = Utils.realMan(); //ask whether the dealer is a real man or not
        if (isMan) {
            str = Utils.getName("dealer");
            dealer = new BlackJackDealer(str);
        }
        else
            dealer = new BlackJackDealer();
        if (this.playerNum <= 0) {
            System.out.println("See you");
        }
        computer = false;
    }

    public void playGame() {
        // the body of the game, ask the player for its choice and ask the checker check whether the player bust or not for each round
        while (players.size() != 0) {
            playerNum = players.size();
            for (BlackJackPlayer player : players) {
                player.initTotal();
                player.initWhich();
            }
            shuffle.giveNewCard(dealer);
            shuffle.newShuffle();
            List<BlackJackPlayer> delete = new ArrayList<>();
            for (BlackJackPlayer player : players) {
                if(!player.makeBet()) {
                    if (!delete.contains(player))
                        delete.add(player);
                    if (delete.size() == players.size())
                        break;
                    continue;
                }
                shuffle.giveNewCard(player);
                dealer.printDealerHandCard();
                player.printHandCard();
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
                        if (standAction(player) == 1)
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
            if (players.size() != 0) {
                if (!computer)
                    shuffle.keepGive(dealer);
                dealer.printHandCard();
                for (BlackJackPlayer player : players)
                    player.printHandCard();
                for (BlackJackPlayer player : players) {
                    player.initWhich();
                    for (int i = 0; i < player.getHandCard().size(); i++)
                        player.endGame(check.checkWin(player, dealer, i));
                }
                List<BlackJackPlayer> temp = new ArrayList<>();
                for (BlackJackPlayer player : players) {
                    System.out.print(player.getName());
                    char c = Utils.nextGame();
                    if (c == 'y' || c == 'Y')
                        temp.add(player);
                    else {
                        System.out.print(player.getName() + "'s balance in wallet is: ");
                        System.out.println(player.getWallet().getMoney());
                    }
                }
                players = temp;
            }
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
            System.out.print(player.getName() + "'s balance in wallet is: ");
            System.out.println(player.getWallet().getMoney());
        }
    }

    private void print(BlackJackPlayer p) {
        //give one card to the player and then print the hand cards
        dealer.printDealerHandCard();
        p.printHandCard();
    }

    private int standAction(BlackJackPlayer p) {
        // after the player stand, use this method to do the following work
        if (!p.isOver())
            return 0;
        shuffle.keepGive(dealer);
        computer = true;
        return 1;
    }
}
