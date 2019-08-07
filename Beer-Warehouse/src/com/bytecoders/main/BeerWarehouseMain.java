package com.bytecoders.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BeerWarehouseMain extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/BeerWarehouseMain.fxml"));
		
			Scene scene = new Scene(root);
			primaryStage.setTitle("Beer Warehouse Management");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		}

		public static void main(String[] args) {
		launch(args);
	}
	
}
