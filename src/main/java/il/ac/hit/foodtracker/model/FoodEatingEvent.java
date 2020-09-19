package il.ac.hit.foodtracker.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "food_eating_event")
public class FoodEatingEvent {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int m_Id;
	
	@Column(name="name")
	private String m_Name;
	
	@Column(name="category")
	private String m_Category;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User m_User;
	
	@Column(name="calory")
	private int m_Calory;
	
	@Column(name="created_date")
	private Date m_Created_date;

	@Column(name="update_date")
	private Date m_Update_date;

	
	public FoodEatingEvent() {
		super();
	}

	public FoodEatingEvent(String i_Name, int i_Calory, Date i_Created_date, Date i_Update_date,String i_Category) {
		super();
		setName(i_Name);
		setCalory(i_Calory);
		setCreated_date(i_Created_date);
		setUpdate_date(i_Update_date);
		setCategory(i_Category);
	}

	public int getId() {
		return m_Id;
	}

	public void setId(int id) {
		this.m_Id = id;
	}

	public String getName() {
		return m_Name;
	}

	public void setName(String name) {
		this.m_Name = name;
	}

	public User getUser() {
		return m_User;
	}

	public void setUser(User user) {
		this.m_User = user;
	}

	public int getCalory() {
		return m_Calory;
	}

	public void setCalory(int calory) {
		this.m_Calory = calory;
	}

	public Date getCreated_date() {
		return m_Created_date;
	}

	public void setCreated_date(Date created_date) {
		this.m_Created_date = created_date;
	}

	public Date getUpdate_date() {
		return m_Update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.m_Update_date = update_date;
	}

	public String getCategory() {
		return m_Category;
	}

	public void setCategory(String category) {
		this.m_Category = category;
	}


	

}
