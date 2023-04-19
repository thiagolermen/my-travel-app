package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


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
    @Consumes({ "application/json" })
	@Produces({ "application/json" })
	public List<Flight> searchFlight(Preliminary p) {
		p.setDepartureDate(Date.valueOf(p.getDepartureDateString().substring(0, 10)));
		if (p.getArrivalDateString() != null) {			
			p.setArrivalDate(Date.valueOf(p.getArrivalDateString().substring(0, 10)));
			String departureAirportCode = p.getDepartureAirport();
			String arrivalAirportCode = p.getArrivalAirport();
			Date departureDate = p.getDepartureDate();
			Date arrivalDate = p.getArrivalDate();
			List<Flight> query_flights = em.createQuery(
				    "SELECT f FROM Flight f " +
				    "JOIN f.departureAirport dep " +
				    "JOIN f.arrivalAirport arr " +
				    "WHERE dep.code = :departureAirportCode " +
				    "AND arr.code = :arrivalAirportCode " +
				    "AND FUNCTION('DATE', f.departureDate) = FUNCTION('DATE', :departureDate);", Flight.class)
				    .setParameter("departureAirportCode", departureAirportCode)
				    .setParameter("arrivalAirportCode", arrivalAirportCode)
				    .setParameter("departureDate", departureDate)
				    .setParameter("arrivalDate", arrivalDate)
				    .getResultList();
			return query_flights;
		}
		return null;
	}

	@POST
	@Path("/loginauthentication")
    @Consumes({ "application/json" })
	public void loginAuthentication(User logged_user) {
		try{
			String email = logged_user.getEmail();
			String password = logged_user.getPassword();
            EntityTransaction entr=em.getTransaction();
            entr.begin();
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class);        
			query.setParameter(1, email);
			query.setParameter(2, password); 
			User u = query.getSingleResult();
		}catch(javax.persistence.NoResultException e){
		}
	}

	@POST
	@Path("/register")
    @Consumes({ "application/json" })
	public void register(User user) {
		user.setBrithDate(Date.valueOf(user.getBirthDateString().substring(0, 10)));
		em.persist(user);
	}

}