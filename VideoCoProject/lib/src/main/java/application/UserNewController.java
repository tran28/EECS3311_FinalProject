package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import credential.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logistics.Name;

public class UserNewController implements Initializable {

	private double x, y;

	@FXML
	private Button addBttn;

	@FXML
	private TextField txtemail;

	@FXML
	private TextField txtname;

	@FXML
	private TextField txtpass;

	@FXML
	void handleAdd(ActionEvent event) {
		String name = txtname.getText().trim();
		String email = txtemail.getText().trim();
		String pass = txtpass.getText().trim();
		User u = null;

		if (!name.isBlank()) {
			if (!email.isBlank()) {
				if (!pass.isBlank()) {
					u = new User(new Name(name), email, pass);
				}
			}
		}
		if (u != null)
			Main.s.addUser(u);
		else {
			System.out.println("User was not created because one or more of the required parameters were missing.");
		}

		// go back to AdminDash
		try {
			Parent adminDashParent = FXMLLoader.load(getClass().getClassLoader().getResource("AdminDash.fxml"));
			Scene adminDashScene = new Scene(adminDashParent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(adminDashScene);
			window.show();

			adminDashScene.setFill(Color.TRANSPARENT);
			adminDashParent.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					x = event.getSceneX();
					y = event.getSceneY();
				}
			});
			adminDashParent.setOnMouseDragged(new EventHandler<MouseEvent>() {
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}
