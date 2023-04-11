package model;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int reservationId;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="reservation")
	Collection<Ticket> listTickets;
	@ManyToOne
	User user;
	
}
