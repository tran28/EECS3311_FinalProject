package application;

import credential.Admin;
import credential.User;
import credential.VideoCoSystem;
import fulfillment.FacadeForWarehouse;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	private Parent root;
	private double xOffset, yOffset;
	
	public static final String CURRENCY = "$";
	public final static VideoCoSystem s = new VideoCoSystem();
	public final static FacadeForWarehouse warehouseFacade = new FacadeForWarehouse();
	public static User currentUser;
	public static Admin currentAdmin;

	@Override
	public void start(Stage primaryStage) {
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();
			scene.setFill(Color.TRANSPARENT);
			root.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
				}
			});
			root.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					primaryStage.setX(event.getScreenX() - xOffset);
					primaryStage.setY(event.getScreenY() - yOffset);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
