package controllers;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import models.Cinema;
import models.ProjectionDate;
import models.ProjectionTime;
import models.Reservation;
import models.Seat;
import models.Theatre;
import models.TheatreReservation;
import models.TheatreSeat;
import models.User;
import models.UserRole;
import play.mvc.Controller;

public class Users extends Controller {
	public static void show() {

		List<Cinema> cinemas = Cinema.findAll();
		List<Theatre> theatres = Theatre.findAll();
		List<Reservation> reservations = new ArrayList<>();
		List<Reservation> activeReservations = new ArrayList<>();
		List<TheatreReservation> theatreReservations = new ArrayList<>();
		List<TheatreReservation> activeTheatreReservations = new ArrayList<>();
		User user = new User();
		if (session.get("user") == null) {
			renderTemplate("Users/login.html", user);
		} else {

			user = (User) (User.find("email = ?", session.get("user")).fetch())
					.get(0);
			activeReservations = getActiveReservations();
			activeTheatreReservations = getActiveTheatreReservations();
			if (user.getReservations().size() > 0) {
				for (Reservation reservation : user.getReservations()) {
					if (!activeReservations.contains(reservation)) {
						reservations.add(reservation);
					}
				}
				for (TheatreReservation theatreReservation : user
						.getTheatreReservations()) {
					if (!activeTheatreReservations.contains(theatreReservation)) {
						theatreReservations.add(theatreReservation);
					}
				}
			}
			renderTemplate("Users/show.html", user, cinemas, theatres,
					reservations, activeReservations,
					activeTheatreReservations, theatreReservations);
		}
	}

	public static void login() {
		User user = new User();
		if (session.get("user") == null) {
			renderTemplate("Users/login.html", user);
		} else {
			show();
		}
	}

	public static void authenticate(User user) {
		String error = "";
		List<User> allUsers = User.findAll();
		for (User u : allUsers) {
			if (u.getUsername().equals(user.getUsername())
					&& u.getPassword().equals(user.getPassword())) {
				user = User.findById(u.getId());
			}
		}
		if (user.getId() == null) {
			error = "You don't have an account!";
			renderTemplate("Users/message.html", error);
		} else if (user.isVerified() == false) {
			error = "You must verify your account! Please check your email and then try again!";
			renderTemplate("Users/message.html", error);
		}
		if (user.getUserRole() == UserRole.USER) {
			user.save();
			session.put("user", user.getEmail());
			session.put("isAdmin", false);
			show();
		} else if (user.getUserRole() == UserRole.ADMIN) {
			session.put("user", user.getEmail());
			session.put("isAdmin", true);
			Application.admin();
		}
	}

