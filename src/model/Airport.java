package model;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "airports")
public class Airport {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int airportId;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="departureAirport")
	Collection<Flight> listDepartureFlights;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="arrivalAirport")
	Collection<Flight> listArrivalFlights;
	
	private String airportName;

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	
	
}
