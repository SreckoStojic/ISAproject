package controllers;

import java.util.List;

import models.Cinema;
import models.CinemaRate;
import models.Repertoire;
import models.User;
import play.mvc.Controller;


public class Cinemas extends Controller{
	public static void show(String mode)
	{
		List<Cinema> cinemas = Cinema.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(cinemas,mode);
	}
	
	public static void create(Cinema cinema){
		
		cinema.save();
		
		show("add");
	}
	
	public static void edit(Cinema cinema){
		Cinema c = Cinema.findById(cinema.id);
		c.setName(cinema.getName());
		c.setAddress(cinema.getAddress());
		c.setCity(cinema.getCity());
		c.setDescription(cinema.getDescription());
		c.save();
		show("edit");
		
	}
	
	public static void remove(Long id){
		Cinema c = Cinema.findById(id);
		c.delete();
		
	
		show("edit");
		
	}
	
	public static void list(){
		List<Cinema> cinemas = Cinema.findAll();
		
		renderTemplate("/Cinemas/list.html", cinemas);
	}
	
	public static void details(Long id){
		Cinema cinema = Cinema.findById(id);
		List<Repertoire> reps = (List<Repertoire>) cinema.getRepertoires();
		Repertoire repertoire = reps.get(0);
		
		renderTemplate("/Cinemas/details.html", cinema,repertoire);
	}

	public static double storeRate(CinemaRate cinemaRate){
		User u = User.findByEmail(session.get("user"));
		CinemaRate cr = CinemaRate.find("user_id = ? AND cinema_id = ?", u.getId(), cinemaRate.getCinema().getId()).first();
		if (cr != null) {
			cr.setRate(cinemaRate.getRate());
		} else {
			cinemaRate.setUser(u);
			cr = cinemaRate;
		}
		cr.save();
		return cr.getCinema().calculateAverageRate();
	}
}
