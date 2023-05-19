package model;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int ticketId;

	@OneToOne(fetch=FetchType.EAGER,mappedBy="ticket")
	Passenger passenger;
	@ManyToOne
	Reservation reservation;
	
	String seatNumber;
	
	float price;
	
	String mealType;

	boolean extraLuggage;
	
	boolean transportFromAirport;
	
	boolean paid = true;
	
	

	public Ticket(String seatNumber, float price, String mealType, boolean extraLuggage, boolean transportFromAirport) {
		this.seatNumber = seatNumber;
		this.price = price;
		this.mealType = mealType;
		this.extraLuggage = extraLuggage;
		this.transportFromAirport = transportFromAirport;
	}

	public Ticket() {
		super();
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public boolean isExtraLuggage() {
		return extraLuggage;
	}

	public void setExtraLuggage(boolean extraLuggage) {
		this.extraLuggage = extraLuggage;
	}

	public boolean isTransportFromAirport() {
		return transportFromAirport;
	}

	public void setTransportFromAirport(boolean transportFromAirport) {
		this.transportFromAirport = transportFromAirport;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	
	
	
}
