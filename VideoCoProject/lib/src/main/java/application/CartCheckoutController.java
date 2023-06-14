package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import fulfillment.Order;
import internal.Operator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logistics.Movie;
import logistics.Name;
import util.OrderMethod;

public class CartCheckoutController implements Initializable {

	private double x, y;
	public static Order order = new Order();
	private int points = 0;
	private static final int POINTS_COST_PER_MOVIE = 10;

	@FXML
	private Button backtoUserDashBttn, payPointsBttn, payCardBttn, dialinBttn, inpersonBttn;

	@FXML
	private ListView<String> listView;

	@FXML
	private TableView<Movie> tableView;

	@FXML
	private TableColumn<Movie, Name> titleColumn;

	@FXML
	private TableColumn<Movie, Double> priceColumn;

	@FXML
	private TextField txtPointsAvail, txtPointsToBeEarned, txtTotal, txtorderID;

	@FXML
	public void goBackToUserDash(ActionEvent event) {
		backtoUserDashboard(event);
	}

	@FXML
	public void handlePayWithCard(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Pay With Card Confirmation");
		alert.setHeaderText(null);
		alert.setGraphic(null);
		alert.setContentText(
				"A card processing window would appear here and allow the user to checkout with card.\n\nFor the purpose of testing, pressing 'OK' will complete the order. Pressing 'Cancel' will return to cart.");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			CartCheckoutController.order.setPayWith(1); // Ref: Order.class method setPayWith
														// (1: pay with card, 2: pay with points)
			resetCartAfterCheckout();
		}
	}

	@FXML
	public void handlePayWithPoints(ActionEvent event) {
		int pointsRequired = POINTS_COST_PER_MOVIE * this.points;
		if (Main.currentUser.getLoyalty_points() < pointsRequired) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Cannot Pay With Points");
			errorAlert.setContentText(
					"You don't have enough points to pay for this order.\n\nReminder: Each movie costs 10 points.\nAmount of points required for this order: "
							+ pointsRequired + "\nYour current point(s): " + Main.currentUser.getLoyalty_points());
			errorAlert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Pay With Points Confirmation");
			alert.setHeaderText(null);
			alert.setGraphic(null);
			alert.setContentText("Complete checkout by paying with points.\n\nYour current points: "
					+ Main.currentUser.getLoyalty_points() + "\nPoints deducted for this order: -" + pointsRequired);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				CartCheckoutController.order.setPayWith(2); // Ref: Order.class method setPayWith
															// (1: pay with card, 2: pay with points)
				resetCartAfterCheckout();

			}
		}
	}

	private void resetCartAfterCheckout() {
		// add loyalty points of order to user account if paid with card
		if (CartCheckoutController.order.getPayWith() == 1) {
			Main.currentUser.setLoyalty_points(Main.currentUser.getLoyalty_points() + this.points);
		} else if (CartCheckoutController.order.getPayWith() == 2) {
			Main.currentUser
					.setLoyalty_points(Main.currentUser.getLoyalty_points() - (POINTS_COST_PER_MOVIE * this.points));
		}
		Main.warehouseFacade.addOrderToWarehouseList(CartCheckoutController.order);
		Main.currentUser.addOrderToOrderList(CartCheckoutController.order);

		// reset variables
		this.tableView.getItems().clear();
		CartCheckoutController.order = new Order();
		this.points = 0;

		// reset text fields
		this.txtorderID.clear();
		this.txtPointsToBeEarned.clear();
		this.txtTotal.clear();
		this.txtPointsAvail.setText(String.valueOf(Main.currentUser.getLoyalty_points()));
	}

	private void populateTableView() {
		// title column
		this.titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

		// price column
		this.priceColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));

		this.tableView.setItems(getMoviesInOrder());
	}

	private void populateListView() {
		String item1 = "Name: " + Main.currentUser.getUserName();
		String item2 = "User ID: " + Main.currentUser.getUserID();
		String item3 = "Location distance: " + Main.currentUser.getLocationDistance();
		String item4 = "Order to be placed on: " + CartCheckoutController.order.getOrdered_date();
		String item5 = "Return this order by: " + CartCheckoutController.order.getExpiryDate();

		this.listView.getItems().addAll(item1, item2, item3, item4, item5);
	}

	private void populateCartInfo() {
		this.txtorderID.setText(String.valueOf(CartCheckoutController.order.getOrderID()));

		double total = 0.0;
		for (Movie m : CartCheckoutController.order.getOrder()) {
			total = total + m.getFee();
			this.points++;
		}

		CartCheckoutController.order.setOrderTotal(total);
		this.txtTotal.setText(String.valueOf(CartCheckoutController.order.getOrderTotal()));

		this.txtPointsToBeEarned.setText(String.valueOf(points));
		this.txtPointsAvail.setText(String.valueOf(Main.currentUser.getLoyalty_points()));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		order.setOrderBelongsTo(Main.currentUser);

		populateTableView();
		populateListView();
		populateCartInfo();

		this.inpersonBttn.setOnAction(e -> {
			Operator op = new Operator();
			String notif = op.completeAlternativeOrder(OrderMethod.ORDER_IN_PERSON);

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Order In-Person Confirmation");
			alert.setHeaderText(null);
			alert.setGraphic(null);
			alert.setContentText("Message from Operator():\n" + notif
					+ "\n\nPlease provide your UserID to an in-store staff if you would like the order to be added to your account.\n\nUserID: "
					+ Main.currentUser.getUserID());
			alert.showAndWait();
		});

		this.dialinBttn.setOnAction(e -> {
			Operator op = new Operator();
			String notif = op.completeAlternativeOrder(OrderMethod.ORDER_VIA_PHONE);

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Order Dial-In Confirmation");
			alert.setHeaderText(null);
			alert.setGraphic(null);
			alert.setContentText("Message from Operator():\n" + notif
					+ "\n\nPlease provide your UserID to an dial-in operator if you would like the order to be added to your account.\n\nUserID: "
					+ Main.currentUser.getUserID());
			alert.showAndWait();
		});
	}

	private void backtoUserDashboard(ActionEvent event) {
		try {
			Parent userDashParent = FXMLLoader.load(getClass().getClassLoader().getResource("UserDash.fxml"));
			Scene userDashScene = new Scene(userDashParent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(userDashScene);
			window.show();

			userDashScene.setFill(Color.TRANSPARENT);
			userDashParent.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					x = event.getSceneX();
					y = event.getSceneY();
				}
			});
			userDashParent.setOnMouseDragged(new EventHandler<MouseEvent>() {
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

	// get all movies ordered
	public ObservableList<Movie> getMoviesInOrder() {
		ObservableList<Movie> moviesInOrder = FXCollections.observableArrayList();
		for (Movie m : CartCheckoutController.order.getOrder()) {
			moviesInOrder.add(m);
		}
		return moviesInOrder;
	}
}
