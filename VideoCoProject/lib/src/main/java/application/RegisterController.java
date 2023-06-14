package application;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import credential.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logistics.Name;

public class RegisterController extends Application {

	@FXML
	private Button bttnCreateMyAcc;

	@FXML
	private TextField txtname, txtemail;

	@FXML
	private PasswordField txtpass;

	@FXML
	private void handleCreateAccount(ActionEvent actionEvent) {
		boolean isValidEmail = checkValidEmail();
		if (!this.txtname.getText().isBlank()) {
			if (isValidEmail) {
				if (!this.txtpass.getText().isBlank()) {
					String name = this.txtname.getText();
					String email = this.txtemail.getText();
					String password = this.txtpass.getText();

					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Account Confirmation");
					alert.setHeaderText(null);
					alert.setGraphic(null);
					alert.setContentText("Do you want to proceed with the following account details?\n\nName: " + name
							+ "\nEmail: " + email + "\nPassword: " + password);
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						User u = new User(new Name(name), email, password);
						Main.s.addUser(u);
						// close the create account window
						((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
					} else {

					}
				} else {
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Invalid Password");
					errorAlert.setContentText("Password cannot be empty.");
					errorAlert.showAndWait();
				}
			} else {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Invalid Email");
				errorAlert.setContentText("User input is not a valid email.");
				errorAlert.showAndWait();
			}
		} else {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Invalid Name");
			errorAlert.setContentText("Name cannot be empty.");
			errorAlert.showAndWait();
		}
	}

	private boolean checkValidEmail() {
		boolean isValid = false;
		String email = this.txtemail.getText();
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		if (m.matches()) {
			isValid = true;
		}
		return isValid;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}
