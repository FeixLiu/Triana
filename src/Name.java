
public class Name {
	private String firstName;
	private String middleName;
	private String lastName;
	private String nickName;

	public Name() {
		/*firstName = Config.DEFAULT_FNAME;
		middleName = Config.DEFAULT_MNAME;
		lastName = Config.DEFAULT_LNAME;
		nickName = Config.DEFAULT_NNAME;*/
	}
	
	public void setName(String fname, String mname, String lname, String nname) {
		firstName = fname;
		middleName = mname;
		lastName = lname;
		nickName = nname;
	}
	
	public String[] getName() {
		String[] result = new String[4];
		result[0] = firstName;
		result[1] = middleName;
		result[2] = lastName;
		result[3] = nickName;
		return result;
	}
}
