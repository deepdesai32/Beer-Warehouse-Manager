package com.bytecoders.management.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

import com.bytecoders.connection.DBConnection;
import com.bytecoders.management.dao.ManagementDAOImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Management_Controller {

	@FXML
	private Button viewBtn;

	@FXML
	private Text outText;

	@FXML
	private Button saveBtn;

	@FXML
	private DatePicker dateFrom;

	@FXML
	private DatePicker dateTo;

	private DBConnection dbcon = new DBConnection();
	private Connection conn = dbcon.openConnection();
	private ManagementDAOImpl mdi;
	private String data;	// this will hold the information we want to find.
	// file related data
	private FileChooser fileChooser;
	private String filePath;
	private PrintStream outStream;

	@FXML
	void getInfo(ActionEvent event) {
		LocalDate dFrom = dateFrom.getValue();
		LocalDate dTo = dateTo.getValue();

		if (dFrom == null) {
			Alert alert = new Alert(AlertType.INFORMATION, "Select initial date.");
			alert.show();
			dateFrom.requestFocus();
		} else if (dTo == null) {
			Alert alert = new Alert(AlertType.INFORMATION, "Select final date.");
			alert.show();
			dateTo.requestFocus();
		} else {
			mdi = new ManagementDAOImpl(conn);

			// Fetch desired data in time interval, converting from LocalDate to SQL Date.
			data = mdi.getInfo(Date.valueOf(dFrom), Date.valueOf(dTo));

			outText.setText(data);
		}
	}

	@FXML
	void saveToFile(ActionEvent event) {
		// create a new stage
		Stage stage = new Stage();
		stage.setTitle("Location to save...");

		// only save if there's something to save
		if (outText.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION, "Nothing to save so far.");
			alert.show();
		} else {
			// get a file choosing dialog
			fileChooser = new FileChooser();
			fileChooser.setTitle("Select file saving location.");

			try {
			// get chosen location
			filePath = fileChooser.showSaveDialog(stage).toString();
			}
			catch (Exception ex) {
				Alert alert = new Alert(AlertType.WARNING, "Content NOT saved yet!");
				alert.show();
			}
			
			// validate file
			if (filePath.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION, "Please select file location.");
				alert.show();
			} else {
				// try saving the file
				try {
					// opens the stream to given filePath.
					outStream = new PrintStream(new FileOutputStream(filePath));
					// get data from GUI and put into file
					outStream.println(outText.getText());
					Alert alert = new Alert(AlertType.INFORMATION, "Contents saved to" + filePath);
					alert.show();

				} catch (IOException ex) {
					Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
					alert.show();
				}
			}
		}
	}

}
