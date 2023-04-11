package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	public Preliminary searchFlight(Preliminary p) {
		p.setDepartureDate(LocalDate.parse(p.getDepartureDateString().subSequence(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		if (p.getArrivalDateString() != null) {			
			p.setArrivalDate(LocalDate.parse(p.getArrivalDateString().subSequence(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}
		return p;
	}

}