package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class TheatreReservation extends Model {

	@ManyToOne
	private TheatreProjection theatreProjection;

	@ManyToOne
	private TheatreSeat theatreSeat;

	@ManyToOne
	private User user;
	

	public TheatreProjection getTheatreProjection() {
		return theatreProjection;
	}

	public void setTheatreProjection(TheatreProjection theatreProjection) {
		this.theatreProjection = theatreProjection;
	}

	public TheatreSeat getTheatreSeat() {
		return theatreSeat;
	}

	public void setTheatreSeat(TheatreSeat theatreSeat) {
		this.theatreSeat = theatreSeat;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}

