package com.bytecoders.management;


 // this class is only for testing the GUI for this feature, and shall be removed 
// after the code has been moved to the BeerWarehouseMain_Controller class
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagementGUI extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Management.fxml"));
	
		Scene scene = new Scene(root);
		primaryStage.setTitle("Batch Management");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
