package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import credential.User;
import fulfillment.Order;
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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logistics.Movie;

public class AdminDashController implements Initializable {

	private double x, y;
	public static Movie selectedMovie;
	public static User selectedUser;
	public static Order selectedOrder;
	private ObservableList<String> obsMovies = FXCollections.observableArrayList();
	private ObservableList<Integer> obsUsers = FXCollections.observableArrayList();
	private ObservableList<Integer> obsOrders = FXCollections.observableArrayList();

	@FXML
	private Button fulfilBttn, logoutBttn, movieEditBttn, newMovieBttn, newOrderBttn, newUserBttn, orderEditBttn,
			userEditBttn, removeMovieBttn, filterUserBttn, removeUserBttn, filterOrderBttn, removeOrderBttn,
			adminManageBttn, dialinBttn;

	@FXML
	private TextField txtuserfilter, txtorderfilter;

	@FXML
	private ListView<String> moviesListView;

	@FXML
	private ListView<Integer> ordersListView;

	@FXML
	private ListView<Integer> usersListView;

	private void populateMoviesListView() {
		for (Character c : Main.s.getInventory().getMoviesMap().keySet()) {
			for (Movie m : Main.s.getInventory().getMoviesMap().get(c)) {
				obsMovies.add(m.getTitle());
			}
		}
		this.moviesListView.setItems(obsMovies);
	}

	private void populateUsersListView() {
		for (Integer i : Main.s.getUsers().keySet()) {
			obsUsers.add(Main.s.getUsers().get(i).getUserID());
		}
		this.usersListView.setItems(obsUsers);
	}

	private void populateOrdersListView() {
		for (Order o : Main.warehouseFacade.getListOfAllOrders()) {
			obsOrders.add(o.getOrderID());
		}
		this.ordersListView.setItems(obsOrders);
	}

	@FXML
	public void handleEditMovie(ActionEvent event) {
		selectedMovie = Main.s.getMovieFromTitle(moviesListView.getSelectionModel().getSelectedItem());
		showWindow(event, "MovieEdit.fxml");
	}

	@FXML
	public void handleRemoveMovie(ActionEvent event) {
		selectedMovie = Main.s.getMovieFromTitle(moviesListView.getSelectionModel().getSelectedItem());
		Main.s.getInventory().removeMovieFromInventory(selectedMovie);
		boolean removed = false;
		if (Main.s.getMovieFromTitle(selectedMovie.getTitle()) == null)
			removed = true;
		System.out.println(selectedMovie.getTitle() + " removed? " + removed);

		int index = this.moviesListView.getSelectionModel().getSelectedIndex();
		obsMovies.remove(index);
	}

	@FXML
	public void handleNewMovie(ActionEvent event) {
		showWindow(event, "MovieNew.fxml");
	}

	@FXML
	public void handleFilterUser(ActionEvent event) {
		ObservableList<Integer> obsFilteredUsers = FXCollections.observableArrayList();

		for (Integer key : Main.s.getUsers().keySet()) {
			if (String.valueOf(Main.s.getUsers().get(key).getUserID()).startsWith(txtuserfilter.getText())) {
				obsFilteredUsers.add(Main.s.getUsers().get(key).getUserID());
			}
		}

		obsUsers.setAll(obsFilteredUsers);
	}

	@FXML
	public void handleRemoveUser(ActionEvent event) {
		selectedUser = Main.s.getUserFromUserID(usersListView.getSelectionModel().getSelectedItem());
		// System.out.println(selectedUser.getUserID());

		Main.s.removeUser(selectedUser);
		boolean exists = false;
		for (Integer key : Main.s.getUsers().keySet()) {
			if (Main.s.getUsers().get(key).equals(selectedUser)) {
				exists = true;
			}
		}
		if (!exists)
			System.out.println("User " + selectedUser.getUserID() + " removed successfully.");
		else
			System.out.println("User " + selectedUser.getUserID() + " not removed.");

		handleFilterUser(event);
	}

