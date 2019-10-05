
public class Person {
	private Name name;
	
	public Person() {}
	public Person(String name) {
		this.name.setNickName(name);
	}
	
	public String getName() {
		return this.name.getNickName();
	}
	
	public void printName(){
		System.out.println(name);
	}
}
