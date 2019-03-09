package controllers;

import java.io.File;
import java.util.List;

import models.ProjectionDate;
import play.mvc.Controller;

public class ProjectionDates extends Controller{
	
	public static void show(String mode)
	{
		List<ProjectionDate> projectionDates = ProjectionDate.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		
		render(projectionDates,mode);
	}
	
	public static void create(ProjectionDate projectionDate){

		projectionDate.save();
		show("add");
	}
	
	public static void edit(ProjectionDate projectionDate){
		ProjectionDate m = ProjectionDate.findById(projectionDate.id);
		m.setDate(projectionDate.getDate());
		m.save();
		show("edit");
		
	}
	
	public static void remove(Long id){
		ProjectionDate m = ProjectionDate.findById(id);
		m.delete();
	
		show("edit");
		
	}
}
