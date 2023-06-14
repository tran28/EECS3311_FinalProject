package payment;

public class PayWithCard implements PaymentStrategy{

	@Override
	public String process() {
		return ("Payment with card completed.");
	}

}
