package models;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import play.db.jpa.Model;

@Entity
public class ProjectionTime extends Model {

	@Column(nullable = false)
	private String time;

	private boolean full;

	@Column(nullable = false)
	private double price;

	@ManyToOne
	private ProjectionDate projectionDate;

	@OneToMany(mappedBy = "projectionTime", cascade = CascadeType.ALL)
	private List<Projection> projections;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isFull() {
		return full;
	}

	public void setFull(boolean full) {
		this.full = full;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ProjectionDate getProjectionDate() {
		return projectionDate;
	}

	public void setProjectionDate(ProjectionDate projectionDate) {
		this.projectionDate = projectionDate;
	}

	public List<Projection> getProjections() {
		return projections;
	}

	public void setProjections(List<Projection> projections) {
		this.projections = projections;
	}

}
