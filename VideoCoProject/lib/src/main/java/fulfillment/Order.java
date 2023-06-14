package fulfillment;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import credential.User;
import logistics.Movie;

public class Order {
	private ArrayList<Movie> order;
	private LocalDate ordered_date;
	private LocalDate expiry_date;
	private int orderID;
	private double orderTotal;
	private User orderBelongsTo;
	private int payWith; // 1: Pay with card, 2: Pay with points
	private double lateFees;
	private int shippingStatus; //-1: not shipped, 1: shipped

	public Order() {
		this.order = new ArrayList<Movie>();

		LocalDate today = LocalDate.now();
		this.ordered_date = today;
		this.expiry_date = today.plusDays(14);

		this.orderID = this.hashCode();
		this.orderTotal = 0.0;
		this.lateFees = 0.0;
		
		this.shippingStatus = -1;
	}

	public LocalDate getOrdered_date() {
		return ordered_date;
	}

	public ArrayList<Movie> getOrder() {
		return order;
	}

	public int getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(int shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public int addSingleMovieToOrder(Movie m) {
		int added; // return 1 if successfully added, -1 if not
		if (m.getStock() > 0) {
			this.order.add(m);
			this.orderTotal = this.orderTotal + m.getFee();
			m.setStock(m.getStock() - 1);
			added = 1;
		} else {
			added = -1;
			System.out.println(m.getTitle() + " was not added to this order. Current stock: " + m.getStock());
		}
		return added;
	}

	// use this when admin creates order for user
	public void setOrder(ArrayList<Movie> order) {
		this.order = order;
	}

	public LocalDate getExpiryDate() {
		return expiry_date;
	}

	public int getOrderID() {
		return orderID;
	}

	public double getOrderTotal() {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(orderTotal));
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public User getOrderBelongsTo() {
		return orderBelongsTo;
	}

	public void setOrderBelongsTo(User orderBelongsTo) {
		this.orderBelongsTo = orderBelongsTo;
	}

	public int getPayWith() {
		return payWith;
	}

	public void setPayWith(int payWith) {
		this.payWith = payWith;
	}

	public double getLateFees() {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(lateFees));
	}

	public void setLateFees(double lateFees) {
		this.lateFees = lateFees;
	}

}
