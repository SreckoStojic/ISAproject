package models;

import java.io.File;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import play.db.jpa.Model;

@Entity
public class User extends Model {

	public static final String BASE_PATH = "public/upload/users/";
	
	@Column(nullable = false)
	private boolean isAdmin;
	
	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Transient
	private String repeatPassword;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private UserRole userRole;

	@Column(nullable = false)
	private boolean verified;

	@Column(nullable = false)
	private String uuid;

	@Column(nullable = false)
	private String city;

	@Column(nullable = true)
	private Long phone;
	
	@Column
	private String profileImage;
	
	private File imageFile;

	@OneToMany
	private List<Friendship> friendships;

	@OneToMany(mappedBy = "user")
	private List<Reservation> reservations;	

	@OneToMany(mappedBy = "user")
	private List<TheatreReservation> theatreReservations;

	@OneToMany(mappedBy = "user")
	private List<CinemaRate> cinemaRates;	
	
	public List<CinemaRate> getCinemaRates() {
		return cinemaRates;
	}

	public void setCinemaRates(List<CinemaRate> cinemaRates) {
		this.cinemaRates = cinemaRates;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public List<Friendship> getFriendships() {
		return friendships;
	}

	public void setFriendships(List<Friendship> friendships) {
		this.friendships = friendships;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}	

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public static String getBasePath() {
		return BASE_PATH;
	}

	public String getImage(){
		String retVal = BASE_PATH + imageFile.getName();
		return retVal;
	}

	public List<TheatreReservation> getTheatreReservations() {
		return theatreReservations;
	}

	public void setTheatreReservations(List<TheatreReservation> theatreReservations) {
		this.theatreReservations = theatreReservations;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public static User findByEmail(String email){
		
		return User.find("email = ?", email).first();
	}
	
}
