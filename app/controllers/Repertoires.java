package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Cinema;
import models.Movie;
import models.Repertoire;
import play.mvc.Controller;

public class Repertoires extends Controller{
	public static void show(String mode)
	{
		List<Repertoire> repertoires = Repertoire.findAll();
		List<Cinema> cinemas = Cinema.findAll();
		List<Movie> movies = Movie.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(repertoires,cinemas,movies,mode);
	}
	
	public static void create(Repertoire repertoire, List<Long> movie_ids, long cinema_id){
		
		repertoire.setCinema((Cinema) Cinema.findById(cinema_id));
		List<Movie> movies = new ArrayList<Movie>();
		for (Long movie_id : movie_ids) {
			movies.add((Movie) Movie.findById(movie_id));
		}
		repertoire.setMovies(movies);
		repertoire.save();
		
		show("add");
	}
	
	public static void edit(Repertoire repertoire, List<Long> movie_ids){
		Repertoire c = Repertoire.findById(repertoire.id);
		List<Movie> movies = new ArrayList<Movie>();
		for (Long movie_id : movie_ids) {
			movies.add((Movie) Movie.findById(movie_id));
		}
		c.setMovies(movies);
		c.save();
		System.out.println(movie_ids);
		show("edit");
		
	}
	
	public static void remove(Long id){
		Repertoire c = Repertoire.findById(id);
		c.delete();
		
	
		show("edit");
		
	}
	
	public static void list(){
		List<Repertoire> repertoires = Repertoire.findAll();
		renderTemplate("/Repertoires/list.html", repertoires);
	}
	
	public static void details(Long id){
		Repertoire repertoire = Repertoire.findById(id);
		renderTemplate("/Repertoires/details.html", repertoire);
	}
}
