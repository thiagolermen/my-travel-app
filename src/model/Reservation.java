package model;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int reservationId;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="reservation")
	Collection<Ticket> listTickets;
	@ManyToOne
	User user;
	
	public Reservation(User user) {
		super();
		this.user = user;
	}

	public Reservation() {
		super();
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public Collection<Ticket> getListTickets() {
		return listTickets;
	}

	public void setListTickets(Collection<Ticket> listTickets) {
		this.listTickets = listTickets;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
