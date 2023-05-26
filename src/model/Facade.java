package model;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import java.util.Locale;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


@Singleton
@Path("/")
public class Facade {
	
	@PersistenceContext
	EntityManager em;
	
	@GET
	@Path("/listairports")
    @Produces({ "application/json" })
	public List<Airport> listAirports() {
		return em.createQuery("FROM Airport", Airport.class).getResultList();
	}
	
	public static String capitalizeWord(String str){  
	    String words[]=str.split("\\s");  
	    String capitalizeWord="";  
	    for(String w:words){  
	        String first=w.substring(0,1);  
	        String afterfirst=w.substring(1);  
	        capitalizeWord+=first.toUpperCase()+afterfirst+" ";  
	    }  
	    return capitalizeWord.trim();  
	}  
	
	@GET
	@Path("/searchdepartureflight")
	@Produces({ "application/json" })
	public List<Flight> searchDepartureFlight(@QueryParam("departureAirport") String departureAirport, @QueryParam("arrivalAirport") String arrivalAirport,  @QueryParam("departureDateString") String departureDateString, @QueryParam("arrivalDateString") String returnDateString) throws java.text.ParseException {
		departureAirport = capitalizeWord(departureAirport);
		arrivalAirport = capitalizeWord(arrivalAirport);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date departureDate;
		Date returnDate;

		departureDate = new Date(dateFormat.parse(departureDateString.substring(0, 10)).getTime());
		returnDate = new Date(dateFormat.parse(returnDateString.substring(0, 10)).getTime());

		List<Object[]> query_results = em.createQuery(
		        "SELECT f, dep, arr FROM Flight f " +
		        "JOIN f.departureAirport dep " +
		        "JOIN f.arrivalAirport arr " +
		        "WHERE dep.airportCountry = :departureAirportCountry " +
		        "AND arr.airportCountry = :arrivalAirportCountry " +
		        "AND f.departureDate = :departureDate " +
		        "AND f.arrivalDate = :arrivalDate" 
				, Object[].class)
		        .setParameter("departureAirportCountry", departureAirport)
		        .setParameter("arrivalAirportCountry", arrivalAirport)
		        .setParameter("departureDate", departureDate)
		        .setParameter("arrivalDate", departureDate)
		        .getResultList();

		List<Flight> query_flights = new ArrayList<>();
		for (Object[] result : query_results) {
		    Flight flight = (Flight) result[0];
		    Airport depAirport = (Airport) result[1];
		    Airport arrAirport = (Airport) result[2];
		    flight.setDepartureAirport(depAirport);
		    flight.setArrivalAirport(arrAirport);
		    query_flights.add(flight);
		}
		return query_flights;
	}
	
	@GET
	@Path("/searchreturnflight")
	@Produces({ "application/json" })
	public List<Flight> searchReturnFlight(@QueryParam("departureAirport") String departureAirport, @QueryParam("arrivalAirport") String arrivalAirport,  @QueryParam("departureDateString") String departureDateString, @QueryParam("arrivalDateString") String returnDateString) throws java.text.ParseException {
		departureAirport = capitalizeWord(departureAirport);
		arrivalAirport = capitalizeWord(arrivalAirport);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date departureDate;
		Date returnDate;

		departureDate = new Date(dateFormat.parse(departureDateString.substring(0, 10)).getTime());
		returnDate = new Date(dateFormat.parse(returnDateString.substring(0, 10)).getTime());

		List<Object[]> query_results = em.createQuery(
		        "SELECT f, dep, arr FROM Flight f " +
		        "JOIN f.departureAirport dep " +
		        "JOIN f.arrivalAirport arr " +
		        "WHERE dep.airportCountry = :departureAirportCountry " +
		        "AND arr.airportCountry = :arrivalAirportCountry " +
		        "AND f.departureDate = :departureDate " +
		        "AND f.arrivalDate = :arrivalDate" 
				, Object[].class)
		        .setParameter("departureAirportCountry", departureAirport)
		        .setParameter("arrivalAirportCountry", arrivalAirport)
		        .setParameter("departureDate", returnDate)
		        .setParameter("arrivalDate", returnDate)
		        .getResultList();

		List<Flight> query_flights = new ArrayList<>();
		for (Object[] result : query_results) {
		    Flight flight = (Flight) result[0];
		    Airport depAirport = (Airport) result[1];
		    Airport arrAirport = (Airport) result[2];
		    flight.setDepartureAirport(depAirport);
		    flight.setArrivalAirport(arrAirport);
		    query_flights.add(flight);
		}
		return query_flights;
	}

	@GET
	@Path("/loginauthentication")
    @Produces({ "application/json" })
	public boolean loginAuthentication(@QueryParam("email") String email, @QueryParam("password") String password) {
		List<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class).setParameter("email", email).setParameter("password", password).getResultList();
		return query.isEmpty();
	}

	@POST
	@Path("/register")
    @Consumes({ "application/json" })
	public void register(User user) {
		user.setBirthDate(Date.valueOf(user.getBirthDateString().substring(0, 10)));
		em.persist(user);
	}
	
	@POST
	@Path("/bookflight")
	@Consumes({ "application/json" })
	public void bookFlight(Booking data) {
		if (!data.isOneWay) {
			data.getTicket().setPrice(data.getTicket().getPrice()*2);
		}
		User user = data.getUser();
		Passenger passenger = data.getPassenger();
		Flight flight = data.getFlight();
		Ticket ticket = data.getTicket();
	    User dbUser = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
	                   .setParameter("email", user.getEmail())
	                   .getSingleResult();
	    
	    em.persist(passenger);
	    em.persist(ticket);

	    Passenger dbPassenger = em.find(Passenger.class, passenger.getPassengerId());
	    Ticket dbTicket = em.find(Ticket.class, ticket.getTicketId());
	    Flight dbFlight = em.find(Flight.class, flight.getFlightId());

	    Reservation reservation = new Reservation(dbUser);
	    em.persist(reservation);
	    dbUser.getListReservation().add(reservation);
	    em.merge(dbUser);

	    dbPassenger.setFlight(dbFlight);
	    dbPassenger.setTicket(dbTicket);
	    em.merge(dbPassenger);

	    dbTicket.setPassenger(dbPassenger);
	    dbTicket.setReservation(reservation);
	    //dbTicket.setFlight(dbFlight);
	    em.merge(dbTicket);

	    dbFlight.getListPassengers().add(dbPassenger);
	    em.merge(dbFlight);
	}

	@GET
	@Path("/searchticket")
	@Produces({ "application/json" })
	public List<Ticket> listTickets(@QueryParam("email") String email, @QueryParam("password") String password) {
		User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class).setParameter("email", email).getSingleResult();
		
		Collection<Reservation> listReservation = user.getListReservation();
		
		List<Ticket> tickets = new ArrayList<>();
		for (Reservation reservation : listReservation) {
			Collection<Ticket> listTickets = reservation.getListTickets();
			
			for (Ticket ticket : listTickets) {
				tickets.add(ticket);
			}
		}
		
		return tickets;
	}
	
	@GET
	@Path("/myaccount")
	@Produces({ "application/json" })
	public User myAccount(@QueryParam("email") String email, @QueryParam("password") String password) {
		User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class).setParameter("email", email).getSingleResult();
		
		return user;
	}
	
	@POST
	@Path("/deleteaccount")
	@Produces({ "application/json" })
	public void deleteAccount(User user) {
		
		em.find(User.class, user);
		
		em.getTransaction().begin();
		em.remove(user);
		em.getTransaction().commit();
	}
}