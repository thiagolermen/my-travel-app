package model;

import javax.persistence.*;

@Entity
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
	
	Double price;

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
}
