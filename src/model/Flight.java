package model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "flights")
public class Flight {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int flightId;
	
	@ManyToOne
    Aircraft aircraft;
	@ManyToOne
	Airport departureAirport;
	@ManyToOne
	Airport arrivalAirport;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="flight")
    Collection<Passenger> listPassengers;

	private Date departureDate;
	private Date arrivalDate;
	private LocalTime departureTime;
	private LocalTime arrivalTime;
	private Double price;
	
	public Flight(){
		super();
	}
	
	public Flight(Aircraft aircraft, Airport departureAirport, Airport arrivalAirport, Date departureDate, Date arrivalDate) {
		super();
		this.aircraft = aircraft;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
	}
	
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public LocalTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
