package fulfillment;

public class Shipping implements Warehouse {

	private String shippingStatus;

	private void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	@Override
	public String processByWarehouse(Order o) {
		this.setShippingStatus("Order has been shipped from warehouse and can no longer be cancelled.");
		return this.shippingStatus;
	}

}
