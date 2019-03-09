package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import play.db.jpa.Model;

@Entity
public class Cinema extends Model {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String address;

	@OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
	private List<Repertoire> repertoires;

	@OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
	private List<Auditorium> auditoriums;

	@Column
	private String description;

	@Column
	private String city;
	
	@OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
	private List<CinemaRate> cinemaRates;

	public List<CinemaRate> getCinemaRates() {
		return cinemaRates;
	}

	public void setCinemaRates(List<CinemaRate> cinemaRates) {
		this.cinemaRates = cinemaRates;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Repertoire> getRepertoires() {
		return repertoires;
	}

	public void setRepertoires(List<Repertoire> repertoires) {
		this.repertoires = repertoires;
	}

	public List<Auditorium> getAuditoriums() {
		return auditoriums;
	}

	public void setAuditoriums(List<Auditorium> auditoriums) {
		this.auditoriums = auditoriums;
	}

	public JSONArray getMoviesJSON() {
		JSONArray array = new JSONArray();
		JSONObject o = null;
		for (Movie movie : repertoires.get(0).getMovies()) {
			o = new JSONObject();
			o.put("id", movie.getId());
			o.put("title", movie.getTitle());
			array.add(o);
		}
		return array;
	}
	
	public double calculateAverageRate(){
		double result = 0;
		if (cinemaRates.size() > 0){
			for (CinemaRate cr : cinemaRates) {
				result += cr.getRate();
			}
			return result / cinemaRates.size();
		}
			return 0;
		
	}
}