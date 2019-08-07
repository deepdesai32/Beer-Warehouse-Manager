package com.bytecoders.receipt.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.List;

import com.bytecoders.connection.DBConnection;
import com.bytecoders.receipt.Receipt;
import com.bytecoders.receipt.dao.ReceiptDAOImpl;
import com.bytecoders.receipt.mail.Mail;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Receipt_Controller {

	@FXML
	private Button searchBtn;

	@FXML
	private Text outText;

	@FXML
	private TextField saveFileTField;

	@FXML
	private TextField sendMailTField;

	@FXML
	private Button saveFileBtn;

	@FXML
	private Button sendMailBtn;

	private DBConnection dbcon = new DBConnection();
	private Connection conn = dbcon.openConnection();
	private ReceiptDAOImpl rdi = new ReceiptDAOImpl(conn);
	
	// the Receipt object which will hold data for printing / saving / sending.
	private Receipt rcpt;

	// file I/O management variables
	private FileChooser fileChooser;	// this will let user choose a file to save data to.
	private String filePath;
	private PrintStream outStream;
	
	// the Mail object will 'send' receipt to given email.
	private Mail mail;

    @FXML
    private ComboBox<String> idList = null;

    @FXML
    private Button refreshBtn;

    public void refreshIDs() {
    	List<String> ids = rdi.getAllIds();
		ObservableList<String> oIds = FXCollections.observableArrayList(ids);
		idList.setItems(oIds);
    }
    
    public void initialize() {
    	refreshIDs();
    }
    
    
    @FXML
    // This method gets all the order IDs.
    // This is used by the ComboBox to populate the items list.
    void refreshIDsList(ActionEvent event) {
    	refreshIDs();
    }
    
    @FXML
	void searchOrder(ActionEvent event) {
		// let's create a receipt object
		rcpt = new Receipt();

		// get data from GUI
		String oID = idList.getSelectionModel().getSelectedItem();
		
		// validate it
		if (oID == null) {
			Alert a = new Alert(AlertType.INFORMATION, "Please provide orderID.\nHit 'Refresh List' if it is empty.");
			a.show();
			refreshBtn.requestFocus();	// send user to desired GUI component.
		} else {
			// all good! let's call our receipt generator to fill receipt object.
			rcpt = rdi.generateReceipt(Integer.parseInt(oID));
			// and put that to the screen
			outText.setText(rcpt.toString());
		}

	}

	@FXML
	void saveFile(ActionEvent event) {
		// create a new stage
		Stage stage = new Stage();
		stage.setTitle("Location to save...");

		// only save if there's something to save
		if (outText.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION, "Nothing to save so far.");
			alert.show();
			refreshBtn.requestFocus();
		} else {
			// get a file choosing dialog
			fileChooser = new FileChooser();
			fileChooser.setTitle("Select file saving location.");

			// get chosen location
			try {
				// using our FileChooser. This will put user file path to filePath var.
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
				// try saving data to the file
				try {
					// get outStream from file path
					outStream = new PrintStream(new FileOutputStream(filePath));
					// print GUI text content to the given file in filePath
					outStream.println(outText.getText());
					
					// and let user know all went ok
					Alert alert = new Alert(AlertType.INFORMATION, "Contents saved to" + filePath);
					alert.show();

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@FXML
	void sendFile(ActionEvent event) {
		// only send e-mail if there is info to send
		if (outText.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION, "Nothing to send so far.");
			alert.setTitle("Send Email");
			alert.show();
			idList.requestFocus();
		} else {

			// get mail recipient from GUI
			String rec = sendMailTField.getText();

			// validate it
			if (rec.isEmpty()) {
				Alert a = new Alert(AlertType.INFORMATION, "Please provide recipient's e-mail");
				a.setTitle("Provide e-Mail of Recipient");
				a.show();
				sendMailTField.requestFocus();
			} else {
				// here we create a Mail object and provide a receiver 'rec', as 
				// well as the message we want to send (taken from GUI).
				mail = new Mail(rec, outText.getText());

				// print mail 'sending' result.
				Alert mailRes = new Alert(AlertType.INFORMATION);
				mailRes.setTitle("Message Sent");
				mailRes.setContentText(mail.Send());
				mailRes.show();
			}
		}
	}

}
