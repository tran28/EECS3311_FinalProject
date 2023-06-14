package application;

import java.net.URL;
import java.util.ResourceBundle;

import credential.User;
import fulfillment.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class DialInController implements Initializable {

	@FXML
	private Button lookupBttn;

	@FXML
	private TextArea txtorders;

	@FXML
	private TextField txtuserid;

	@FXML
	void handleLookup(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.lookupBttn.setOnAction(e -> {
			String id = txtuserid.getText().trim();

			if (!id.isBlank()) {
				User user = Main.s.getUserFromUserID(Integer.valueOf(id));
				if (user != null) {
					this.printOrders(user);
				} else {
					// user not found
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Unable to Locate User");
					alert.setContentText("There is no user with the specified UserID.");
					alert.showAndWait();
				}
			} else {
				// no userID
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Empty UserID");
				alert.setContentText("Cannot lookup an empty UserID.");
				alert.showAndWait();
			}
		});

	}

	private void printOrders(User u) {
		for (Order o : u.getOrderList()) {
			String paidBy = "unknown";
			if (o.getPayWith() == 1)
				paidBy = "card";
			else if (o.getPayWith() == 2)
				paidBy = "points";

			String shipped = "unknown";
			if (o.getShippingStatus() == -1)
				shipped = "not shipped";
			else if (o.getShippingStatus() == 1)
				shipped = "shipped";

			this.txtorders
					.appendText("Order ID: " + o.getOrderID() + "\n\tPlaced on: " + o.getOrdered_date() + "\n\tTotal: $"
							+ o.getOrderTotal() + "\n\tPaid by: " + paidBy + "\n\tReturn by: " + o.getExpiryDate()
							+ "\n\tCurrent late fees: $" + o.getLateFees() + "\n\tShipping status: " + shipped);
			this.txtorders.appendText("\n\n");
		}
	}

}
