package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Theatre;
import models.Performance;
import models.TheatreRepertoire;
import play.mvc.Controller;

public class TheatreRepertoires extends Controller{
	public static void show(String mode)
	{
		List<TheatreRepertoire> theatreRepertoires = TheatreRepertoire.findAll();
		List<Theatre> theatres = Theatre.findAll();
		List<Performance> performances = Performance.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(theatreRepertoires,theatres,performances,mode);
	}
	
	public static void create(TheatreRepertoire theatreRepertoire, List<Long> performance_ids, long theatre_id){
		
		theatreRepertoire.setTheatre((Theatre) Theatre.findById(theatre_id));
		List<Performance> performances = new ArrayList<Performance>();
		for (Long performance_id : performance_ids) {
			performances.add((Performance) Performance.findById(performance_id));
		}
		theatreRepertoire.setPerformances(performances);
		theatreRepertoire.save();
		
		show("add");
	}
	
	public static void edit(TheatreRepertoire theatreRepertoire, List<Long> performance_ids){
		TheatreRepertoire c = TheatreRepertoire.findById(theatreRepertoire.id);
		List<Performance> performances = new ArrayList<Performance>();
		for (Long performance_id : performance_ids) {
			performances.add((Performance) Performance.findById(performance_id));
		}
		c.setPerformances(performances);
		c.save();
		System.out.println(performance_ids);
		show("edit");
		
	}
	
	public static void remove(Long id){
		TheatreRepertoire c = TheatreRepertoire.findById(id);
		c.delete();
		
	
		show("edit");
		
	}
	
	public static void list(){
		List<TheatreRepertoire> theatreRepertoires = TheatreRepertoire.findAll();
		renderTemplate("/TheatreRepertoires/list.html", theatreRepertoires);
	}
	
	public static void details(Long id){
		TheatreRepertoire theatreRepertoire = TheatreRepertoire.findById(id);
		renderTemplate("/TheatreRepertoiress/details.html", theatreRepertoire);
	}
}
