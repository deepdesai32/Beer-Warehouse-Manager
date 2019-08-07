package com.bytecoders.receipt;

import javafx.application.Application;
import javafx.beans.DefaultProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// this class is only for testing the GUI for this feature, and shall be removed 
// after the code has been moved to the BeerWarehouseMain_Controller class
@DefaultProperty("children")
public class ReceiptGUI extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Receipt.fxml"));
	
		Scene scene = new Scene(root);
		primaryStage.setTitle("Generate Receipt");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		// Launches ReceiptGUI Generation GUI
		launch(args);
		
		// remove me
		System.out.println("Print Receipt...");
	}
}
