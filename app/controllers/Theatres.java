package controllers;

import java.util.List;

import models.Theatre;
import models.TheatreRate;
import models.User;
import play.mvc.Controller;

public class Theatres extends Controller{
	public static void show(String mode)
	{
		List<Theatre> theatres = Theatre.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(theatres,mode);
	}
	
	public static void create(Theatre theatre){
		
		theatre.save();
		
		show("add");
	}
	
	public static void edit(Theatre theatre){
		Theatre c = Theatre.findById(theatre.id);
		c.setName(theatre.getName());
		c.setAddress(theatre.getAddress());
		c.setCity(theatre.getCity());
		c.setDescription(theatre.getDescription());
		c.save();
		show("edit");
		
	}
	
	public static void remove(Long id){
		Theatre c = Theatre.findById(id);
		c.delete();
		
	
		show("edit");
		
	}
	
	public static void list(){
		List<Theatre> theatres = Theatre.findAll();
		
		renderTemplate("/Theatres/list.html", theatres);
	}
	
	public static void details(Long id){
		Theatre theatre = Theatre.findById(id);
		renderTemplate("/Theatres/details.html", theatre);
	}
	
	public static double storeRate(TheatreRate theatreRate){
		User u = User.findByEmail(session.get("user"));
		TheatreRate cr = TheatreRate.find("user_id = ? AND theatre_id = ?", u.getId(), theatreRate.getTheatre().getId()).first();
		if (cr != null) {
			cr.setRate(theatreRate.getRate());
		} else {
			theatreRate.setUser(u);
			cr = theatreRate;
		}
		cr.save();
		return cr.getTheatre().calculateAverageRate();
	}
}