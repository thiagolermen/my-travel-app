package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Path;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Singleton
@Startup
//@Path("/")
public class DatabaseInit {
	@PersistenceContext
	EntityManager em;
	
	final String DATA_PATH = "/home/thiago/Documents/ENSEEIHT-BigData/2SN/S8/UE Applications concurrentes et communicantes, bases de données/Applications Web/my-travel-app/src/data";
	
	@PostConstruct
	public void init() {
		
		JSONParser parser = new JSONParser();
		int maxData = 1000;
		
		// Import airport data from JSON file
		try {
			
			// Initialize admin user
			em.persist(new User("admin@admin", "admin", true));
			
			JSONArray a = (JSONArray) parser.parse(new FileReader(DATA_PATH + "/airports.json"));
			for (Object o : a){
				JSONObject airport = (JSONObject) o;
		
			    String name = (String) airport.get("name");
			    String iataCode = (String) airport.get("iata_code");
				String country = (String) airport.get("country");
				if(iataCode.equals("N/A") || iataCode.isEmpty()) continue;
				
				em.persist(new Airport(name, iataCode, country));
			}
			
			a = (JSONArray) parser.parse(new FileReader(DATA_PATH + "/aircrafts.json"));
			for (Object o : a){
				JSONObject aircraft = (JSONObject) o;
		
			    String name = (String) aircraft.get("Model");
			    String icaoCode = (String) aircraft.get("ICAOCode");
			    int capacity = getRandomNumberInRange(500, 800);
			    
			    if(icaoCode.equals("N/A") || icaoCode.isEmpty()) continue;
			    List<Aircraft> qa = em.createQuery("SELECT a FROM Aircraft a WHERE a.aircraftIcaoCode = :icaoCode", Aircraft.class).setParameter("icaoCode", icaoCode).getResultList(); 
				if(!qa.isEmpty()) continue;
				em.persist(new Aircraft(name, icaoCode, capacity));
			}
			
			Object o = (JSONObject) parser.parse(new FileReader(DATA_PATH + "/flights.json"));
			JSONObject data = (JSONObject) o;
			JSONArray flight = (JSONArray) data.get("data");
			int counter = 0;
			// Import flight information from JSON
			for (Object f : flight){
				if (counter == maxData) break;
				counter++;
				JSONObject flight_info = (JSONObject) f;

				JSONObject aircraft = (JSONObject) flight_info.get("aircraft");
				JSONObject departure = (JSONObject) flight_info.get("departure");
				JSONObject arrival = (JSONObject) flight_info.get("arrival");
				
				// Aircraft data
				String aircraftIcaoCode = (String) aircraft.get("icaoCode");
				List<Aircraft> query_aircraft = em.createQuery("SELECT a FROM Aircraft a WHERE a.aircraftIcaoCode = :aircraftIcaoCode", Aircraft.class).setParameter("aircraftIcaoCode", aircraftIcaoCode).getResultList(); 
				
				// If iataAirportCode doesn't exist
				if (query_aircraft.isEmpty()) continue;
				
				// Flight data
				String departureAirportIataCode = (String) departure.get("iataCode");
				String arrivalAirportIataCode = (String) arrival.get("iataCode");
				float price = (float) getRandomNumberInRange(200, 800);
				
				// Check if there's available data
				if (aircraftIcaoCode.isEmpty() || departureAirportIataCode.isEmpty() || arrivalAirportIataCode.isEmpty()) continue;
				
				List<Airport> query_departure_airport = em.createQuery("SELECT a FROM Airport a WHERE a.airportIataCode = :departureAirportIataCode", Airport.class).setParameter("departureAirportIataCode", departureAirportIataCode).getResultList(); 
				List<Airport> query_arrival_airport = em.createQuery("SELECT a FROM Airport a WHERE a.airportIataCode = :arrivalAirportIataCode", Airport.class).setParameter("arrivalAirportIataCode", arrivalAirportIataCode).getResultList(); 
				
				// If iataAirportCode doesn't exist
				if (query_departure_airport.isEmpty() || query_arrival_airport.isEmpty()) continue;
				
				Date startDate = new Date(System.currentTimeMillis());
				Date endDate = Date.valueOf("2023-06-03");
				Date date = between(startDate, endDate);

				// Create the same flight every day
				for (Date d = startDate; d.before(endDate); d = new Date(d.getTime() + (24*60*60*1000))) {
					em.persist(new Flight(query_aircraft.get(0), query_departure_airport.get(0), query_arrival_airport.get(0), d, d, price));
					em.persist(new Flight(query_aircraft.get(0), query_arrival_airport.get(0), query_departure_airport.get(0), d, d, price));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public static Date between(Date startInclusive, Date endExclusive) {
	    long startMillis = startInclusive.getTime();
	    long endMillis = endExclusive.getTime();
	    long randomMillisSinceEpoch = ThreadLocalRandom
	      .current()
	      .nextLong(startMillis, endMillis);

	    return new Date(randomMillisSinceEpoch);
	}
}
