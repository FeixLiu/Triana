import java.util.ArrayList;
import java.util.List;

public class TriantaEnaTable implements Table {
    private TriantaEnaShuffle shuffle;
    private TriantaEnaRules check;
    private List<TriantaEnaPlayer> players;
    private TriantaEnaPlayer dealer;
    private int playerNum;
    private boolean computer;
    private boolean flag;

    public TriantaEnaTable() {
    	System.out.println("Welcome to the Trianta Ena game.");
    	System.out.println("The objective of the game is to accumulate a hand of cards that equals 31.");
    	System.out.println("Or a hand that has a card value greater than your opponents without exceeding 31.");
        int playerNum = 11;
        while ((playerNum > Config.MAXPLAYER || playerNum < Config.MINPLAYER) && playerNum != 0) {
            System.out.print("How many people in the game (include banker)? ");
            playerNum = Utils.getNumberFromPlayer();
            this.playerNum = playerNum;
        }
        int all = playerNum;
    	players = new ArrayList<>();
        shuffle = new TriantaEnaShuffle(Config.CARDSET, Config.DEALERMIN, Config.UPBOUND);
        check = new TriantaEnaRules();
        String str;
        List<String> allName = new ArrayList<>();
        for (int i = 0; i < playerNum; i++) {
            System.out.print("Please input the name of person " + (i + 1) + ".");
            str = Utils.getName();
            allName.add(str);
        }
        // random banker
        int random = (int)(Math.random() * all);
        System.out.print("Please input the money for all player (default 200, please" +
                " make sure you have more than 10): ");
        int playerMoney = Utils.getMoney(-1);
        System.out.print("Please input the money for all banker (default 3 times of player's money, " +
                "please make sure you have more than 10): ");
        int bankerMoney = Utils.getMoney(playerMoney);
        for (int i = 0; i < playerNum; i++) {
            if (i == random) {
                dealer = new TriantaEnaPlayer(allName.get(i), Config.BANKER, bankerMoney);
                System.out.println(allName.get(i) + " you are the banker.");
            }
            else
                players.add(new TriantaEnaPlayer(allName.get(i), Config.PLAYER, playerMoney));
        }
        if (this.playerNum <= 0)
            System.out.println("See you");
        computer = false;
        flag = true;
    }

    public void playGame() {
        // the body of the game, ask the player for its choice and ask the checker check whether the player bust or not for each round
        List<TriantaEnaPlayer> playing = new ArrayList<>();
        List<TriantaEnaPlayer> delete = new ArrayList<>();
        while (flag && players.size() != 0) {
            printMoney();
            dealer.deleteHandCard();
            for (TriantaEnaPlayer player : players) {
                player.deleteHandCard();
            }
            playerNum = players.size();
            shuffle.newShuffle();
            shuffle.giveOneCard(dealer,0);
            for (TriantaEnaPlayer player : players) {
                shuffle.giveOneCard(player, 0);
            }
            for (TriantaEnaPlayer player : players) {
            	print(player);
                int a = player.makeBet();
                if (a == Config.NOENOUGHMONEY) {
                    if (!delete.contains(player))
                        delete.add(player);
                    if (delete.size() == players.size())
                        break;
                }
                else if (a == Config.MAKEBET){
                    playing.add(player);
                    shuffle.giveNewCard(player, 2);
                }
            }
            if (playing.size() == 0) {
                System.out.println("See you");
                break;
            }
            for (TriantaEnaPlayer player : playing) {
                print(player);
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
                for (TriantaEnaPlayer player : playing)
                    player.printHandCard(true);
                for (TriantaEnaPlayer player : playing)
                    for (int i = 0; i < player.getHandCard().size(); i++)
                        player.endGame(check.checkWin(player, dealer, i), dealer);
                printMoney();
                List<TriantaEnaPlayer> temp = new ArrayList<>();
                for (TriantaEnaPlayer player : players) {
                    System.out.print(player.getNickName() + ", do you want to play another game? ");
                    char c = Utils.yesOrNo();
                    if (c != 'y' && c != 'Y') {
                        temp.add(player);
                        System.out.print(player.getNickName() + "'s balance in wallet is: ");
                        System.out.println(player.getWallet().getMoney());
                    }
                }
                for (TriantaEnaPlayer out : temp)
                    players.remove(out);
            }
            playing.clear();
            changeRole();
            List<TriantaEnaPlayer> no_money = new ArrayList<>();
            for (TriantaEnaPlayer p : players) {
                if (p.getWallet().getMoney() <= 0)
                    no_money.add(p);
            }
            for (TriantaEnaPlayer p : no_money)
                players.remove(p);
        }
        if (playerNum > 0)
            printResult();
    }

    private void printMoney() {
        System.out.println("Banker's money: " + dealer.getWallet().getMoney());
        for (TriantaEnaPlayer p : players)
            System.out.println(p.getNickName() + "'s money: " + p.getWallet().getMoney());
    }

    private void changeRole() {
        List<TriantaEnaPlayer> more = new ArrayList<>();
        int banker = dealer.getWallet().getMoney();
        for (TriantaEnaPlayer player : players) {
            if (player.getWallet().getMoney() > banker)
                more.add(player);
        }
        if (more.size() == 0)
            return;
        else {
            more.sort((o1, o2) -> {
                if (o1.getWallet().getMoney() > o2.getWallet().getMoney())
                    return 1;
                else if (o1.getWallet().getMoney() == o2.getWallet().getMoney())
                    return 0;
                return -1;
            });
        }
        for (TriantaEnaPlayer p : more) {
            System.out.println(p.getNickName() + " do you want to be a banker? ");
            char c = Utils.yesOrNo();
            if (c == 'y' || c == 'Y') {
                p.switchRole();
                dealer.switchRole();
                players.add(dealer);
                dealer = p;
                players.remove(p);
                return;
            }
        }
        int a = (int)(Math.random() * more.size());
        TriantaEnaPlayer p = more.get(a);
        System.out.println(p.getNickName() + " you are chosen to be a banker ramdonly.");
        p.switchRole();
        dealer.switchRole();
        players.add(dealer);
        dealer = p;
        players.remove(p);
    }

    private boolean hitAction(TriantaEnaPlayer player) {
        shuffle.giveOneCard(player, player.getWhich());
        print(player);
        if (check.checkBust(player, player.getWhich())) {
            player.endGame(Config.BUST, dealer);
            return false;
        }
        return true;
    }

    public void printResult() {
        for (TriantaEnaPlayer player : players) {
            System.out.print(player.getNickName() + "'s balance in wallet is: ");
            System.out.println(player.getWallet().getMoney());
        }
    }

    private void print(TriantaEnaPlayer p) {
        //give one card to the player and then print the hand cards
    	System.out.println("========================================");
        dealer.printHandCard(true);
        for (TriantaEnaPlayer other : players) {
            if (p.equals(other))
                other.printHandCard(true);
            else
                other.printHandCard(false);
        }
        System.out.println("========================================");
    }

    private int standAction(TriantaEnaPlayer p) {
        // after the player stand, use this method to do the following work
        if (!p.isOver())
            return 0;
        shuffle.keepGive(dealer);
        computer = true;
        return 1;
    }
}
