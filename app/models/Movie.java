package models;

import java.io.File;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Movie extends Model {
	
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
	
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	private List<Projection> projections;
	
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	private List<MovieRate> movieRates;

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

	public List<Projection> getProjections() {
		return projections;
	}

	public void setProjections(List<Projection> projections) {
		this.projections = projections;
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
	
	public List<Projection> getProjectionsByRepertoireId(Long rep_id){
		
		return Projection.find("movie_id = ? AND repertoire_id = ?", this.id, rep_id).fetch();
	}
	
	public double calculateAverageRate(){
		double result = 0;
		if (movieRates.size() > 0){
			for (MovieRate mr : movieRates) {
				result += mr.getRate();
			}
			return result / movieRates.size();
		}
			return 0;
		
	}
}
