package com.bytecoders.expired;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Adjust_Remove.fxml"));
			

	        Scene scene = new Scene(root, 400, 300);
	        //scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
	        primaryStage.setTitle("Adjust or Remove Products");
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
