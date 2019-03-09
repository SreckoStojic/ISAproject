package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Auditorium;
import models.Cinema;
import models.Movie;
import models.Projection;
import models.ProjectionDate;
import models.ProjectionTime;
import models.Repertoire;
import models.Reservation;
import models.Seat;
import models.User;
import play.mvc.Controller;

public class Reservations extends Controller {
	public static void show(String mode, Long rep_id, Long movie_id,
			Long date_id, Long time_id, Long seat_id, Long cinema_id,
			List<Integer> seat_rows, List<Integer> seat_numbers) {
		User user = new User();
		if (session.get("user") == null) {
			renderTemplate("Users/login.html", user);
		}
		{
			user = (User) (User.find("email = ?", session.get("user")).fetch())
					.get(0);
			double price = 0;
			if (mode == null || mode.equals("")) {
				mode = "initial";

				seat_rows = new ArrayList<>();
				seat_numbers = new ArrayList<>();
			}
			List<Cinema> cinemas = Cinema.findAll();
			List<Movie> movies = Movie.findAll();
			List<ProjectionDate> projectionDates = ProjectionDate.findAll();
			List<ProjectionTime> projectionTimes = ProjectionTime.findAll();
			List<Auditorium> auditoriums = Auditorium.findAll();
			if (mode.equals("next")) {
				List<Projection> projections = Projection.find(
						"repertoire_id = ? AND movie_id = ?", rep_id, movie_id)
						.fetch();
				projectionDates = new ArrayList<>();
				Repertoire repertoire = Repertoire.findById(rep_id);
				movies = new ArrayList<>();
				movies.add((Movie) Movie.findById(movie_id));
				for (Movie movie : repertoire.getMovies()) {
					if (movie.getId() != movie_id) {
						movies.add(movie);
					}
				}
				for (Projection projection : projections) {
					projectionDates.add(projection.getProjectionDate());
				}
			} else if (mode.equals("seats")) {
				movies = new ArrayList<>();
				auditoriums = new ArrayList<>();
				projectionDates = new ArrayList<>();
				List<Projection> projections = Projection
						.find("repertoire_id = ? AND movie_id = ? AND projectionDate_id = ? AND projectionTime_id = ?",
								rep_id, movie_id, date_id, time_id).fetch();

				Repertoire repertoire = Repertoire.findById(rep_id);
				movies = new ArrayList<>();
				movies.add((Movie) Movie.findById(movie_id));

				List<Projection> projections1 = Projection.find(
						"repertoire_id = ? AND movie_id = ?", rep_id, movie_id)
						.fetch();
				for (Projection projection : projections1) {
					projectionDates.add(projection.getProjectionDate());
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

				for (Movie movie : repertoire.getMovies()) {
					if (movie.getId() != movie_id) {
						movies.add(movie);
					}
				}
				for (Projection projection : projections) {
					auditoriums.add(projection.getAuditorium());
				}
				ProjectionTime projectionTime = ProjectionTime
						.findById(time_id);
				price = projectionTime.getPrice();

			}

			render(mode, cinemas, movies, projectionDates, projectionTimes,
					auditoriums, rep_id, movie_id, date_id, time_id, seat_id,
					cinema_id, price, seat_rows, seat_numbers);
		}
	}

	public static void next(Long cinema_id, Long movie_id,
			List<Integer> seat_rows, List<Integer> seat_numbers) {
		String mode = "next";
		Cinema cinema = Cinema.findById(cinema_id);
		Long rep_id = cinema.getRepertoires().get(0).getId();
		show(mode, rep_id, movie_id, null, null, null, cinema_id, seat_rows,
				seat_numbers);
	}

	public static void getSeat(Long rep_id, Long movie_id, Long date_id,
			Long time_id, Long cinema_id, List<Integer> seat_rows,
			List<Integer> seat_numbers) {
		String mode = "seats";
		show(mode, rep_id, movie_id, date_id, time_id, null, cinema_id,
				seat_rows, seat_numbers);
	}

	public static void create(Long rep_id, Long movie_id, Long date_id,
			Long time_id, Long cinema_id, Long aud_id, List<Integer> seat_rows,
			List<Integer> seat_numbers) {

		for (int i = 0; i < seat_rows.size(); i++) {
			Reservation reservation = new Reservation();
			Auditorium auditorium = Auditorium.findById(aud_id);
			Seat seat = new Seat();
			User user = (User) (User.find("email = ?", session.get("user"))
					.fetch()).get(0);

			for (Seat s : auditorium.getSeats()) {
				if (s.getRow() == seat_rows.get(i)
						&& s.getNumber() == seat_numbers.get(i)) {
					s.setAvailable(false);
					s.save();
					seat = Seat.findById(s.getId());
				}
			}
			auditorium.save();
			List<Projection> projections = Projection
					.find("repertoire_id = ? AND movie_id = ? AND projectionDate_id = ? AND projectionTime_id = ? AND auditorium_id = ?",
							rep_id, movie_id, date_id, time_id, aud_id).fetch();

			if (projections.size() == 1) {
				reservation.setProjection(projections.get(0));
				reservation.setSeat(seat);
				reservation.setUser(user);
				reservation.create();
			}
		}
		Users.show();
	}

	public static void drop() {
		String mode = "initial";
		show(mode, null, null, null, null, null, null, null, null);
	}
	
	
	
	
	///quick reservation
	public static void quickReservation(Long projection_id){
		Projection p = Projection.findById(projection_id);
		User u = User.findByEmail(session.get("user"));
		Seat s = Seat.find("auditorium_id = ? AND available = 1", p.getAuditorium().getId()).first();
		if (s != null){
			Reservation r = new Reservation();
			r.setProjection(p);
			r.setSeat(s);
			r.setUser(u);
			s.setAvailable(false);
			s.save();
			r.save();
			Users.show();
		}
	}
}
