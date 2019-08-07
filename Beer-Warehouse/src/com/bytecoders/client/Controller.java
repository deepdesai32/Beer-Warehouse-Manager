package com.bytecoders.client;

import com.bytecoders.connection.DBConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;



public class Controller {
	
	//client ID textfield
	@FXML
	TextField ClientID = new TextField();
	
	//client name textfield
	@FXML
	TextField Name = new TextField();
	
	//address textfields and combobox
	@FXML
	TextField Street = new TextField();

	@FXML
	TextField City = new TextField();
	
	@FXML
	TextField PCode = new TextField();

	@FXML
	private ComboBox<String> Province;
	
	
	//client phone number
	@FXML
	TextField PhoneNum = new TextField();
	
	
	//add client button
	@FXML
	Button SubButton = new Button();

	//update client button
	@FXML
	Button UpdateButton = new Button();
	
	//delete client button
	@FXML
	Button DeleteButton = new Button();
	
	//connection object from DBConnection Class 
	DBConnection conn = null;
	
	//ClientDB object
	ClientDB cdb = null;
	
	
	@FXML
	private void initialize() {
		
		//DBconnection object initialized
		conn = new DBConnection();
		//ClientDB object initialized and passing connection
		cdb = new ClientDB(conn.getConnection());
		
		//within province combobox, initializes first option
		Province.getSelectionModel().selectFirst();
		
		
		
	}
	
	//adding client button
	public void SubmitInfo(ActionEvent actionEvent) {


		// getting value of province and storing it into string
		String Prov = Province.getValue().toString();

		// if user doesnt enter all fields, then error will occur
		if (ClientID.getText().contentEquals("") || Name.getText().contentEquals("")
				|| Street.getText().contentEquals("") || City.getText().contentEquals("")
				|| Prov.contentEquals("Province") || PCode.getText().contentEquals("")
				|| PhoneNum.getText().contentEquals("")) {

			Alert error = new Alert(AlertType.ERROR);

			error.setHeaderText("Error!!!");

			error.setContentText("You did not submit all information for the client!");

			error.showAndWait();
			
			


		} else {
			
			//string to get all values of address to create final address string
			String Address= Street.getText() + ", " + City.getText() + ", " +  Province.getValue().toString()  +  " " + PCode.getText();
			
			//gets clientID from user
			String ClientID1 = ClientID.getText();
			
			//gets name from user
			String Name1 = Name.getText();
			
			
			//gets phoneNumber from user
			String phoneNumber = PhoneNum.getText();
			
			
			
			
			//creating new client object and passing all variables from client
			Clients c = new Clients(ClientID1,Name1,Address,phoneNumber);
			
			//executing add query using clientDB and passing client object, stored in variable
			boolean result1 = cdb.add(c);
			
			
			//if true then that means adding the client wass successful
			if(result1==true) {
			
			Alert info = new Alert(AlertType.INFORMATION);
			info.setTitle("Client Information");

			info.setHeaderText("Here is the Client.");

			info.setContentText("Client-ID: " + ClientID.getText() + "\nName: " + Name.getText() + "\nAddress: "
					+ Street.getText() + " " + City.getText() + " " + Prov + " " + PCode.getText() + "\nContact Info: "
					+ PhoneNum.getText());

			info.showAndWait();
			}
			
			//if false, the client could not be added
			else {
				
				Alert addError = new Alert(AlertType.ERROR);

				addError.setHeaderText("Error!!!");

				addError.setContentText("Client Could not be added!");

				addError.showAndWait();
				
			}
		}

	}

	
	
	// this will update the clients information in the database
	public void UpdateInfo(ActionEvent actionEvent) {
		
		//string to get all values of address to create final address string
		String Address= Street.getText() + ", " + City.getText() + ", " +  Province.getValue().toString()  +  " " + PCode.getText();
		
		//gets clientID from user
		String ClientID1 = ClientID.getText();
		
		//gets name from user
		String Name1 = Name.getText();
		
		//gets province from user
		String Prov = Province.getValue().toString();

		
		//new client object
		Clients z = new Clients();
		
		//sets address as address user input
		z.setAddress(Address);
		
		//sets clientID as clientID user input
		z.setClientID(ClientID1);
		
		//sets Name as Name1 user input
		z.setName(Name1);
		
		//sets phoneNum as phoneNum from user input
		z.setPhoneNum(PhoneNum.getText());
		
		
		//executing update query using clientDB and passing client object, stored in variable
		boolean results = cdb.update(z);

		
		//tells user client has been updated if results in true
		if(results == true) {
			
		Alert info1 = new Alert(AlertType.INFORMATION);
		
		info1.setTitle("Client Updated");

		info1.setHeaderText("Here is the Clients Updated Info.");

		info1.setContentText("Client-ID: " + ClientID.getText() + "\nName: " + Name.getText() + "\nAddress: "
				+ Street.getText() + " " + City.getText() + " " + Prov + " " + PCode.getText() + "\nContact Info: "
				+ PhoneNum.getText());

		info1.showAndWait();
		}
		
		//cannot update if results is false
		else {
			
			Alert updateError = new Alert(AlertType.ERROR);

			updateError.setHeaderText("Error!!!");

			updateError.setContentText("Client Could not be updated!");

			updateError.showAndWait();
			
		}
		
	}
	
	//Delete client button
	public void DeleteInfo(ActionEvent actionEvent) {
		
		
		//new client object
		Clients e = new Clients();
		
		//setting clientID from user input
		e.setClientID(ClientID.getText());
		
		//executing query using clientDB and passing client object, stored in variable
	    boolean result2 =  cdb.delete(e);
	    
	    //if true then execution of query was successfull
	    if (result2 == true) {
		
		Alert info2 = new Alert(AlertType.INFORMATION);
		
		info2.setTitle("Client Deleted");

		info2.setHeaderText("Here is the client you Deleted!");

		info2.setContentText("Client-ID: " + ClientID.getText() + "\nName: " + Name.getText() + "\nContact Info: "
				+ PhoneNum.getText());

		info2.showAndWait();
	    }
	    
	    //if false then execution of query was unsuccessful
	    else{
	    	Alert error = new Alert(AlertType.ERROR);
	    	
	    	error.setTitle("Client Deletion Error");
	    	error.setHeaderText("Client could not be deleted");
	    	error.setContentText("This client could not be deleted because it is referenced in another table!");
	    }
		
	}

}
