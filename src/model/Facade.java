package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

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
	
	@POST
	@Path("/searchflight")
    @Consumes({ "application/json" })
	public void searchFlight(Preliminary p) {
		p.setDepartureDate(LocalDate.parse(p.getDepartureDateString().subSequence(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		if (p.getArrivalDateString() != null) {			
			p.setArrivalDate(LocalDate.parse(p.getArrivalDateString().subSequence(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}
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