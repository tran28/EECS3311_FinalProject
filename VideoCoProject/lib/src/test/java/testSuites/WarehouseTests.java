package testSuites;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import credential.Location;
import credential.User;
import credential.VideoCoSystem;
import fulfillment.FacadeForWarehouse;
import fulfillment.Order;
import logistics.Category;
import logistics.Movie;
import logistics.Name;

class WarehouseTests {

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
	void testWarehouseFulfilment() {
		this.addMovieDetails();

		// create an order
		Order o = new Order();
		o.addSingleMovieToOrder(m1);
		o.addSingleMovieToOrder(m4);
		o.addSingleMovieToOrder(m6);
		o.addSingleMovieToOrder(m2);

		o.setPayWith(2); // pay with points

		// make a system, admin, and user
		VideoCoSystem s = new VideoCoSystem();
		User u1 = new User(new Name("User1"), "user1@videoco.com", "user1pass");
		s.addUser(u1);

		// add order to account
		u1.addOrderToOrderList(o);

		// set user location
		Location l = new Location();
		u1.setLocationDistance(l.generateRandomUserDistance());
		System.out.println("User location: " + u1.getLocationDistance());

		FacadeForWarehouse warehousefront = new FacadeForWarehouse();
		String status = warehousefront.fulfilOrder(o);
		System.out.println(status);

		assertEquals(1, o.getShippingStatus());
	}

}
