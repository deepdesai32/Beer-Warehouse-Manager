package com.bytecoders.expired;

import com.bytecoders.connection.DBConnection;
import com.bytecoders.losses.ExpiredDB;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


public class Controller {
	
	//button to remove expired batch
	@FXML
	Button mRemove = new Button();

	//textfield to get user batchid through input
	@FXML
	TextField batchID = new TextField();
	
	//connection object from DBConnection class 
	DBConnection conn = null;
	
	//database class object
	RemoveExpiredDB redb = null;

	
	@FXML
	private void initialize() {
		
		//initializing connection object
		conn = new DBConnection();
		
		//initializing database object and passing connection
		redb = new RemoveExpiredDB(conn.getConnection());

	}
	
	
	//remove button
	public void manRemove(ActionEvent actionEvent) {

		//if user doesnt enter all required info, then validation error will occur
		
		if (batchID.getText().contentEquals("")){

			Alert error = new Alert(AlertType.ERROR);

			error.setHeaderText("Error!!!");

			error.setContentText("You did not submit all information needed to remove the product");

			error.showAndWait();
			
			//once the user enters all required info, the information will be displayed as to what was removed
		} else {
			
			//converting user input from string to int
			int batchIDs = Integer.parseInt(batchID.getText());
			
			//removeExpired object creation
	        RemoveExpired f = new RemoveExpired();
	        
	        //setting batchID as user inputted batchid
	        f.setBatchID(batchIDs);
	        
	        

	         //using db object with remove method and passing RemoveExpired object
	         boolean result2 =  redb.remove(f);
	         
	         //if its true, then the query execution was successful
	         if (result2 == true) {

			Alert info = new Alert(AlertType.INFORMATION);
			info.setTitle("Products Manually Removed");

			info.setHeaderText("Here are your products that were removed Manually");

			info.setContentText("Batch ID: " + batchID.getText());

			info.showAndWait();
			
			
			
	         }
	         //if false, then the execution query was unsuccessful
	         else
	         {
	        	 
	        	 Alert error2 = new Alert(AlertType.ERROR);

	 			error2.setHeaderText("Error!!!");

	 			error2.setContentText("Batch Could not be deleted!!");

	 			error2.showAndWait();
	        	 
	         }
		}
	}
	
	
	

}
