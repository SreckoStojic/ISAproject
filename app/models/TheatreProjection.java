package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class TheatreProjection extends Model {

	@ManyToOne
	private TheatreRepertoire theatreRepertoire;

	@ManyToOne
	private ProjectionDate projectionDate;	

	@ManyToOne
	private TheatreAuditorium theatreAuditorium;

	@ManyToOne
	private ProjectionTime projectionTime;

	@ManyToOne
	private Performance performance;

	@OneToMany(mappedBy = "theatreProjection")
	private List<TheatreReservation> theatreReservations;

	public List<TheatreReservation> getTheatreReservations() {
		return theatreReservations;
	}

	public void setTheatreReservations(List<TheatreReservation> theatreReservations) {
		this.theatreReservations = theatreReservations;
	}

	public TheatreRepertoire getTheatreRepertoire() {
		return theatreRepertoire;
	}

	public void setTheatreRepertoire(TheatreRepertoire theatreRepertoire) {
		this.theatreRepertoire = theatreRepertoire;
	}

	public ProjectionDate getProjectionDate() {
		return projectionDate;
	}

	public void setProjectionDate(ProjectionDate projectionDate) {
		this.projectionDate = projectionDate;
	}

	public TheatreAuditorium getTheatreAuditorium() {
		return theatreAuditorium;
	}

	public void setTheatreAuditorium(TheatreAuditorium theatreAuditorium) {
		this.theatreAuditorium = theatreAuditorium;
	}

	public ProjectionTime getProjectionTime() {
		return projectionTime;
	}

	public void setProjectionTime(ProjectionTime projectionTime) {
		this.projectionTime = projectionTime;
	}

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
	}	

}
