import java.util.Scanner;

public class Utils {
	private static Scanner scanner = new Scanner(System.in);
	
	public static String getName() {
		String str;
		do {
			System.out.print("What's your name: ");
			str = scanner.nextLine();
		} while (str.length() == 0);
		return str;
	}

	public static boolean realMan() {
		String str;
		do {
			System.out.print("Is the dealer the real man? Y/y for yes, other for no:");
			str = scanner.nextLine();
		} while (str.length() == 0);
		return (str.length() == 1 && (str.charAt(0) == 'y' || str.charAt(0) == 'Y'));
	}

	public static int getMoney(int player_money) {
		boolean flag = true;
		int m = 0;
		while (flag) {
			boolean temp = false;
			m = 0;
			String str = scanner.nextLine();
			char[] a = str.toCharArray();
			for (char c : a) {
				if (c < '0' || c > '9') {
					System.out.println("Wrong money format, please input again.");
					temp = true;
					break;
				}
				m = m * 10 + c - '0';
			}
			flag = temp;
		}
		if (player_money == -1)
			return m == 0 ? Config.DEFAULTMONEY : m;
		else {
			return Math.max(m, 3 * player_money);
		}
	}

	public static char yesOrNo() {
		String str;
		do {
			System.out.print("Y/y for yes, other for no: ");
			str = scanner.nextLine();
		} while (str.length() == 0);
		return str.length() == 1 ? str.charAt(0) : 'n';
	}
	
	public static int getNumberFromPlayer(){
		String str = scanner.nextLine();
		while(!str.matches("^[0-9]*$") || str.isEmpty()) {
			System.out.print("Please input a correct number: ");
			str = scanner.nextLine();
		}
		int num = 0;
		try {
			num = Integer.parseInt(str);
		}
		catch (NumberFormatException e) {
			System.out.println("Out of range, set to default 1.");
			num = 1;
		}
		return num;
	}
	
}
