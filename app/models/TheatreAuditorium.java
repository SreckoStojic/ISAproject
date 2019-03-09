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
public class TheatreAuditorium extends Model {

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "theatreAuditorium", cascade = CascadeType.ALL)
	private List<TheatreSeat> theatreSeats;

	@OneToMany(mappedBy = "theatreAuditorium", cascade = CascadeType.ALL)
	private List<TheatreProjection> theatreProjections;

	@ManyToOne
	private Theatre theatre;

	public List<TheatreSeat> getTheatreSeats() {
		return theatreSeats;
	}

	public void setTheatreSeats(List<TheatreSeat> theatreSeats) {
		this.theatreSeats = theatreSeats;
	}

	public List<TheatreProjection> getTheatreProjections() {
		return theatreProjections;
	}

	public void setTheatreProjections(List<TheatreProjection> theatreProjections) {
		this.theatreProjections = theatreProjections;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}

	public JSONObject getTheatreSeatsJSON() {
		JSONObject res = new JSONObject();
		
		if (theatreSeats.size() > 0) {
			for (int i = 0; i < theatreSeats.size() - 1; i++) {
				for (int j = i + 1; j < theatreSeats.size(); j++) {
					if (theatreSeats.get(i).getRow() > theatreSeats.get(j).getRow()) {
						TheatreSeat temp = theatreSeats.get(i);
						theatreSeats.set(i, theatreSeats.get(j));
						theatreSeats.set(j, temp);
					} else if (theatreSeats.get(i).getRow() == theatreSeats.get(j).getRow()) {
						if (theatreSeats.get(i).getNumber() > theatreSeats.get(j).getNumber()) {
							TheatreSeat temp = theatreSeats.get(i);
							theatreSeats.set(i, theatreSeats.get(j));
							theatreSeats.set(j, temp);
						}
					}
				}
			}
			JSONArray array = new JSONArray();
			JSONObject o = null;
			for (TheatreSeat seat : theatreSeats) {
				o = new JSONObject();
				o.put("row", seat.getRow());
				o.put("num", seat.getNumber());
				o.put("available", seat.isAvailable());
				array.add(o);
			}
			int rows = theatreSeats.get(theatreSeats.size() - 1).getRow();
			int cols = theatreSeats.get(theatreSeats.size() - 1).getNumber();
			res.put("rows", rows);
			res.put("cols", cols);
			res.put("seats", array);
		}
		return res;
	}
}
