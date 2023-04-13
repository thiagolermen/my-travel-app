package model;

import java.util.Collection;

import javax.persistence.*;


@Entity
@Table(name = "aircrafts")
public class Aircraft {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int aircraftId;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="aircraft")
	Collection<Flight> listFlights;
	
	private String aircraftName;
	private int aircraftCapacity;
	
	public String getAircraftName() {
		return aircraftName;
	}
	public void setAircraftName(String aircraftName) {
		this.aircraftName = aircraftName;
	}
	public int getAircraftCapacity() {
		return aircraftCapacity;
	}
	public void setAircraftCapacity(int aircraftCapacity) {
		this.aircraftCapacity = aircraftCapacity;
	}
	
	
}
