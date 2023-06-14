package fulfillment;

import java.time.LocalDate;
import java.util.ArrayList;

import logistics.Movie;

public class FacadeForWarehouse {
	private Warehouse warehouse;
	private ArrayList<Order> listOfAllOrders = new ArrayList<Order>();

	public ArrayList<Order> getListOfAllOrders() {
		return listOfAllOrders;
	}

	public void addOrderToWarehouseList(Order o) {
		this.listOfAllOrders.add(o);
	}

	public void removeOrderFromWarehouseList(Order o) {
		for (Movie m : o.getOrder()) {
			m.setStock(m.getStock() + 1);
		}
		this.listOfAllOrders.remove(o);
	}

	public String fulfilOrder(Order o) {
		warehouse = new Payment();
		String pay = warehouse.processByWarehouse(o);

		warehouse = new WarehouseLocation();
		String loc = warehouse.processByWarehouse(o);

		warehouse = new Shipping();
		String ship = warehouse.processByWarehouse(o);

		o.setShippingStatus(1); // change orderStatus from -1 to 1 => shipped

		String s = ("Order: " + o.getOrderID() + " has been processed and fulfilled.\n\tMessage from Payment(): " + pay
				+ "\n\tMessage from WarehouseLocation(): " + loc + "\n\tMessage from Shipping(): " + ship);

		return s;
	}

	public Order getOrderFromOrderID(int id) {
		Order found = null;
		for (Order order : this.listOfAllOrders) {
			if (id == order.getOrderID()) {
				found = order;
			}
		}
		return found;
	}

	public void updateLateFeesOnAllOrdersInSystem() {
		for (Order o : this.listOfAllOrders) {
			int pastDue = LocalDate.now().getDayOfYear() - o.getExpiryDate().getDayOfYear();
			double outsideOntFee = 9.99;
			double fixedLateFee = 1.0; // $1.00 per movie
			int numOfMovies = o.getOrder().size();
			double totalLateFees = numOfMovies * fixedLateFee * pastDue;

			if (pastDue > 0) {
				// add a fixed late charge for people outside of Ontario => integer distance
				// greater than 2000
				if (o.getOrderBelongsTo().getLocationDistance() > 2000) {
					o.setLateFees(totalLateFees + outsideOntFee);
				} else {
					o.setLateFees(totalLateFees);
				}
			} else {
				// do nothing; not late
			}
		}
	}

}
