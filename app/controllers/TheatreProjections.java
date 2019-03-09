package controllers;

import java.util.List;

import models.TheatreAuditorium;
import models.Performance;
import models.TheatreProjection;
import models.ProjectionDate;
import models.ProjectionTime;
import models.TheatreRepertoire;
import play.mvc.Controller;

public class TheatreProjections extends Controller{
	public static void show(String mode, Long rep_id, Long performance_id)
	{
		TheatreRepertoire theatreRepertoire = TheatreRepertoire.findById(rep_id);
		Performance performance = Performance.findById(performance_id);
		List<TheatreProjection> theatreProjections = TheatreProjection.find("theatreRepertoire_id = ? AND performance_id = ?", rep_id, performance_id).fetch();
		List<ProjectionDate> projectionDates = ProjectionDate.findAll();
		List<ProjectionTime> projectionTimes = ProjectionTime.findAll();
		List<TheatreAuditorium> theatreAuditoriums = TheatreAuditorium.find("theatre_id = ?", theatreRepertoire.getTheatre().getId()).fetch();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(theatreProjections,theatreRepertoire,performance,rep_id, performance_id, projectionDates,projectionTimes,mode,theatreAuditoriums);
	}
	
	public static void create(TheatreProjection theatreProjection){
		
		theatreProjection.save();
		
		show("add", theatreProjection.getTheatreRepertoire().getId(), theatreProjection.getPerformance().getId());
	}
	
	public static void edit(TheatreProjection theatreProjection){
		
		TheatreProjection p = TheatreProjection.findById(theatreProjection.id);
		p.setTheatreAuditorium(theatreProjection.getTheatreAuditorium());
		p.setPerformance(theatreProjection.getPerformance());
		p.setProjectionDate(theatreProjection.getProjectionDate());
		p.setProjectionTime(theatreProjection.getProjectionTime());
		show("edit", theatreProjection.getTheatreRepertoire().getId(), theatreProjection.getPerformance().getId());
		
	}
	
	public static void remove(Long id){
		TheatreProjection c = TheatreProjection.findById(id);
		c.delete();
		
	
		show("edit", c.getTheatreRepertoire().getId(), c.getPerformance().getId());
		
	}

}