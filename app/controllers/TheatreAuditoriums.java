package controllers;

import java.util.List;

import models.Auditorium;
import models.TheatreAuditorium;
import play.mvc.Controller;

public class TheatreAuditoriums extends Controller{
	public static void show(String mode, Long theatre_id)
	{
		List<TheatreAuditorium> theatreAuditoriums = TheatreAuditorium.find("theatre_id = ?", theatre_id).fetch();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(theatreAuditoriums,mode,theatre_id);
	}
	
	public static void create(TheatreAuditorium theatreAuditorium){
		theatreAuditorium.save();
		
		show("add",theatreAuditorium.getTheatre().getId());
	}
	
	public static void edit(TheatreAuditorium theatreAuditorium){
		TheatreAuditorium c = TheatreAuditorium.findById(theatreAuditorium.id);
		c.setName(theatreAuditorium.getName());
		c.save();
		show("edit", theatreAuditorium.getTheatre().getId());
		
	}
	
	public static void remove(Long id){
		TheatreAuditorium c = TheatreAuditorium.findById(id);
		c.delete();
		
	
		show("edit", null);
		
	}
}

