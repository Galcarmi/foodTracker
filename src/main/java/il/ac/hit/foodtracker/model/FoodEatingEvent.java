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
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="category")
	private String category;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="calory")
	private int calory;
	
	@Column(name="created_date")
	private Date created_date;

	@Column(name="update_date")
	private Date update_date;

	
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
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getCalory() {
		return calory;
	}

	public void setCalory(int calory) {
		this.calory = calory;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "FoodEatingEvent [id=" + id + ", name=" + name + ", category=" + category + ", user=" + user
				+ ", calory=" + calory + ", created_date=" + created_date + ", update_date=" + update_date + "]";
	}

	public FoodEatingEvent getFoodEatingEventResponse() {
		FoodEatingEvent fevToReturn = new FoodEatingEvent( name,  calory,  created_date,  update_date, category);
		fevToReturn.setId(id);
		return fevToReturn;
	}
	

}
