package com.bytecoders.losses;

import com.bytecoders.batch.Batch;
import com.bytecoders.client.ClientDB;
import com.bytecoders.connection.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


public class Controller {

	//button to show all expired items and its cost
	@FXML
	Button Expired = new Button();

	//button to show total of losses
	@FXML
	Button Losses = new Button();

	//gets date from user
	@FXML
	TextField date = new TextField();

	//listview to add batches to store expired items
	@FXML
	private ListView<Batch> listExpired;
	
	//total lost will be displayed within label
	@FXML
	Label LossLabel = new Label();

	//DB connection object
	DBConnection conn = null;
	
	//expired db object
	ExpiredDB edb = null;

	@FXML
	private void initialize() {
		//initiallize connection
		conn = new DBConnection();
		//initialize expired db and passing connection through it
		edb = new ExpiredDB(conn.getConnection());

	}

	//check whats expired button
	public void CheckExpired(ActionEvent actionEvent) {
		
		//validation
		if (date.getText().contentEquals("")) {

			Alert error = new Alert(AlertType.ERROR);

			error.setHeaderText("Error!!!");

			error.setContentText("You did not enter the date!");

			error.showAndWait();

		} else {
			
			//expired DB object
			edb = new ExpiredDB();
			
			//running query through batch class, storing in observabale array list, passing date to run query
			ObservableList<Batch> expired = FXCollections.observableArrayList(edb.selectExpired(date.getText()));
			
			//adding each array list index within a new item in the ListView
			listExpired.setItems(expired);
		}
	}

	public void CheckLosses(ActionEvent actionEvent) {

		//validation
		if (date.getText().contentEquals("")) {

			Alert error = new Alert(AlertType.ERROR);

			error.setHeaderText("Error!!!");

			error.setContentText("You did not enter the date!");

			error.showAndWait();

		} else {
			
			//query is executed by passing the date, then it changes the value of the label to the queried selection
			LossLabel.setText(Double.toString(edb.selectLosses(date.getText())));
			
		}
	}

}
