package testSuites;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import credential.Login;
import credential.User;
import credential.VideoCoSystem;
import fulfillment.Order;
import logistics.Category;
import logistics.Movie;
import logistics.Name;

public class OrderTests {

	Movie m1 = new Movie(new Name("Love, Simon"), new Category("Romance"));
	Movie m2 = new Movie(new Name("Shang-Chi"), new Category("Action"));
	Movie m3 = new Movie(new Name("Frozen"), new Category("Animation"));
	Movie m4 = new Movie(new Name("Fantastic Beast"), new Category("Fantasy"));
	Movie m5 = new Movie(new Name("The Favourite"), new Category("Historical"));
	Movie m6 = new Movie(new Name("Spider-Man: Far From Home"), new Category("Action"));
	Movie m7 = new Movie(new Name("Free Guy"), new Category("Comedy"));

	private void addMovieDetails() {
		m1.setStock(10);
		m2.setStock(5);
		m3.setStock(5);
		m4.setStock(1);
		m5.setStock(0);
		m6.setStock(10);
		m7.setStock(0);

		m1.setFee(7.99);
		m2.setFee(9.99);
		m3.setFee(7.99);
		m4.setFee(7.99);
		m5.setFee(5.99);
		m6.setFee(7.99);
		m7.setFee(9.99);
	}

	@Test
	public void testCreateOrder() {
		this.addMovieDetails();

		Order o = new Order();
		o.addSingleMovieToOrder(m1);
		assertEquals(1, o.getOrder().size());
		o.addSingleMovieToOrder(m7);
		assertEquals(1, o.getOrder().size());
		o.addSingleMovieToOrder(m4);
		assertEquals(2, o.getOrder().size());
	}

	@Test
	public void testDecreaseStock() {
		this.addMovieDetails();

		Order o = new Order();
		o.addSingleMovieToOrder(m4);
		assertEquals(1, o.getOrder().size());

		Order o2 = new Order();
		o2.addSingleMovieToOrder(m4);
		assertEquals(0, o2.getOrder().size());
	}

	@Test
	public void testCorrectInvoice() {
		this.addMovieDetails();

		Order o = new Order();
		o.addSingleMovieToOrder(m1);
		assertEquals(1, o.getOrder().size());
		o.addSingleMovieToOrder(m4);
		assertEquals(2, o.getOrder().size());
		assertEquals(15.98, o.getOrderTotal(), 0.01d);
	}

	@Test
	public void testOrderAddedToUser() {
		this.addMovieDetails();

		// create an order
		Order o = new Order();
		o.addSingleMovieToOrder(m1);
		o.addSingleMovieToOrder(m4);
		o.addSingleMovieToOrder(m6);
		o.addSingleMovieToOrder(m2);

		// make a system, admin, and user
		VideoCoSystem s = new VideoCoSystem();
		User u1 = new User(new Name("User1"), "user1@videoco.com", "user1pass");
		s.addUser(u1);

		// login as user
		Login l = new Login(s, u1.getEmail(), u1.getPassword(), 2);
		l.attempt_login();

		// add order to account
		u1.addOrderToOrderList(o);

		// check if order is inside account
		int expected = o.getOrderID(); // unique hashCode for this orderID 'o'
		assertEquals(1, u1.getOrderList().size());
		assertEquals(expected, u1.getOrderList().get(0).getOrderID());

		// make another order and add to the same user account
		Order o2 = new Order();
		o2.addSingleMovieToOrder(m3);
		u1.addOrderToOrderList(o2);
		assertEquals(2, u1.getOrderList().size());

		// verifying orderTotal of both orders
		assertEquals(33.96, u1.getOrderList().get(0).getOrderTotal(), 0.01d);
		assertEquals(7.99, u1.getOrderList().get(1).getOrderTotal(), 0.01d);
	}

}
