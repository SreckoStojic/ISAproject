package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Projection extends Model {

	@ManyToOne
	private Repertoire repertoire;

	@ManyToOne
	private ProjectionDate projectionDate;	

	@ManyToOne
	private Auditorium auditorium;

	@ManyToOne
	private ProjectionTime projectionTime;

	@ManyToOne
	private Movie movie;

	@OneToMany(mappedBy = "projection")
	private List<Reservation> reservations;

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Repertoire getRepertoire() {
		return repertoire;
	}

	public void setRepertoire(Repertoire repertoire) {
		this.repertoire = repertoire;
	}

	public ProjectionDate getProjectionDate() {
		return projectionDate;
	}

	public void setProjectionDate(ProjectionDate projectionDate) {
		this.projectionDate = projectionDate;
	}

	public Auditorium getAuditorium() {
		return auditorium;
	}

	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}

	public ProjectionTime getProjectionTime() {
		return projectionTime;
	}

	public void setProjectionTime(ProjectionTime projectionTime) {
		this.projectionTime = projectionTime;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}	

}
