package internal;

public class OrderInPerson implements OrderProcessStrategy {

	@Override
	public String buildOrderingProcess() {
		return ("Order to be completed in-person.");
	}
}
