package models;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import play.db.jpa.Model;
import play.libs.Time;

@Entity
public class Performance extends Model {
	
	public static final String BASE_PATH = "/public/upload/posters/";
	
	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private int duration;

	@Column
	private double eval;
	
	@Column
	private String genre;
	
	@Column
	private String director;

	@Column
	private double price;
	
	@Column
	private String description;
	
	@Column
	private String poster;
	
	private File posterFile;
	
	@OneToMany(mappedBy = "performance", cascade = CascadeType.ALL)
	private List<TheatreProjection> theatreProjections;
	
	@OneToMany(mappedBy = "performance", cascade = CascadeType.ALL)
	private List<PerformanceRate> performanceRates;

	public List<PerformanceRate> getPerformanceRates() {
		return performanceRates;
	}

	public void setPerformanceRates(List<PerformanceRate> performanceRates) {
		this.performanceRates = performanceRates;
	}

	public void setTheatreProjections(List<TheatreProjection> theatreProjections) {
		this.theatreProjections = theatreProjections;
	}

	public File getPosterFile() {
		return posterFile;
	}

	public void setPosterFile(File posterFile) {
		this.posterFile = posterFile;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
	
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getEval() {
		return eval;
	}

	public void setEval(double eval) {
		this.eval = eval;
	}

	public List<TheatreProjection> getTheatreProjections() {
		return theatreProjections;
	}

	public void setProjections(List<TheatreProjection> theatreProjections) {
		this.theatreProjections = theatreProjections;
	}
	
	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	public String getPosterPath(){
		return BASE_PATH + posterFile.getName();
	}
	
	public List<TheatreProjection> getTheatreProjectionsByRepertoireId(Long rep_id){
		
		return TheatreProjection.find("performance_id = ? AND theatreRepertoire_id = ?", this.id, rep_id).fetch();
	}
	
	public double calculateAverageRate(){
		double result = 0;
		if (performanceRates.size() > 0){
			for (PerformanceRate mr : performanceRates) {
				result += mr.getRate();
			}
			return result / performanceRates.size();
		}
			return 0;
		
	}
}
