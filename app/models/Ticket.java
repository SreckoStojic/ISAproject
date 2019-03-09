package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import play.db.jpa.Model;

@Entity
public class Ticket extends Model {

	/*
	 * @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL) private User
	 * userId;
	 * 
	 * @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL) private Movie
	 * movieId;
	 * 
	 * @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL) private Performance
	 * performanceId;
	 * 
	 * @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL) private Seat
	 * seatId;
	 */

	@Column(nullable = false)
	private boolean reserved;

	@Column
	private int discount;

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	
}
