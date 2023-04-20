package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.BeanParam;
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
	
	@GET
	@Path("/searchflight")
	@Produces({ "application/json" })
	public List<Flight> searchFlight(@QueryParam("departureAirport") String departureAirport, @QueryParam("arrivalAirport") String arrivalAirport,  @QueryParam("departureDate") String departureDate, @QueryParam("returnDate") String returnDate) {
//		p.setDepartureDate(Date.valueOf(p.getDepartureDateString().substring(0, 10)));
//		if (p.getArrivalDateString() != null) {			
//			p.setArrivalDate(Date.valueOf(p.getArrivalDateString().substring(0, 10)));
//			Date departureDate = p.getDepartureDate();
//			Date arrivalDate = p.getArrivalDate();
			List<Object[]> query_results = em.createQuery(
				    "SELECT f, dep, arr FROM Flight f " +
				    "JOIN f.departureAirport dep " +
				    "JOIN f.arrivalAirport arr " +
				    "WHERE dep.airportIataCode = :departureAirportCode " +
				    "AND arr.airportIataCode = :arrivalAirportCode "
//				    "AND FUNCTION('DATE', f.departureDate) = FUNCTION('DATE', :departureDate);"
				    ,Object[].class)
				    .setParameter("departureAirportCode", departureAirport)
				    .setParameter("arrivalAirportCode", arrivalAirport)
//				    .setParameter("departureDate", departureDate)
//				    .setParameter("arrivalDate", arrivalDate)
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
		//}
		//return null;
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
		user.setBrithDate(Date.valueOf(user.getBirthDateString().substring(0, 10)));
		em.persist(user);
	}
	
	@POST
	@Path("/bookflight")
	@Consumes({ "application/json" })
	public void bookFlight(Booking data) {
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
	    dbTicket.setFlight(dbFlight);
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
}