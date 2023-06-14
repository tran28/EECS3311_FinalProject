package credential;

import java.util.ArrayList;

import fulfillment.Order;
import logistics.Name;

public class User {
	private Name userName;
	private String email;
	private String password;
	private int userID;
	private Location location;
	private ArrayList<Order> orderList;
	private int loyalty_points;

	public User(Name name, String email, String password) {
		this.userName = name;
		this.email = email;
		this.password = password;
		this.userID = name.hashCode();
		this.location = new Location();
		this.orderList = new ArrayList<Order>();

		this.setLocationDistance(this.location.generateRandomUserDistance());
	}

	public int getLoyalty_points() {
		return loyalty_points;
	}

	public void setLoyalty_points(int loyalty_points) {
		this.loyalty_points = loyalty_points;
	}

	public String getUserName() {
		return userName.getName();
	}

	public void setUsername(Name username) {
		this.userName = username;
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

	public int getUserID() {
		return userID;
	}

	public ArrayList<Order> getOrderList() {
		return orderList;
	}

	public int getLocationDistance() {
		return location.getDistance();
	}

	public void setLocationDistance(int d) {
		this.location.setDistance(d);
		;
	}

	public void addOrderToOrderList(Order o) {
		this.orderList.add(o);
		o.setOrderBelongsTo(this);
	}

	public void removeOrderFromOrderList(Order o) {
		this.orderList.remove(o);
		this.loyalty_points = this.loyalty_points - o.getOrder().size();
		o.setOrderBelongsTo(null);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		final User other = (User) obj;
		if ((this.userName == null) ? (other.userName != null) : !this.userName.equals(other.userName)) {
			return false;
		}

		if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
			return false;
		}

		if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
			return false;
		}

		if (this.userID != other.userID) {
			return false;
		}

		return true;
	}

}
