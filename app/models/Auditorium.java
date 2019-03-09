package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import play.db.jpa.Model;

@Entity
public class Auditorium extends Model {

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "auditorium", cascade = CascadeType.ALL)
	private List<Seat> seats;

	@OneToMany(mappedBy = "auditorium", cascade = CascadeType.ALL)
	private List<Projection> projections;

	@ManyToOne
	private Cinema cinema;

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public List<Projection> getProjections() {
		return projections;
	}

	public void setProjections(List<Projection> projections) {
		this.projections = projections;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public JSONObject getSeatsJSON() {
		JSONObject res = new JSONObject();
		
		if (seats.size() > 0) {
			for (int i = 0; i < seats.size() - 1; i++) {
				for (int j = i + 1; j < seats.size(); j++) {
					if (seats.get(i).getRow() > seats.get(j).getRow()) {
						Seat temp = seats.get(i);
						seats.set(i, seats.get(j));
						seats.set(j, temp);
					} else if (seats.get(i).getRow() == seats.get(j).getRow()) {
						if (seats.get(i).getNumber() > seats.get(j).getNumber()) {
							Seat temp = seats.get(i);
							seats.set(i, seats.get(j));
							seats.set(j, temp);
						}
					}
				}
			}
			JSONArray array = new JSONArray();
			JSONObject o = null;
			for (Seat seat : seats) {
				o = new JSONObject();
				o.put("row", seat.getRow());
				o.put("num", seat.getNumber());
				o.put("available", seat.isAvailable());
				o.put("reserve", 0);
				array.add(o);
			}
			int rows = seats.get(seats.size() - 1).getRow();
			int cols = seats.get(seats.size() - 1).getNumber();
			res.put("rows", rows);
			res.put("cols", cols);
			res.put("seats", array);
		}
		return res;
	}
}
