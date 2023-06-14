package internal;

public class OrderByDialIn implements OrderProcessStrategy {

	@Override
	public String buildOrderingProcess() {
		return ("Order to be completed via dial-in.");
	}
}
