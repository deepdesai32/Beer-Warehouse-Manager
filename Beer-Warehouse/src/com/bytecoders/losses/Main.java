package com.bytecoders.losses;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Expire.fxml"));
			

	        Scene scene = new Scene(root, 1000, 1000);
	        //scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
	        primaryStage.setTitle("Expired Product Manager");
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
