package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logistics.Category;
import logistics.Movie;
import logistics.Name;

public class MovieNewController implements Initializable {

	private double x, y;

	@FXML
	private Button addNewBttn;

	@FXML
	private TextArea txtAct;

	@FXML
	private TextField txtCat;

	@FXML
	private TextArea txtDir;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtPoster;

	@FXML
	private TextField txtPrice;

	@FXML
	private TextField txtStock;

	@FXML
	void handleAddNew(ActionEvent event) {
		String title = this.txtName.getText().trim();
		String category = this.txtCat.getText().trim();
		String price = this.txtPrice.getText().trim();
		String stock = this.txtStock.getText().trim();
		String poster = this.txtPoster.getText().trim();
		String directors = this.txtDir.getText().trim();
		String actors = this.txtAct.getText().trim();
		Movie m;

		if (!title.isBlank() && !category.isBlank()) {
			m = new Movie(new Name(title), new Category(category));
			if (!price.isBlank())
				m.setFee(Double.valueOf(price));
			if (!stock.isBlank())
				m.setStock(Integer.valueOf(stock));
			if (!poster.isBlank())
				m.setImage(poster);
			if (!directors.isBlank())
				m.setDirectors(getArrayListFromString(directors));
			if (!actors.isBlank())
				m.setActors(getArrayListFromString(actors));
			Main.s.getInventory().addMovieToInventory(m);
		}

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

	private ArrayList<String> getArrayListFromString(String s) {
		ArrayList<String> arr = new ArrayList<String>();
		String[] arr_values = s.split(",");
		for (String value : arr_values) {
			arr.add(value.trim());
		}
		return arr;
	}

}
