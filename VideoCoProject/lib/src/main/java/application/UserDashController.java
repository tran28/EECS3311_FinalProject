package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logistics.Movie;
import logistics.MovieComparator;
import search.Inventory;
import search.SearchVisitor;

public class UserDashController implements Initializable {

	@FXML
	private Button bttnsignout, viewCartBttn, addBttn, moreBttn, viewAccountBttn;

	@FXML
	private VBox chosenMovieCard;

	@FXML
	private GridPane grid;

	@FXML
	private ImageView movieImage;

	@FXML
	private Label movieNameLabel, moviePriceLabel;

	@FXML
	private ScrollPane scroll;

	@FXML
	private TextField searchField;

	@FXML
	private MenuButton menuBttn;

	@FXML
	private MenuItem searchName, searchCategory, searchAll;

	private ArrayList<Movie> allMovies = new ArrayList<Movie>();
	private Image image;
	private MyListener myListener;
	private Movie selectedMovie;
	private double x, y;

	private ArrayList<Movie> getMoviesData() {
		for (Character k : Main.s.getInventory().getMoviesMap().keySet()) {
			for (Movie m : Main.s.getInventory().getMoviesMap().get(k)) {
				this.allMovies.add(m);
			}
		}
		Collections.sort(allMovies, new MovieComparator());
		return allMovies;
	}

	private void setChosenMovie(Movie m) {
		this.selectedMovie = m;
		this.movieNameLabel.setText(m.getTitle());
		this.moviePriceLabel.setText(Main.CURRENCY + m.getFee());

		image = new Image(getClass().getClassLoader().getResourceAsStream(m.getImage()));
		this.movieImage.setImage(image);
		this.chosenMovieCard.setStyle("-fx-background-color: #051937;\n" + "-fx-background-radius: 30;");
	}

	private void populateGridWithAllMovies() {
		int col = 0;
		int row = 1;

		try {
			for (Movie m : this.allMovies) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getClassLoader().getResource("Item.fxml"));
				AnchorPane anchorPane = fxmlLoader.load();

				ItemController itemController = fxmlLoader.getController();
				itemController.setMoviesData(m, this.myListener);

				if (col == 3) {
					col = 0;
					row++;
				}

				grid.add(anchorPane, col++, row);
				grid.setMinWidth(Region.USE_COMPUTED_SIZE);
				grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
				grid.setMaxWidth(Region.USE_PREF_SIZE);

				grid.setMinHeight(Region.USE_COMPUTED_SIZE);
				grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
				grid.setMaxHeight(Region.USE_PREF_SIZE);

				GridPane.setMargin(anchorPane, new Insets(10));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void populateGridWithSearchMovies(ArrayList<Movie> searchResult) {
		int col = 0;
		int row = 1;

		this.grid.getChildren().clear();

		try {
			for (Movie m : searchResult) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getClassLoader().getResource("Item.fxml"));
				AnchorPane anchorPane = fxmlLoader.load();

				ItemController itemController = fxmlLoader.getController();
				itemController.setMoviesData(m, this.myListener);

				if (col == 3) {
					col = 0;
					row++;
				}

				grid.add(anchorPane, col++, row);
				grid.setMinWidth(Region.USE_COMPUTED_SIZE);
				grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
				grid.setMaxWidth(Region.USE_PREF_SIZE);

				grid.setMinHeight(Region.USE_COMPUTED_SIZE);
				grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
				grid.setMaxHeight(Region.USE_PREF_SIZE);

				GridPane.setMargin(anchorPane, new Insets(10));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void goBackToLogin(ActionEvent event) {
		backtoLoginWindow(event);
	}

	@FXML
	public void handleViewCart(ActionEvent event) {
		showCartCheckout(event);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.getMoviesData();

		if (this.allMovies.size() > 0) {
			this.setChosenMovie(this.allMovies.get(0));
			this.myListener = new MyListener() {
				@Override
				public void onClickListener(Movie movie) {
					setChosenMovie(movie);
				}
			};
		}

		this.populateGridWithAllMovies();

		this.moreBttn.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Movie Information");
			alert.setHeaderText(null);
			alert.setGraphic(null);

			// Directors
			String dirText = new String();
			for (int i = 0; i < this.selectedMovie.getDirectors().size(); i++) {
				if (i < this.selectedMovie.getDirectors().size() - 1) {
					dirText = dirText + this.selectedMovie.getDirectors().get(i) + ", ";
				} else {
					dirText = dirText + this.selectedMovie.getDirectors().get(i);
				}
			}

			// Actors
			String actText = new String();
			for (int i = 0; i < this.selectedMovie.getActors().size(); i++) {
				if (i < this.selectedMovie.getActors().size() - 1) {
					actText = actText + this.selectedMovie.getActors().get(i) + ", ";
				} else {
					actText = actText + this.selectedMovie.getActors().get(i);
				}
			}
			alert.setContentText("Director(s): " + dirText + "\n\nActors: " + actText);
			alert.showAndWait();
		});

		this.addBttn.setOnAction(event -> {
			int added = CartCheckoutController.order.addSingleMovieToOrder(this.selectedMovie);
			if (added == 1) {
				System.out.println(
						this.selectedMovie.getTitle() + " added to order list. Price: " + this.selectedMovie.getFee());
			} else if (added == -1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Movie Information");
				alert.setHeaderText(null);
				alert.setGraphic(null);
				alert.setContentText("Movie was not added to your order as it is out of stock.\n\nCurrent stock: "
						+ this.selectedMovie.getStock());
				alert.showAndWait();
			} else {
				System.out.println("addBttn does not function");
			}
		});

		this.searchName.setOnAction(event -> {
			Inventory inv = Main.s.getInventory();
			SearchVisitor search = new SearchVisitor(this.searchField.getText(), 1);
			this.populateGridWithSearchMovies(inv.accept(search));
		});

		this.searchCategory.setOnAction(event -> {
			Inventory inv = Main.s.getInventory();
			SearchVisitor search = new SearchVisitor(this.searchField.getText(), 2);
			this.populateGridWithSearchMovies(inv.accept(search));
		});

		this.searchAll.setOnAction(event -> {
			Inventory inv = Main.s.getInventory();
			SearchVisitor search = new SearchVisitor(this.searchField.getText(), 3);
			this.populateGridWithSearchMovies(inv.accept(search));
		});

	}

	private void backtoLoginWindow(ActionEvent event) {
		try {
			Parent loginParent = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
			Scene loginScene = new Scene(loginParent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(loginScene);
			window.show();

			loginScene.setFill(Color.TRANSPARENT);
			loginParent.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					x = event.getSceneX();
					y = event.getSceneY();
				}
			});
			loginParent.setOnMouseDragged(new EventHandler<MouseEvent>() {
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

	private void showCartCheckout(ActionEvent event) {
		try {
			Parent cartParent = FXMLLoader.load(getClass().getClassLoader().getResource("CartCheckout.fxml"));
			Scene cartScene = new Scene(cartParent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(cartScene);
			window.show();

			cartScene.setFill(Color.TRANSPARENT);
			cartParent.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					x = event.getSceneX();
					y = event.getSceneY();
				}
			});
			cartParent.setOnMouseDragged(new EventHandler<MouseEvent>() {
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
	private void handleViewAccount(ActionEvent actionEvent) throws IOException {
		Parent summaryRoot = FXMLLoader.load(getClass().getClassLoader().getResource("AccountSummary.fxml"));

		Scene scene = new Scene(summaryRoot);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("View Account");
		primaryStage.setScene(scene);

		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initOwner(this.viewAccountBttn.getScene().getWindow());
		primaryStage.show();
	}
}