package model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userId;
	
	@OneToMany
	Collection<Reservation> listReservation;

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String birthDateString;
	private Date brithDate;
	private boolean isAdmin = false;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBirthDateString() {
		return birthDateString;
	}
	public void setBirthDateString(String birthDateString) {
		this.birthDateString = birthDateString;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Date getBrithDate() {
		return brithDate;
	}
	public void setBrithDate(Date brithDate) {
		this.brithDate = brithDate;
	}

}
