package application;

import java.net.URL;
import java.util.ResourceBundle;

import credential.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logistics.Name;
import javafx.scene.control.Alert.AlertType;

public class AdminManagementController implements Initializable {

	@FXML
	private Button addBttn;

	@FXML
	private Button removeBttn;

	@FXML
	private TextField txtemail;

	@FXML
	private TextField txtname;

	@FXML
	private TextField txtpass;

	@FXML
	void handleAddAdmin(ActionEvent event) {
		String name = txtname.getText().trim();
		String email = txtemail.getText().trim();
		String pass = txtpass.getText().trim();

		if (!name.isBlank() & !email.isBlank() & !pass.isBlank()) {
			Admin a = new Admin(new Name(name), email, pass);
			Main.s.addAdmin(a);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Admin Added");
			alert.setHeaderText(null);
			alert.setGraphic(null);
			alert.setContentText("Admin added:\n\nAdminID: " + a.getAdminID() + "\nName: " + a.getAdminName()
					+ "\nEmail: " + a.getEmail() + "\nPassword: " + a.getPassword());
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Cannot Create Admin");
			alert.setHeaderText(null);
			alert.setGraphic(null);
			alert.setContentText("One or more parameters [name, email, password] is/are empty.");
			alert.showAndWait();
		}
	}

	@FXML
	void handleRemoveAdmin(ActionEvent event) {
		Admin found = Main.s.getAdminFromEmail(txtemail.getText());
		if (found != null) {
			System.out.println("Admin removed: " + found.getEmail());

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Admin Removed");
			alert.setHeaderText(null);
			alert.setGraphic(null);
			alert.setContentText("Admin removed:\n\nAdminID: " + found.getAdminID() + "\nName: " + found.getAdminName()
					+ "\nEmail: " + found.getEmail() + "\nPassword: " + found.getPassword());
			alert.showAndWait();

			Main.s.removeAdmin(found);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Not Found");
			alert.setHeaderText(null);
			alert.setGraphic(null);
			alert.setContentText("There is no admin found with the provided credentials.");
			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
