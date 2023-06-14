package fulfillment;

import credential.Location;

public class WarehouseLocation implements Warehouse {
	private Location warehouse1 = new Location();
	private Location warehouse2 = new Location();
	private Location warehouse3 = new Location();

	public WarehouseLocation() {
		// fixed distances for warehouses
		this.warehouse1.setDistance(200);
		this.warehouse2.setDistance(1100);
		this.warehouse3.setDistance(2700);
	}

	private static int closest(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}

	@Override
	public String processByWarehouse(Order o) {
		String status = "Not processed.";
		
		if (o.getOrderBelongsTo() != null) {
			int distance1 = Math.abs(this.warehouse1.getDistance() - o.getOrderBelongsTo().getLocationDistance());
			int distance2 = Math.abs(this.warehouse2.getDistance() - o.getOrderBelongsTo().getLocationDistance());
			int distance3 = Math.abs(this.warehouse3.getDistance() - o.getOrderBelongsTo().getLocationDistance());
			
			int closest = closest(distance1, distance2, distance3);

			String warehouseNumber = null;
			if (closest == distance1) {
				warehouseNumber = "Warehouse 1";
			} else if (closest == distance2) {
				warehouseNumber = "Warehouse 2";
			} else if (closest == distance3) {
				warehouseNumber = "Warehouse 3";
			} else {
				warehouseNumber = "No warehouse found. Closest value: " + String.valueOf(closest);
			}
			status = "Order processed at " + warehouseNumber + ".";
		}
		else {
			status = "Order is not associated with any user.";
		}
		
		return status;
	}
}
