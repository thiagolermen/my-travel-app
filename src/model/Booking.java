package model;

public class Booking {
	
	User user;
	Flight departureFlight;
	Flight returnFlight;
	Ticket departureTicket;
	Ticket returnTicket;
	
	public Booking(User user, Flight departureFlight, Flight returnFlight, Ticket departureTicket, Ticket returnTicket) {
		super();
		this.user = user;
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
