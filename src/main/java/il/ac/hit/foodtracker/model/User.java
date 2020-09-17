package il.ac.hit.foodtracker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="created_date")
	private Date created_date;

	@OneToMany(mappedBy = "user",
			cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			   	   	CascadeType.DETACH,CascadeType.REFRESH})
	private List<FoodEatingEvent> eatingEvents;

	public User() {
		super();
	}

	public User( String username, String password, Date created_date) {
		super();
		setPassword(password);
		setUsername(username);
		setCreated_date(created_date);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public List<FoodEatingEvent> getEatingEvents() {
		return eatingEvents;
	}

	public void setEatingEvents(List<FoodEatingEvent> eatingEvents) {
		this.eatingEvents = eatingEvents;
	}
	
	public void addFoodEatingEvent(FoodEatingEvent fev) {
		if(this.eatingEvents ==null) {
			this.eatingEvents = new ArrayList<>();
		}
		
		this.eatingEvents.add(fev);
		
		fev.setUser(this);
	}


	

}
