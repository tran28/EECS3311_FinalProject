package internal;

import util.OrderMethod;

public class Operator {

	private OrderProcessStrategy orderProcess;

	public String completeAlternativeOrder(String s) {
		String res = "No aternative ordering process found.";

		if (s.equals(OrderMethod.ORDER_IN_PERSON)) {
			orderProcess = new OrderInPerson();
			res = this.orderProcess.buildOrderingProcess();
		} else if (s.equals(OrderMethod.ORDER_VIA_PHONE)) {
			orderProcess = new OrderByDialIn();
			res = this.orderProcess.buildOrderingProcess();
		} else {
			// online
		}
		return res;

	}

}
