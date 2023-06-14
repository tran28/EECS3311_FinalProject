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

public class UserEditController implements Initializable {

	private double x, y;

	@FXML
	private Button doneBttn;

	@FXML
	private TextField txtaddress;

	@FXML
	private TextField txtemail;

	@FXML
	private TextField txtname;

	@FXML
	private TextField txtpass;

	@FXML
	private TextField txtpoints;

	@FXML
	void handleDone(ActionEvent event) {
		User u = AdminDashController.selectedUser;

		String updateName = txtname.getText().trim();
		String updateEmail = txtemail.getText().trim();
		String updatePass = txtpass.getText().trim();
		int updateAddress = Integer.valueOf(txtaddress.getText().trim());
		int updatePoints = Integer.valueOf(txtpoints.getText().trim());

		if (!updateName.isBlank()) {
			u.setUsername(new Name(updateName));
		}
		if (!updateEmail.isBlank()) {
			u.setEmail(updateEmail);
		}
		if (!updatePass.isBlank()) {
			u.setPassword(updatePass);
		}
		if (!txtaddress.getText().trim().isBlank()) {
			u.setLocationDistance(updateAddress);
		}
		if (!txtpoints.getText().trim().isBlank() && updatePoints >= 0) {
			u.setLoyalty_points(updatePoints);
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
		this.txtname.setText(AdminDashController.selectedUser.getUserName());
		this.txtemail.setText(AdminDashController.selectedUser.getEmail());
		this.txtpass.setText(AdminDashController.selectedUser.getPassword());
		this.txtaddress.setText(String.valueOf(AdminDashController.selectedUser.getLocationDistance()));
		this.txtpoints.setText(String.valueOf(AdminDashController.selectedUser.getLoyalty_points()));
	}

}
