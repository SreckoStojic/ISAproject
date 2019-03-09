package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class TheatreSeat extends Model {

	@Column(nullable = false)	
	private int row;
	
	@Column(nullable = false)
	private int number;
	
	@Column(nullable = false)
	private boolean available;
	
	@ManyToOne
	private TheatreAuditorium theatreAuditorium;

	@OneToMany(mappedBy = "theatreSeat", cascade = CascadeType.ALL)
	private List<TheatreReservation> theatreReservations;


	public TheatreAuditorium getTheatreAuditorium() {
		return theatreAuditorium;
	}

	public void setAuditorium(TheatreAuditorium theatreAuditorium) {
		this.theatreAuditorium = theatreAuditorium;
	}

	public List<TheatreReservation> getTheatreReservations() {
		return theatreReservations;
	}

	public void setTheatreReservations(List<TheatreReservation> theatreReservations) {
		this.theatreReservations = theatreReservations;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	
}
