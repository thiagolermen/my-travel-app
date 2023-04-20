package model;

import javax.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int passengerId;
	
	@ManyToOne
	Flight flight;
	
	@OneToOne
	Ticket ticket;
	
	private String firstName;
	private String lastName;
	private String passportNumber;
	
	public Passenger(String firstName, String lastName, String passportNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.passportNumber = passportNumber;
	}
	public Passenger() {
		super();
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	
	
	
}
