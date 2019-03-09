package controllers;

import java.io.File;
import java.util.List;

import models.Performance;
import models.PerformanceRate;
import models.User;
import play.mvc.Controller;

public class Performances extends Controller{
	
	public static void show(String mode)
	{
		List<Performance> performances = Performance.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(performances,mode);
	}
	
	public static void create(Performance performance){
		File f = new File("IsaProj" + Performance.BASE_PATH + performance.getPosterFile().getName());
		performance.getPosterFile().renameTo(f);
		performance.setPoster(performance.getPosterFile().getName());
		performance.save();
		show("add");
	}
	
	public static void edit(Performance performance){
		Performance m = Performance.findById(performance.id);
		File f = new File("IsaProj" + Performance.BASE_PATH + performance.getPosterFile().getName());
		m.setTitle(performance.getTitle());
		m.setDuration(performance.getDuration());
		m.setDirector(performance.getDirector());
		m.setGenre(performance.getGenre());
		m.setPrice(performance.getPrice());
		performance.getPosterFile().renameTo(f);
		m.setPosterFile(f);
		m.save();
		show("edit");
		
	}
	
	public static void remove(Long id){
		Performance m = Performance.findById(id);
		m.delete();
	
		show("edit");
		
	}
	
	public static void list(){
		List<Performance> performances = Performance.findAll();
		
		renderTemplate("/Performances/list.html", performances);
	}
	
	public static void details(Long id){
		Performance performance = Performance.findById(id);
		renderTemplate("/Performances/details.html", performance);
	}
	
	public static double storeRate(PerformanceRate performanceRate){
		User u = User.findByEmail(session.get("user"));
		PerformanceRate mr = PerformanceRate.find("user_id = ? AND performance_id = ?", u.getId(), performanceRate.getPerformance().getId()).first();
		if (mr != null) {
			mr.setRate(performanceRate.getRate());
		} else {
			performanceRate.setUser(u);
			mr = performanceRate;
		}
		mr.save();
		return mr.getPerformance().calculateAverageRate();
	}
	
	
}