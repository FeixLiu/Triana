import java.util.Scanner;

public class Utils {
	private static Scanner scanner = new Scanner(System.in);
	
	public static String getName(String name) {
		String str;
		do {
			System.out.print("What's " + name + "'s name: ");
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

	public static int getMoney() {
		System.out.print("How many money do you have (default 200, please make sure you have more than 10): ");
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
		return m == 0 ? 200 : m;
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
