package controllers;

import java.util.List;

import models.ProjectionTime;
import play.mvc.Controller;

public class ProjectionTimes extends Controller{
	public static void show(String mode, Long projectionDate_id)
	{
		List<ProjectionTime> projectionTimes = ProjectionTime.find("projectionDate_id = ?", projectionDate_id).fetch();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(projectionTimes,mode,projectionDate_id);
	}
	
	public static void create(ProjectionTime projectionTime){
		
		projectionTime.save();
		
		show("add",projectionTime.getProjectionDate().getId());
	}
	
	public static void edit(ProjectionTime projectionTime){
		ProjectionTime c = ProjectionTime.findById(projectionTime.id);
		c.setTime(projectionTime.getTime());
		c.setPrice(projectionTime.getPrice());
		c.save();
		show("edit", projectionTime.getProjectionDate().getId());
		
	}
	
	public static void remove(Long id){
		ProjectionTime c = ProjectionTime.findById(id);
		c.delete();
		show("edit", null);
	}
}
