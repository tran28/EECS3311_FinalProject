package fulfillment;

import payment.PayWithCard;
import payment.PayWithPoints;
import payment.PaymentStrategy;

public class Payment implements Warehouse {

	private PaymentStrategy paymentStrategy;

	@Override
	public String processByWarehouse(Order o) {
		String payment = "No payment as order does not have a 'payWith'.";
		if (o.getPayWith() == 1) {
			paymentStrategy = new PayWithCard();
			payment = this.paymentStrategy.process();
		} else if (o.getPayWith() == 2) {
			paymentStrategy = new PayWithPoints();
			payment = this.paymentStrategy.process();
		}
		return payment;
	}
}