	public static void register(User user) {
		List<User> allUsers = User.findAll();
		String error = "";
		System.out.println("2" + user.getUsername() + " " + user.getPassword());
		for (User u : allUsers) {
			if (u.getEmail().equals(user.getEmail())) {
				error = "User with this email already exists!";
				renderTemplate("Users/message.html", error);
			}
		}
		if (!user.getPassword().equals(user.getRepeatPassword())) {
			error = "Password and repeated password are not the same!";
			renderTemplate("Users/message.html", error);
		}
		if (user.getEmail().equals("") || user.getUsername().equals("")
				|| user.getPassword().equals("")
				|| user.getFirstName().equals("")
				|| user.getLastName().equals("") || user.getCity().equals("")) {
			error = "You must fill all fields!";
			renderTemplate("Users/message.html", error);
		}
		final String username = "obrensar@gmail.com";
		final String password = "adminadmin123";

		UUID uuid = UUID.randomUUID();

		for (User u : allUsers) {
			if (u.getUuid().equals(uuid.toString())) {
				uuid = UUID.randomUUID();
			}
		}
		user.setUuid(uuid.toString());

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("obrensar@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(user.getEmail()));
			message.setSubject("ISA - confirm registration");
			message.setText("In order to complete registration plese click on link below,\n"
					+ "http://localhost:9000/users/confirm?uuid=" + uuid);

			Transport.send(message);

			System.out.println("Sent");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		user.setVerified(false);
		user.setUserRole(UserRole.USER);
		File f = new File("IsaProj" + User.BASE_PATH + "user_logo.jpg");
		user.setImageFile(f);
		user.setProfileImage(user.getImageFile().getName());
		user.create();
		renderTemplate("Users/show.html", user);
	}

	public static void confirm(String uuid) {
		User user = new User();
		List<User> allUsers = User.findAll();
		for (User u : allUsers) {
			if (u.getUuid().equals(uuid)) {
				user = User.findById(u.getId());
			}
		}
		user.setVerified(true);
		user.save();
		session.put("user", user.getEmail());
		renderTemplate("Users/show.html", user);
	}

	public static void edit(User user) {
		User u = User.findById(user.getId());
		u.setUsername(user.getUsername());
		u.setLastName(user.getLastName());
		u.setFirstName(user.getFirstName());
		u.setEmail(user.getEmail());
		u.setPassword(user.getPassword());
		u.setUuid(user.getUuid());
		u.setUserRole(user.getUserRole());
		u.setPhone(user.getPhone());
		u.setCity(user.getCity());
		File f = new File(User.BASE_PATH + user.getImageFile().getName());

		user.getImageFile().renameTo(f);

		u.setImageFile(f);
		u.setProfileImage(f.getName());
		u.save();
		renderTemplate("Users/show.html", u);
	}

	public static void logout() {
		session.clear();
/*		session.remove("user");
		session.remove("isAdmin");*/
		Application.index();
	}

	public static void cancel(Long id) {
		Reservation reservation = Reservation.findById(id);
		Seat seat = reservation.getSeat();
		seat.setAvailable(true);
		seat.save();
		reservation.delete();
		show();
	}

	public static void cancelTheatre(Long id) {
		TheatreReservation reservation = TheatreReservation.findById(id);
		TheatreSeat seat = reservation.getTheatreSeat();
		seat.setAvailable(true);
		seat.save();
		reservation.delete();
		show();
	}

	private static List<Reservation> getActiveReservations() {
		List<Reservation> activeReservations = new ArrayList<>();
		int year, month, day, hours, minutes;
		int yearNow, monthNow, dayNow, hoursNow, minutesNow;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String dateNow = dateFormat.format(cal.getTime()).split(" ")[0];
		String timeNow = dateFormat.format(cal.getTime()).split(" ")[1];
		yearNow = Integer.parseInt(dateNow.split("/")[0]);
		monthNow = Integer.parseInt(dateNow.split("/")[1]);
		dayNow = Integer.parseInt(dateNow.split("/")[2]);
		hoursNow = Integer.parseInt(timeNow.split(":")[0]);
		minutesNow = Integer.parseInt(timeNow.split(":")[1]);

		User user = (User) (User.find("email = ?", session.get("user")).fetch())
				.get(0);

		for (Reservation reservation : user.getReservations()) {
			ProjectionDate projectionDate = reservation.getProjection()
					.getProjectionDate();
			ProjectionTime projectionTime = reservation.getProjection()
					.getProjectionTime();
			String date = projectionDate.getDate().toString().split(" ")[0];
			String time = projectionTime.getTime();
			year = Integer.parseInt(date.split("-")[0]);
			month = Integer.parseInt(date.split("-")[1]);
			day = Integer.parseInt(date.split("-")[2]);
			hours = Integer.parseInt(time.split(":")[0]);
			minutes = Integer.parseInt(time.split(":")[1]);
			if (year >= yearNow && month >= monthNow && day > dayNow) {
				activeReservations.add(reservation);
			} else if (year >= yearNow && month >= monthNow && day >= dayNow && hours > hoursNow) {
				activeReservations.add(reservation);
			} else if (year >= yearNow && month >= monthNow && day >= dayNow && hours == hoursNow && minutes - 30 >= minutesNow) {
				activeReservations.add(reservation);
			}else{
				reservation.delete();
			}
		}

		return activeReservations;
	}

	private static List<TheatreReservation> getActiveTheatreReservations() {
		List<TheatreReservation> activeReservations = new ArrayList<>();
		int year, month, day, hours, minutes;
		int yearNow, monthNow, dayNow, hoursNow, minutesNow;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String dateNow = dateFormat.format(cal.getTime()).split(" ")[0];
		String timeNow = dateFormat.format(cal.getTime()).split(" ")[1];
		yearNow = Integer.parseInt(dateNow.split("/")[0]);
		monthNow = Integer.parseInt(dateNow.split("/")[1]);
		dayNow = Integer.parseInt(dateNow.split("/")[2]);
		hoursNow = Integer.parseInt(timeNow.split(":")[0]);
		minutesNow = Integer.parseInt(timeNow.split(":")[1]);

		User user = (User) (User.find("email = ?", session.get("user")).fetch())
				.get(0);

		for (TheatreReservation reservation : user.getTheatreReservations()) {
			ProjectionDate projectionDate = reservation.getTheatreProjection()
					.getProjectionDate();
			ProjectionTime projectionTime = reservation.getTheatreProjection()
					.getProjectionTime();
			String date = projectionDate.getDate().toString().split(" ")[0];
			String time = projectionTime.getTime();
			year = Integer.parseInt(date.split("-")[0]);
			month = Integer.parseInt(date.split("-")[1]);
			day = Integer.parseInt(date.split("-")[2]);
			hours = Integer.parseInt(time.split(":")[0]);
			minutes = Integer.parseInt(time.split(":")[1]);
			if (year >= yearNow && month >= monthNow && day > dayNow) {
				activeReservations.add(reservation);
			} else if (year >= yearNow && month >= monthNow && day >= dayNow && hours > hoursNow) {
				activeReservations.add(reservation);
			} else if (year >= yearNow && month >= monthNow && day >= dayNow && hours == hoursNow && minutes - 30 >= minutesNow) {
				activeReservations.add(reservation);
			}else{
				reservation.delete();
			}
		}

		return activeReservations;
	}
}
