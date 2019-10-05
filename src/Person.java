
public class Person {
	private Name name;
	
	public Person() {}
	public Person(String nickName) {
		this.name = new Name(nickName);
	}
	
	public Person(String firstName, String middleName, String lastName, String nickName) {
		this.name.setName(firstName, middleName, lastName, nickName);
	}
	
	public String getNickName() {
		return this.name.getNickName();
	}
	
	public void printNickName(){
		System.out.println(name.getNickName());
	}
	
	public void printName() {
		String[] names = name.getName();
		if(names[0].isEmpty() || names[0] == null) {
			System.out.println("First Name: " + names[0]);
		}
		if(names[1].isEmpty() || names[1] == null) {
			System.out.println("Middle Name: " + names[1]);	
		}
		if(names[2].isEmpty() || names[2] == null) {
			System.out.println("Last Name: " + names[2]);
		}
		if(names[3].isEmpty() || names[3] == null) {
			System.out.println("First Name: " + names[3]);
		}
	}
}
