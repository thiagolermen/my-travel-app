package model;

public class Booking {
	
	User user;
	Passenger passenger;
	Flight flight;
	Ticket ticket;
	
	public Booking(User user, Passenger passenger, Flight flight, Ticket ticket) {
		super();
		this.user = user;
		this.passenger = passenger;
		this.flight = flight;
		this.ticket = ticket;
	}
	public Booking() {
		super();
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Passenger getPassenger() {
		return passenger;
	}
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	
	
	
}
