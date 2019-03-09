package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import play.db.jpa.Model;

@Entity
public class ProjectionDate extends Model {

	// format parameter to small date
	@Column(nullable = false)
	private Date date;

	private boolean full;

	@OneToMany(mappedBy = "projectionDate", cascade = CascadeType.ALL)
	private List<ProjectionTime> projectionTimes;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isFull() {
		return full;
	}

	public void setFull(boolean full) {
		this.full = full;
	}

	public List<ProjectionTime> getProjectionTimes() {
		return projectionTimes;
	}

	public void setProjectionTimes(List<ProjectionTime> projectionTimes) {
		this.projectionTimes = projectionTimes;
	}

	public JSONArray getProjectionTimesJSON() {
		JSONArray array = new JSONArray();
		JSONObject o = null;
		for (ProjectionTime projectionTime : projectionTimes) {
			o = new JSONObject();
			o.put("id", projectionTime.getId());
			o.put("time", projectionTime.getTime());
			array.add(o);
		}
		return array;
	}
	
	public String getFormatedDate(){
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		return df.format(date);
	}
}
