package model;

public class RegisteredPersonEmail {

	private int emailId;
	private String personEmail;
	
	public RegisteredPersonEmail() {}
	
	public RegisteredPersonEmail( int informedEmailId, String informedPersonEmail ) {
		this.emailId = informedEmailId;
		this.personEmail = informedPersonEmail;
	}
	
	// Method Getters
	public int getEmailId() {
		return emailId;
	}
	
	public String getPersonEmail() {
		return personEmail;
	}

	// Method Setters
	public void setEmailId(int informedEmailId) {
		this.emailId = informedEmailId;
	}

	public void setPersonEmail(String informedPersonEmail) {
		this.personEmail = informedPersonEmail;
	}
	
	// Printing registered person e-mail class data
	public String toString() {
		return "Id e-mail: " + this.emailId + ".\nPerson E-mail " + this.personEmail + ".";
	}
}