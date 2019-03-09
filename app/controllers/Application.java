package controllers;

import play.*;
import play.cache.Cache;
import play.mvc.*;
import play.mvc.Http.Cookie;

import java.util.*;

import org.hibernate.mapping.Array;

import models.*;

public class Application extends Controller {

	public static void index() {
		
		// populateDatabase();
		String mode = "normal";
		String val = "Login";
		User user = new User();
		if (session.get("user") != null) {
			
			mode = "logged";
			List<User> allUsers = User.findAll();
			for (User u : allUsers) {
				if (u.getEmail().equals(session.get("user"))) {
					user = User.findById(u.getId());
				}
			}
			if(user.getUserRole()==UserRole.USER){
				val = user.getUsername();
			}else if(user.getUserRole()==UserRole.ADMIN){
				val = "ADMIN";
			}
		}
		render(mode, val);
	}

	public static void admin() {
		renderTemplate("admin.html");
	}
	
	public static boolean isGuest(){
		return session.get("user") == null;
	}
	
	public static boolean isAdmin(){
		return session.get("isAdmin").equals("true");
	}

}