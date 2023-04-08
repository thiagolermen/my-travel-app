package model;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class Airport {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int airportId;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="airport")
	Collection<Flight> listFlights;
	
	private String airportName;

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	
	
}