	@FXML
	public void handleFilterOrder(ActionEvent event) {
		ObservableList<Integer> obsFilteredOrders = FXCollections.observableArrayList();

		for (Order o : Main.warehouseFacade.getListOfAllOrders()) {
			if (String.valueOf(o.getOrderID()).startsWith(txtorderfilter.getText())) {
				obsFilteredOrders.add(o.getOrderID());
			}
		}

		obsOrders.setAll(obsFilteredOrders);
	}

	@FXML
	public void handleRemoveOrder(ActionEvent event) {
		selectedOrder = Main.warehouseFacade.getOrderFromOrderID(ordersListView.getSelectionModel().getSelectedItem());

		Main.warehouseFacade.removeOrderFromWarehouseList(selectedOrder);
		Main.currentUser.removeOrderFromOrderList(selectedOrder);

		boolean exists = false;
		for (Order o : Main.warehouseFacade.getListOfAllOrders()) {
			if (o.equals(selectedOrder))
				exists = true;
		}
		if (!exists)
			System.out.println("Order " + selectedOrder.getOrderID() + " removed successfully from warehouse.");
		else
			System.out.println("Order " + selectedOrder.getOrderID() + " not removed from warehouse.");

		handleFilterOrder(event);
	}

	@FXML
	public void handleUserEdit(ActionEvent event) {
		selectedUser = Main.s.getUserFromUserID(usersListView.getSelectionModel().getSelectedItem());
		showWindow(event, "UserEdit.fxml");
	}

	@FXML
	public void handleUserNew(ActionEvent event) {
		showWindow(event, "UserNew.fxml");
	}

	@FXML
	public void handleLogout(ActionEvent event) {
		backtoLoginWindow(event);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// update any current late fees on all orders in the system
		Main.warehouseFacade.updateLateFeesOnAllOrdersInSystem();

		// TODO Auto-generated method stub
		populateMoviesListView();
		// this.moviesListView.setItems(obsMovies);
		this.moviesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		populateUsersListView();
		this.usersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		populateOrdersListView();
		this.ordersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		this.fulfilBttn.setOnAction(event -> {
			selectedOrder = Main.warehouseFacade
					.getOrderFromOrderID(ordersListView.getSelectionModel().getSelectedItem());
			String status = Main.warehouseFacade.fulfilOrder(selectedOrder);
			System.out.println(status);

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Warehouse Facade");
			alert.setHeaderText(null);
			alert.setGraphic(null);
			alert.setContentText(
					"Warehouse operations occur within the facade design. Fulfilment for the following order is completed:\n\n"
							+ status);
			alert.showAndWait();
		});

		this.adminManageBttn.setOnAction(e -> {
			Parent adminManageRoot;
			try {
				adminManageRoot = FXMLLoader.load(getClass().getClassLoader().getResource("AdminManagement.fxml"));

				Scene scene = new Scene(adminManageRoot);
				Stage primaryStage = new Stage();
				primaryStage.setTitle("Admin Management");
				primaryStage.setScene(scene);

				primaryStage.initModality(Modality.APPLICATION_MODAL);
				primaryStage.initOwner(this.adminManageBttn.getScene().getWindow());
				primaryStage.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		this.dialinBttn.setOnAction(e -> {
			Parent dialinRoot;
			try {
				dialinRoot = FXMLLoader.load(getClass().getClassLoader().getResource("DialIn.fxml"));

				Scene scene = new Scene(dialinRoot);
				Stage primaryStage = new Stage();
				primaryStage.setTitle("Dial-In");
				primaryStage.setScene(scene);

				primaryStage.initModality(Modality.APPLICATION_MODAL);
				primaryStage.initOwner(this.adminManageBttn.getScene().getWindow());
				primaryStage.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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

	private void showWindow(ActionEvent event, String s) {
		try {
			Parent movieEditParent = FXMLLoader.load(getClass().getClassLoader().getResource(s));
			Scene movieEditScene = new Scene(movieEditParent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(movieEditScene);
			window.show();

			movieEditScene.setFill(Color.TRANSPARENT);
			movieEditParent.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					x = event.getSceneX();
					y = event.getSceneY();
				}
			});
			movieEditParent.setOnMouseDragged(new EventHandler<MouseEvent>() {
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

}
