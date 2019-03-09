package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Performance;
import models.ProjectionDate;
import models.ProjectionTime;
import models.Theatre;
import models.TheatreAuditorium;
import models.TheatreProjection;
import models.TheatreRepertoire;
import models.TheatreReservation;
import models.TheatreSeat;
import models.User;
import play.mvc.Controller;

public class TheatreReservations extends Controller {
	public static void show(String mode, Long rep_id, Long performance_id,
			Long date_id, Long time_id, Long theatreSeat_id, Long theatre_id,
			List<Integer> theatre_seat_rows, List<Integer> theatre_seat_numbers) {
		User user = new User();
		if (session.get("user") == null) {
			renderTemplate("Users/login.html", user);
		}
		{
			user = (User) (User.find("email = ?", session.get("user")).fetch())
					.get(0);
			double price = 0;
			List<User> allUsers = User.findAll();
			for (User u : allUsers) {
				if (u.getEmail().equals(session.get("user"))) {
					user = User.findById(u.getId());
				}
			}
			if (mode == null || mode.equals("")) {
				mode = "initial";

				theatre_seat_rows = new ArrayList<>();
				theatre_seat_numbers = new ArrayList<>();
			}
			List<Theatre> theatres = Theatre.findAll();
			List<Performance> performances = Performance.findAll();
			List<ProjectionDate> projectionDates = ProjectionDate.findAll();
			List<ProjectionTime> projectionTimes = ProjectionTime.findAll();
			List<TheatreAuditorium> theatreAuditoriums = TheatreAuditorium
					.findAll();
			if (mode.equals("next")) {
				List<TheatreProjection> theatreProjections = TheatreProjection
						.find("theatreRepertoire_id = ? AND performance_id = ?",
								rep_id, performance_id).fetch();
				projectionDates = new ArrayList<>();
				TheatreRepertoire theatreRepertoire = TheatreRepertoire
						.findById(rep_id);
				performances = new ArrayList<>();
				performances.add((Performance) Performance
						.findById(performance_id));
				for (Performance performance : theatreRepertoire
						.getPerformances()) {
					if (performance.getId() != performance_id) {
						performances.add(performance);
					}
				}
				for (TheatreProjection theatreProjection : theatreProjections) {
					projectionDates.add(theatreProjection.getProjectionDate());
				}
			} else if (mode.equals("seats")) {
				performances = new ArrayList<>();
				theatreAuditoriums = new ArrayList<>();
				projectionDates = new ArrayList<>();
				List<TheatreProjection> theatreProjections = TheatreProjection
						.find("theatreRepertoire_id = ? AND performance_id = ? AND projectionDate_id = ? AND projectionTime_id = ?",
								rep_id, performance_id, date_id, time_id)
						.fetch();

				TheatreRepertoire theatreRepertoire = TheatreRepertoire
						.findById(rep_id);
				performances = new ArrayList<>();
				performances.add((Performance) Performance
						.findById(performance_id));

				List<TheatreProjection> theatreProjections1 = TheatreProjection
						.find("theatreRepertoire_id = ? AND performance_id = ?",
								rep_id, performance_id).fetch();
				for (TheatreProjection theatreProjection : theatreProjections1) {
					projectionDates.add(theatreProjection.getProjectionDate());
				}
				projectionDates.remove(ProjectionDate.findById(date_id));
				projectionDates.add(0,
						(ProjectionDate) ProjectionDate.findById(date_id));

				projectionTimes = new ArrayList<>();
				ProjectionDate projectionDate = ProjectionDate
						.findById(date_id);
				for (ProjectionTime projectionTime : projectionDate
						.getProjectionTimes()) {
					projectionTimes.add(projectionTime);
				}
				projectionTimes.remove(ProjectionTime.findById(time_id));
				projectionTimes.add(0,
						(ProjectionTime) ProjectionTime.findById(time_id));

				for (Performance performance : theatreRepertoire
						.getPerformances()) {
					if (performance.getId() != performance_id) {
						performances.add(performance);
					}
				}
				for (TheatreProjection theatreProjection : theatreProjections) {
					theatreAuditoriums.add(theatreProjection
							.getTheatreAuditorium());
				}
				ProjectionTime projectionTime = ProjectionTime
						.findById(time_id);
				price = projectionTime.getPrice();

			}
			System.out.println(mode + " " + performance_id);
			render(mode, theatres, performances, projectionDates,
					projectionTimes, theatreAuditoriums, rep_id,
					performance_id, date_id, time_id, theatreSeat_id,
					theatre_id, price, theatre_seat_rows, theatre_seat_numbers);
		}
	}

	public static void next(Long theatre_id, Long performance_id,
			List<Integer> theatre_seat_rows, List<Integer> theatre_seat_numbers) {
		String mode = "next";
		Theatre theatre = Theatre.findById(theatre_id);
		System.out.println(mode + " " + theatre_id);
		Long rep_id = theatre.getTheatreRepertoires().get(0).getId();
		show(mode, rep_id, performance_id, null, null, null, theatre_id,
				theatre_seat_rows, theatre_seat_numbers);
	}

	public static void getTheatreSeat(Long rep_id, Long performance_id,
			Long date_id, Long time_id, Long theatre_id,
			List<Integer> theatre_seat_rows, List<Integer> theatre_seat_numbers) {
		String mode = "theatreSeats";
		System.out.println(mode + " " + theatre_id);
		show(mode, rep_id, performance_id, date_id, time_id, null, theatre_id,
				theatre_seat_rows, theatre_seat_numbers);
	}

	public static void create(Long rep_id, Long performance_id, Long date_id,
			Long time_id, Long theatre_id, Long theatreAud_id,
			List<Integer> theatre_seat_rows, List<Integer> theatre_seat_numbers) {

		for (int i = 0; i < theatre_seat_rows.size(); i++) {
			TheatreReservation theatreReservation = new TheatreReservation();
			TheatreAuditorium theatreAuditorium = TheatreAuditorium
					.findById(theatreAud_id);
			TheatreSeat theatreSeat = new TheatreSeat();
			User user = new User();
			List<User> allUsers = User.findAll();
			for (User u : allUsers) {
				if (u.getEmail().equals(session.get("user"))) {
					user = User.findById(u.getId());
				}
			}
			for (TheatreSeat s : theatreAuditorium.getTheatreSeats()) {

				if (s.getRow() == theatre_seat_rows.get(i)
						&& s.getNumber() == theatre_seat_numbers.get(i)) {
					s.setAvailable(false);
					s.save();
					theatreSeat = TheatreSeat.findById(s.getId());
				}
			}
			theatreAuditorium.save();
			List<TheatreProjection> theatreProjections = TheatreProjection
					.find("theatreRepertoire_id = ? AND performance_id = ? AND projectionDate_id = ? AND projectionTime_id = ? AND theatreAuditorium_id = ?",
							rep_id, performance_id, date_id, time_id,
							theatreAud_id).fetch();

			if (theatreProjections.size() == 1) {
				theatreReservation.setTheatreProjection(theatreProjections
						.get(0));
				theatreReservation.setTheatreSeat(theatreSeat);
				theatreReservation.setUser(user);
				theatreSeat.save();
				theatreReservation.create();
			}
		}
		Users.show();
	}
}
