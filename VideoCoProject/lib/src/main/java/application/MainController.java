package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import credential.Admin;
import credential.Login;

public class MainController implements Initializable {

	@FXML
	private TextField txtemail;

	@FXML
	private PasswordField txtpass;

	@FXML
	private Button bttnUserSignin, bttnexit, bttnAdminSignin, bttnCreateAccount;

	private double x, y;

	@FXML
	public void handleUserSignin(ActionEvent event) {
		Login l = new Login(Main.s, txtemail.getText(), txtpass.getText(), 2);
		boolean success = l.attempt_login();
		if (success) {
			// Get the current user that is logged in
			for (Integer i : Main.s.getUsers().keySet()) {
				if (Main.s.getUsers().get(i).getEmail().equals(txtemail.getText())) {
					Main.currentUser = Main.s.getUsers().get(i);
				}
			}

			showUserDashWindow(event);

		} else {
			showLoginError(l.getEmail(), l.getPassword(), "User");
		}
	}

	@FXML
	public void handleAdminSignin(ActionEvent event) {
		Login l = new Login(Main.s, txtemail.getText(), txtpass.getText(), 1);
		boolean success = l.attempt_login();
		if (success) {
			// Get the current admin that is logged in
			for (Admin a : Main.s.getAdmins()) {
				if (a.getEmail().equals(txtemail.getText())) {
					Main.currentAdmin = a;
				}
			}

			showAdminDashWindow(event);

		} else {
			showLoginError(l.getEmail(), l.getPassword(), "Admin");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bttnexit.setOnMouseClicked(e -> System.exit(0));
	}

	private void showLoginError(String email, String pass, String type) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Sign In Error");
		alert.setHeaderText("Error sign in as: " + type);
		alert.setContentText("The following credentials is not a valid " + type + " in the system.\n\nEmail: " + email
				+ "\nPassword: " + pass);
		alert.showAndWait();
	}

	private void showUserDashWindow(ActionEvent event) {
		try {
			Parent userdashParent = FXMLLoader.load(getClass().getClassLoader().getResource("UserDash.fxml"));
			Scene userdashScene = new Scene(userdashParent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(userdashScene);
			window.show();

			userdashScene.setFill(Color.TRANSPARENT);
			userdashParent.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					x = event.getSceneX();
					y = event.getSceneY();
				}
			});
			userdashParent.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					window.setX(event.getScreenX() - x);
					window.setY(event.getScreenY() - y);
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showAdminDashWindow(ActionEvent event) {
		try {
			Parent admindashParent = FXMLLoader.load(getClass().getClassLoader().getResource("AdminDash.fxml"));
			Scene admindashScene = new Scene(admindashParent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(admindashScene);
			window.show();

			admindashScene.setFill(Color.TRANSPARENT);
			admindashParent.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					x = event.getSceneX();
					y = event.getSceneY();
				}
			});
			admindashParent.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					window.setX(event.getScreenX() - x);
					window.setY(event.getScreenY() - y);
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void handleCreateAccount(ActionEvent actionEvent) throws IOException {
		Parent registerRoot = FXMLLoader.load(getClass().getClassLoader().getResource("Register.fxml"));

		Scene scene = new Scene(registerRoot);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Create Account");
		primaryStage.setScene(scene);

		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initOwner(this.bttnCreateAccount.getScene().getWindow());
		primaryStage.show();
	}

}
