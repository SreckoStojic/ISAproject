package controllers;

import java.util.List;

import models.Auditorium;
import models.Movie;
import models.Projection;
import models.ProjectionDate;
import models.ProjectionTime;
import models.Repertoire;
import play.mvc.Controller;

public class Projections extends Controller{
	public static void show(String mode, Long rep_id, Long movie_id)
	{
		Repertoire repertoire = Repertoire.findById(rep_id);
		Movie movie = Movie.findById(movie_id);
		List<Projection> projections = Projection.find("repertoire_id = ? AND movie_id = ?", rep_id, movie_id).fetch();
		List<ProjectionDate> projectionDates = ProjectionDate.findAll();
		List<ProjectionTime> projectionTimes = ProjectionTime.findAll();
		List<Auditorium> auditoriums = Auditorium.find("cinema_id = ?", repertoire.getCinema().getId()).fetch();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(projections,repertoire,movie,rep_id, movie_id, projectionDates,projectionTimes,mode,auditoriums);
	}
	
	public static void create(Projection projection){
		
		projection.save();
		
		show("add", projection.getRepertoire().getId(), projection.getMovie().getId());
	}
	
	public static void edit(Projection projection){
		
		Projection p = Projection.findById(projection.id);
		p.setAuditorium(projection.getAuditorium());
		p.setMovie(projection.getMovie());
		p.setProjectionDate(projection.getProjectionDate());
		p.setProjectionTime(projection.getProjectionTime());
		show("edit", projection.getRepertoire().getId(), projection.getMovie().getId());
		
	}
	
	public static void remove(Long id){
		Projection c = Projection.findById(id);
		c.delete();
		
	
		show("edit", c.getRepertoire().getId(), c.getMovie().getId());
		
	}

}
