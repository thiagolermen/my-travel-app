package model;

public class Booking {
	
	User user;
	Passenger departurePassenger;
	Passenger returnPassenger;
	Flight departureFlight;
	Flight returnFlight;
	Ticket departureTicket;
	Ticket returnTicket;
	
	public Booking(User user, Passenger departurePassenger, Passenger returnPassenger, Flight departureFlight, Flight returnFlight, Ticket departureTicket, Ticket returnTicket) {
		super();
		this.user = user;
		this.departurePassenger = departurePassenger;
		this.returnPassenger = returnPassenger;
		this.departureFlight = departureFlight;
		this.returnFlight = returnFlight;
		this.departureTicket = departureTicket;
		this.returnTicket = returnTicket;
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
	public Passenger getDeparturePassenger() {
		return departurePassenger;
	}
	public void setDeparturePassenger(Passenger departurePassenger) {
		this.departurePassenger = departurePassenger;
	}
	public Passenger getReturnPassenger() {
		return returnPassenger;
	}
	public void setReturnPassenger(Passenger returnPassenger) {
		this.returnPassenger = returnPassenger;
	}
	public Flight getDepartureFlight() {
		return departureFlight;
	}
	public void setDepartureFlight(Flight departureFlight) {
		this.departureFlight = departureFlight;
	}
	public Flight getReturnFlight() {
		return returnFlight;
	}
	public void setReturnFlight(Flight returnFlight) {
		this.returnFlight = returnFlight;
	}
	public Ticket getDepartureTicket() {
		return departureTicket;
	}
	public void setTicket(Ticket departureTicket) {
		this.departureTicket = departureTicket;
	}
	public Ticket getReturnTicket() {
		return returnTicket;
	}
	public void setReturnTicket(Ticket returnTicket) {
		this.returnTicket = returnTicket;
	}
}
