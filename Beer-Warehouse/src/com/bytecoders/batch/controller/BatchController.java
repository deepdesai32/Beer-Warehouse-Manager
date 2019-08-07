package com.bytecoders.batch.controller;

import com.bytecoders.batch.Batch;
import com.bytecoders.batch.dao.BatchDao;
import com.bytecoders.batch.dao.BatchDaoImpl;
import com.bytecoders.connection.DBConnection;
import com.bytecoders.item.ItemRegistration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class BatchController {

	@FXML
	private TabPane tabs;

	@FXML
	private Tab tabBatchList;

	@FXML
	private Tab tabBatchEdit;

	@FXML
	private DatePicker dtpExpiryDate;

	@FXML
	private DatePicker dtpReceivedDate;

	@FXML
	private TextField txtBatchNumber;

	@FXML
	private TextField txtCost;

	@FXML
	private ComboBox<ItemRegistration> cmbItem;

	@FXML
	private TextField txtQuantity;

	@FXML
	private TextField txtWeight;

	@FXML
	private Button btnCreateBatch;

	@FXML
	private Button btnFind;

	@FXML
	private Button btnUpdate;

	@FXML
	private ListView<Batch> lstBatches;

	@FXML
	private Button btnDelete;

	@FXML
	private Label lblReceivedDate;

	@FXML
	private Label lblExpiryDate;

	@FXML
	private Label lblQuantity;

	@FXML
	private Label lblCost;

	@FXML
	private Label lblItem;

	@FXML
	private Label lblBatch;

	@FXML
	private Label lblWeight;

    @FXML
    private ProgressIndicator progressIndicator;
	
	private Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
	


	// Creates and does initial connection to server
	DBConnection conn = null;
	// Creates the BatchDao for interacting with the database
	BatchDao batchDao = null;

	@FXML
	void clear(ActionEvent event) {
		// Clear all the fields on the GUI
		clearFields();
	}

	@FXML
	// CALLED WHEN UPDATE BUTTON IS CLICKED
	// Updates the informed Batch
	void updateBatch(ActionEvent event) {
		// Validate the fields and if the result is not null it tries to update based on
		// the ID
		Batch updateBatch = validateFields(true);
		if (updateBatch != null) {
			// If the Update doesnt work, sends an error message, otherwise inform the user
			// about success			
			if (batchDao.updateBatch(updateBatch)) {
				alertInfo.setHeaderText("Batch updated");
				alertInfo.setContentText("Your batch data has been updated and stored in stock");
				alertInfo.show();
			} else {
				alertInfo.setHeaderText("Error while updating Batch");
				alertInfo.setContentText("There was an error while updating batch, check if your batchID is correct");
				alertInfo.show();
			}

			// Refresh the list of batches on the GUI, so the data is accurate
			refreshBatchList();
		}

	}

	@FXML
	// CALLED WHEN DELETE BUTTON IS CLICKED
	// Delete a batch based on the ID
	void deleteBatch(ActionEvent event) {
		Batch deleteBatch = validateFields(true);
		// Validate the fields and if the result is not null it tries to delete based on
		// the ID
		if (deleteBatch != null) {
			// If the Delete doesn't work, sends an error message, otherwise inform the user
			// about success
			if (batchDao.deleteBatch(deleteBatch)) {
				alertInfo.setHeaderText("Batch Deleted");
				alertInfo.setContentText("Your batch data has been deleted");
				alertInfo.show();
				clearFields();
			} else {
				alertInfo.setHeaderText("Error while deleting Batch");
				alertInfo.setContentText("You can not delete this batch because it is being used on an order already");
				alertInfo.show();
			}
			refreshBatchList();
		}
	}

	@FXML
	// CALLED WHEN CREATE BATCH BUTTON IS CLICKED
	// Creates a new batch with the data informed
	void createBatch(ActionEvent event) {
		Batch insertBatch = validateFields(false);
		// Validate the fields and if the result is not null it tries to insert all the
		// data
		if (insertBatch != null) {
			// If the Insert doesn't work, sends an error message, otherwise inform the user
			// about success
			if (batchDao.insertBatch(insertBatch)) {
				alertInfo.setHeaderText("New Batch created");
				alertInfo.setContentText("Your batch has been created and stored in stock");
				alertInfo.show();
				clearFields();
			} else {
				alertInfo.setHeaderText("Error while creating Batch");
				alertInfo.setContentText("There was an error while creating batch, please contact admin");
				alertInfo.show();
			}
			refreshBatchList();
		}
	}

	@FXML
	// CALLED WHEN FIND BUTTON IS CLICKED
	// Find the batch based on the batchID
	void findBatch(ActionEvent event) {
		try {
			Batch findBatch = new Batch();
			findBatch.setID(Integer.parseInt(txtBatchNumber.getText()));
			findBatch = batchDao.selectBatch(findBatch);
			setBatchFields(findBatch);
		} catch (NumberFormatException ex) {
			alertInfo.setHeaderText("Error while trying to find batch");
			alertInfo.setContentText("Please provide a numeric ID");
			alertInfo.show();
		} catch (NullPointerException ex) {
			alertInfo.setHeaderText("Batch could not be found");
			alertInfo.setContentText("Check the Id and try again");
			alertInfo.show();
		}

	}

	// Clear all the fields
	public void clearFields() {
		txtBatchNumber.clear();
		dtpReceivedDate.setValue(null);
		dtpExpiryDate.setValue(null);
		txtCost.clear();
		txtQuantity.clear();
		cmbItem.getSelectionModel().select(null);
		txtWeight.clear();
		resetErrorMessages();
	}

	// Runs on GUI initialization
	public void initialize() {
		// Set new Connection
		conn = new DBConnection();
		// Creates the Batch Database Object and set the connection
		batchDao = new BatchDaoImpl(conn.getConnection());
		// Sets default Title for all the alerts
		alertInfo.setTitle("Batch");

		// Populates the list with the batches as soon as the GUI loads
		refreshBatchList();
		// Gets all the items and populate the combobox
		fillItemsCombo();
		// Hides all the error messages
		resetErrorMessages();

	}

	// Hides all the error message at once
	public void resetErrorMessages() {
		lblReceivedDate.setVisible(false);
		lblExpiryDate.setVisible(false);
		lblQuantity.setVisible(false);
		lblCost.setVisible(false);
		lblItem.setVisible(false);
		lblBatch.setVisible(false);
		lblWeight.setVisible(false);
	}

	// Called when GUI is closed, and closes the Connectioon to the DB
	public void shutdown() {
		conn.closeConnection();
	}

	// Refreshes the data inside the List, with the up to date Batches on the
	// database
	public void refreshBatchList() {
		new Thread(() -> {
			progressIndicator.setVisible(true);
			lstBatches.setItems(null);
			ObservableList<Batch> batchesList = FXCollections.observableArrayList(batchDao.selectAllBatches(1));
			lstBatches.setItems(batchesList);
			progressIndicator.setVisible(false);
		}).start();
	}

	// Populate the combo box with all the items on the Database;
	public void fillItemsCombo() {
		new Thread(() -> {
			ObservableList<ItemRegistration> itemsList = FXCollections.observableArrayList(batchDao.selectAllItems());
			cmbItem.setItems(itemsList);
		}).start();
	}

	@FXML
	// Event for the mouse click on the List
	// It populates all the fields based on the Batch selected on the list
	void handleMouseClick(MouseEvent event) {
		setBatchFields(lstBatches.getSelectionModel().getSelectedItem());
	}

	// Set all the fields given a Batch
	public void setBatchFields(Batch batch) {
		txtBatchNumber.setText(String.valueOf(batch.getID()));
		txtQuantity.setText(String.valueOf(batch.getItemQuantity()));
		txtCost.setText(String.valueOf(batch.getCost()));
		dtpReceivedDate.setValue(batch.getReceivedDate());
		dtpExpiryDate.setValue(batch.getExpiryDate());
		cmbItem.getSelectionModel().select(batch.getItem());
		txtWeight.setText(String.valueOf(batch.getTotalWeight()));
	}

	// Check all the fields, convert them, and return a Batch.
	// If any of the fields have invalid data, it returns a NULL reference
	public Batch validateFields(boolean validateBatchID) {
		Batch batch = new Batch();
		Exception hadExecption = null;

		try {
			batch.setCost(Double.parseDouble(txtCost.getText()));
			lblCost.setVisible(false);
		} catch (NumberFormatException ex) {
			lblCost.setVisible(true);
			hadExecption = ex;
		}

		try {
			batch.setItemQuantity(Integer.parseInt(txtQuantity.getText()));
			lblQuantity.setVisible(false);
		} catch (NumberFormatException ex) {
			lblQuantity.setVisible(true);
			hadExecption = ex;
		}

		try {
			batch.setTotalWeight(Double.parseDouble(txtWeight.getText()));
			lblWeight.setVisible(false);
		} catch (NumberFormatException ex) {
			lblWeight.setVisible(true);
			hadExecption = ex;
		}

		batch.setReceivedDate(dtpReceivedDate.getValue());
		if (batch.getReceivedDate() == null) {
			hadExecption = new Exception("Invalid Received date");
			lblReceivedDate.setVisible(true);
		} else {
			lblReceivedDate.setVisible(false);
		}

		batch.setExpiryDate(dtpExpiryDate.getValue());
		if (batch.getExpiryDate() == null) {
			hadExecption = new Exception("Invalid Expiry date");
			lblExpiryDate.setVisible(true);
		} else {
			lblExpiryDate.setVisible(false);
		}

		batch.setItem(cmbItem.getSelectionModel().getSelectedItem());
		if (batch.getItem() == null) {
			hadExecption = new Exception("Invalid Item");
			lblItem.setVisible(true);
		} else {
			lblItem.setVisible(false);
		}

		//Checks if the method should validate the BatchID of the object
		if (validateBatchID) {
			try {
				batch.setID(Integer.parseInt(txtBatchNumber.getText()));
				lblBatch.setVisible(false);
			} catch (NumberFormatException ex) {
				lblBatch.setVisible(true);
				hadExecption = ex;
			}
		}

		//Case there was any error while validating, returns a null reference, otherwise returns the batch  object
		if (hadExecption != null)
			return null;
		else
			return batch;
	}

}
