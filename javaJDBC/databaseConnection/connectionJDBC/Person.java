package connectionJDBC;

public class Person {

	private int personId;
	private String personName;
	private int personAge;
	
	public Person() {}
	
	public Person( int informedPersonId, String informedPersonName, int informedPersonAge ) {
		this.personId = informedPersonId;
		this.personName = informedPersonName;
		this.personAge = informedPersonAge;
	}
	
	// Method Getters
	public int getPersonId() {
		return this.personId;
	}
	
	public String getPersonName() {
		return this.personName;
	}
	
	public int getPersonAge() {
		return this.personAge;
	}
	
	// Method Setters
	public void setPersonId( int informedPersonId ) {
		this.personId = informedPersonId;
	}
	
	public void setPersonName( String informedPersonName ) {
		this.personName = informedPersonName;
	}
	
	public void setPersonAge( int informedPersonAge ) {
		this.personAge = informedPersonAge;
	}
	
	// Printing person class data
	public String toString() {
		return "Hey, my name is " + this.personName + ".\nMy age is " + this.personAge + ".\nMy id is " + this.personId + ".";
	}
}