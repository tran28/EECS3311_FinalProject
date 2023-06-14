package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logistics.Movie;

public class ItemController implements Initializable {

	@FXML
	private ImageView image;

	@FXML
	private Label nameLabel;

	@FXML
	private Label priceLabel;
	
	private Movie movie;
	
	private MyListener myListener;
	
	@FXML
	private void click(MouseEvent mouseEvent) {
		this.myListener.onClickListener(movie);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	public void setMoviesData(Movie m, MyListener myListener) {
		this.movie = m;
		this.myListener = myListener;
		this.nameLabel.setText(m.getTitle());
		this.priceLabel.setText(Main.CURRENCY + m.getFee());
		Image img = new Image(getClass().getClassLoader().getResourceAsStream(m.getImage()));
		this.image.setImage(img);
	}

}
