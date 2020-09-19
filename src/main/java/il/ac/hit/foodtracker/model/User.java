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
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int m_Id;

	@Column(name = "username", unique = true)
	private String m_Username;

	@Column(name = "password")
	private String m_Password;

	@Column(name = "created_date")
	private Date m_Created_date;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<FoodEatingEvent> eatingEvents;

	public User() {
		super();
	}

	public User(String i_Username, String i_Password, Date i_Created_date) {
		super();
		setPassword(i_Password);
		setUsername(i_Username);
		setCreated_date(i_Created_date);
	}

	public int getId() {
		return m_Id;
	}

	public void setId(int id) {
		this.m_Id = id;
	}

	public String getUsername() {
		return m_Username;
	}

	public void setUsername(String username) {
		this.m_Username = username;
	}

	public String getPassword() {
		return m_Password;
	}

	public void setPassword(String password) {
		this.m_Password = password;
	}

	public Date getCreated_date() {
		return m_Created_date;
	}

	public void setCreated_date(Date created_date) {
		this.m_Created_date = created_date;
	}

	public List<FoodEatingEvent> getEatingEvents() {
		return eatingEvents;
	}

	public void setEatingEvents(List<FoodEatingEvent> eatingEvents) {
		this.eatingEvents = eatingEvents;
	}

	public void addFoodEatingEvent(FoodEatingEvent fev) {
		if (this.eatingEvents == null) {
			this.eatingEvents = new ArrayList<>();
		}

		this.eatingEvents.add(fev);

		fev.setUser(this);
	}

	@Override
	public String toString() {
		return "User [id=" + m_Id + ", username=" + m_Username + ", password=" + m_Password + ", created_date=" + m_Created_date
				+ ", eatingEvents=" + eatingEvents + "]";
	}

}
