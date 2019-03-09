package controllers;

import java.util.List;

import models.Auditorium;
import play.mvc.Controller;

public class Auditoriums extends Controller{
	public static void show(String mode, Long cinema_id)
	{
		List<Auditorium> auditoriums = Auditorium.find("cinema_id = ?", cinema_id).fetch();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(auditoriums,mode,cinema_id);
	}
	
	public static void create(Auditorium auditorium){
		auditorium.save();
		
		show("add",auditorium.getCinema().getId());
	}
	
	public static void edit(Auditorium auditorium){
		Auditorium c = Auditorium.findById(auditorium.id);
		c.setName(auditorium.getName());
		c.save();
		show("edit", auditorium.getCinema().getId());
		
	}
	
	public static void remove(Long id){
		Auditorium c = Auditorium.findById(id);
		c.delete();
		
	
		show("edit", null);
		
	}
}
