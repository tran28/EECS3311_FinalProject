package payment;

public class PayWithPoints implements PaymentStrategy{

	@Override
	public String process() {
		return ("Payment with points completed.");
	}

}
