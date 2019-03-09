package controllers;

import java.io.File;
import java.util.List;

import models.CinemaRate;
import models.Movie;
import models.MovieRate;
import models.Projection;
import models.User;
import play.mvc.Controller;

public class Movies extends Controller{
	
	public static void show(String mode)
	{
		List<Movie> movies = Movie.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(movies,mode);
	}
	
	public static void create(Movie movie){
		File f = new File("IsaProj" + Movie.BASE_PATH + movie.getPosterFile().getName());
		movie.getPosterFile().renameTo(f);
		movie.setPoster(movie.getPosterFile().getName());
		movie.save();
		show("add");
	}
	
	public static void edit(Movie movie){
		Movie m = Movie.findById(movie.id);
		File f = new File("IsaProj" + Movie.BASE_PATH + movie.getPosterFile().getName());
		m.setTitle(movie.getTitle());
		m.setDuration(movie.getDuration());
		m.setDirector(movie.getDirector());
		m.setGenre(movie.getGenre());
		m.setPrice(movie.getPrice());
		movie.getPosterFile().renameTo(f);
		m.setPosterFile(f);
		m.save();
		show("edit");
		
	}
	
	public static void remove(Long id){
		Movie m = Movie.findById(id);
		m.delete();
	
		show("edit");
		
	}
	
	public static void list(){
		List<Movie> movies = Movie.findAll();
		
		renderTemplate("/Movies/list.html", movies);
	}
	
	public static void details(Long id){
		Movie movie = Movie.findById(id);
		renderTemplate("/Movies/details.html", movie);
	}
	
	public static double storeRate(MovieRate movieRate){
		User u = User.findByEmail(session.get("user"));
		MovieRate mr = MovieRate.find("user_id = ? AND movie_id = ?", u.getId(), movieRate.getMovie().getId()).first();
		if (mr != null) {
			mr.setRate(movieRate.getRate());
		} else {
			movieRate.setUser(u);
			mr = movieRate;
		}
		mr.save();
		return mr.getMovie().calculateAverageRate();
	}
	
	
}
