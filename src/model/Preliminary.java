package model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;


//@Entity
public class Preliminary{
	
	private String departureAirport;
	private String arrivalAirport;
	private String departureDateString;
	private String arrivalDateString;
	private Date departureDate;
	private Date arrivalDate;
	private int nbPassengers;
	private boolean oneWay;
	private int flightId;
	
	public Preliminary() { 
    }

	public String getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}
	
	public String getDepartureDateString() {
		return departureDateString;
	}

	public void setDepartureDateString(String departureDateString) {
		this.departureDateString = departureDateString;
	}

	public String getArrivalDateString() {
		return arrivalDateString;
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

	public void setArrivalDateString(String arrivalDateString) {
		this.arrivalDateString = arrivalDateString;
	}

	public int getNbPassengers() {
		return nbPassengers;
	}

	public void setNbPassengers(int nbPassengers) {
		this.nbPassengers = nbPassengers;
	}

	public boolean isOneWay() {
		return oneWay;
	}

	public void setOneWay(boolean oneWay) {
		this.oneWay = oneWay;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	} 
	
	
	
}
