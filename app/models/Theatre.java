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
public class Theatre extends Model {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String address;

	@OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
	private List<TheatreRepertoire> theatreRepertoires;

	@OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
	private List<TheatreAuditorium> theatreAuditoriums;

	@Column
	private double eval;

	@Column
	private String description;

	@Column
	private String city;

	@OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
	private List<TheatreRate> theatreRates;
	
	public List<TheatreRate> getTheatreRates() {
		return theatreRates;
	}

	public void setTheatreRates(List<TheatreRate> theatreRates) {
		this.theatreRates = theatreRates;
	}

	public void setTheatreAuditoriums(List<TheatreAuditorium> theatreAuditoriums) {
		this.theatreAuditoriums = theatreAuditoriums;
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

	public List<TheatreRepertoire> getTheatreRepertoires() {
		return theatreRepertoires;
	}

	public void setTheatreRepertoires(List<TheatreRepertoire> theatreRepertoires) {
		this.theatreRepertoires = theatreRepertoires;
	}

	public double getEval() {
		return eval;
	}

	public void setEval(double eval) {
		this.eval = eval;
	}

	public List<TheatreAuditorium> getTheatreAuditoriums() {
		return theatreAuditoriums;
	}

	public void setAuditoriums(List<TheatreAuditorium> theatreAuditoriums) {
		this.theatreAuditoriums = theatreAuditoriums;
	}

	public JSONArray getPerformancesJSON() {
		JSONArray array = new JSONArray();
		JSONObject o = null;
		for (Performance performance : theatreRepertoires.get(0).getPerformances()) {
			o = new JSONObject();
			o.put("id", performance.getId());
			o.put("title", performance.getTitle());
			array.add(o);
		}
		return array;
	}
	
	public double calculateAverageRate(){
		double result = 0;
		if (theatreRates.size() > 0){
			for (TheatreRate cr : theatreRates) {
				result += cr.getRate();
			}
			return result / theatreRates.size();
		}
			return 0;
		
	}
}
