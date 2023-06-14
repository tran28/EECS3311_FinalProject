package application;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import fulfillment.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import logistics.Name;

public class AccountSummaryController implements Initializable {

	@FXML
	private Button changeaddressbttn, cancelOrderBttn;

	@FXML
	private Button changeemailbttn;

	@FXML
	private Button changenamebttn;

	@FXML
	private Button changepassbttn;

	@FXML
	private TextField txtaddress;

	@FXML
	private TextField txtemail;

	@FXML
	private TextField txtname;

	@FXML
	private TextArea txtorders;

	@FXML
	private TextField txtpass;

	@FXML
	private TextField txtpoints;

	@FXML
	private TextField txtuserid;

	@FXML
	public void handleChangeName(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Name");
		Optional<String> string = dialog.showAndWait();

		if (string.isPresent()) {
			String s = string.get().trim();
			if (!s.isBlank()) {
				Main.currentUser.setUsername(new Name(s));
				this.txtname.setText(s);
			}
		}
	}

	@FXML
	public void handleChangeEmail(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Email");
		Optional<String> string = dialog.showAndWait();

		if (string.isPresent()) {
			String s = string.get().trim();
			if (!s.isBlank()) {
				Main.currentUser.setEmail(s);
				this.txtemail.setText(s);
			}
		}
	}

	@FXML
	public void handleChangePassword(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Password");
		Optional<String> string = dialog.showAndWait();

		if (string.isPresent()) {
			String s = string.get().trim();
			if (!s.isBlank()) {
				Main.currentUser.setPassword(s);
				this.txtpass.setText(s);
			}
		}
	}

	@FXML
	public void handleChangeAddress(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Address");
		Optional<String> string = dialog.showAndWait();

		if (string.isPresent()) {
			String s = string.get().trim();
			if (!s.isBlank()) {
				Main.currentUser.setLocationDistance(Integer.valueOf(s));
				;
				this.txtaddress.setText(s);
			}
		}
	}

	@FXML
	public void handleCancelOrder(ActionEvent event) {
		String str = txtorders.getSelectedText().trim();
		if (str.matches("[0-9]+") && str.length() > 3) { // only contains numbers and greater than 3 digits
			// check of OrderID exists
			Order selectedOrder = Main.warehouseFacade.getOrderFromOrderID(Integer.valueOf(str));
			if (selectedOrder != null) { // order exists
				if (selectedOrder.getShippingStatus() == -1) { // order not shipped
					Main.warehouseFacade.removeOrderFromWarehouseList(selectedOrder);
					Main.currentUser.removeOrderFromOrderList(selectedOrder);

					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Order Cancelled");
					alert.setHeaderText(null);
					alert.setGraphic(null);
					alert.setContentText("Order " + selectedOrder.getOrderID() + " has been successfully cancelled.");
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Unable to Cancel Order");
					alert.setContentText(
							"Our system shows that you are trying to cancel an order that has already been shipped.");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Order Error");
				alert.setContentText("Order does not exist in warehouse.");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Selection Error");
			alert.setContentText("The OrderID must contain only numbers.");
			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.txtuserid.setText(String.valueOf(Main.currentUser.getUserID()));
		this.txtname.setText(Main.currentUser.getUserName());
		this.txtemail.setText(Main.currentUser.getEmail());
		this.txtpass.setText(Main.currentUser.getPassword());
		this.txtaddress.setText(String.valueOf(Main.currentUser.getLocationDistance()));

		this.txtpoints.setText(String.valueOf(Main.currentUser.getLoyalty_points()));

		for (Order o : Main.currentUser.getOrderList()) {
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
