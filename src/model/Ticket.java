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
	@ManyToOne
	Flight flight;
	
	String seatNumber;
	
	float price;
	
	Meal meal;

	int extraLuggage;
	
	int transportFromAirport;
	
	boolean paid;

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
	
	
	
}
