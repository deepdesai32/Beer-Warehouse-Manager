package com.bytecoders.order.controller;

import java.text.DecimalFormat;

import com.bytecoders.batch.Batch;
import com.bytecoders.batch.dao.BatchDao;
import com.bytecoders.batch.dao.BatchDaoImpl;
import com.bytecoders.client.Clients;
import com.bytecoders.connection.DBConnection;
import com.bytecoders.employee.Employee;
import com.bytecoders.order.Order;
import com.bytecoders.order.dao.OrdersDao;
import com.bytecoders.order.dao.OrdersDaoImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

public class OrderController {

	@FXML
	private ComboBox<Clients> cmbClient;

	@FXML
	private ListView<Batch> lstAvailableBatches;

	@FXML
	private ListView<Batch> lstSelectedBatches;

	@FXML
	private Button btnSelectBatch;

	@FXML
	private Button btnDeselectBatch;

	@FXML
	private TextField txtSellingPrice;

	@FXML
	private Button btnCreateOrder;

	@FXML
	private Label lblProfit;

	@FXML
	private Label lblTotalCost;

	@FXML
	private Label lblErrorInvalidPrice;

	@FXML
	private Label lblErrorSelectedBatch;

	@FXML
	private Label lblErrorClientNotSelected;

	@FXML
	private Label lblErrorEmployeeNotSelected;

	@FXML
	private ComboBox<Employee> cmbEmployee;

	@FXML
	private Button btnClear;

	@FXML
	private ProgressIndicator progressIndicatorAvailable;

	// Creates and does initial connection to server
	DBConnection conn = null;
	// Creates the BatchDao for interacting with the database
	OrdersDao ordersDao = null;
	// Creates the BatchDao for interacting with the database
	BatchDao batchDao = null;

	Order currentOrder = new Order();

	private Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

	@FXML
	//Action of the Clear button
	void clearFields(ActionEvent event) {
		clearFields();
	}

	//Validate all the data on the GUI and return a boolean informing if it is valid or not
	private boolean validateOrder() {
		// Changes the error label visibility depending if the order has a batch into it

		////Checks if the order has any batches in it
		if (currentOrder.getBatchList().size() < 0)
			lblErrorSelectedBatch.setVisible(true);
		else
			lblErrorSelectedBatch.setVisible(false);

		//Convert the Selling price and validate
		try {
			Double.parseDouble(txtSellingPrice.getText());
			lblErrorInvalidPrice.setVisible(false);
		} catch (Exception e) {
			lblErrorInvalidPrice.setVisible(true);
		}

		//Set the Client for the one selected and check if it is valid
		currentOrder.setClient(cmbClient.getSelectionModel().getSelectedItem());
		if (currentOrder.getClient() == null)
			lblErrorClientNotSelected.setVisible(true);
		else
			lblErrorClientNotSelected.setVisible(false);

		//Set the Employee for the one selected and check if it is valid
		currentOrder.setEmployee(cmbEmployee.getSelectionModel().getSelectedItem());
		if (currentOrder.getEmployee() == null)
			lblErrorEmployeeNotSelected.setVisible(true);
		else
			lblErrorEmployeeNotSelected.setVisible(false);

		// If there are any error return false, else return true
		if (lblErrorClientNotSelected.isVisible() || lblErrorEmployeeNotSelected.isVisible()
				|| lblErrorInvalidPrice.isVisible() || lblErrorSelectedBatch.isVisible())
			return false;
		else
			return true;

	}

	//Clear all the fields on the GUI
	private void clearFields() {
		cmbClient.getSelectionModel().select(null);
		cmbEmployee.getSelectionModel().select(null);
		lstSelectedBatches.getItems().clear();
		// Populates the list with the batches as soon as the GUI loads
		loadAvailableBatches();
		txtSellingPrice.clear();
		lblProfit.setText("$0");
		lblTotalCost.setText("$0");
		resetErrorMessages();
	}

	@FXML
	//Action for the create Button
	void createOrder(ActionEvent event) {
		//Validates the fields
		if (validateOrder()) {
			//Case all the fields are valid, it inserts the order on the database
			if (ordersDao.insertOrder(currentOrder)) {
				alertInfo.setHeaderText("Order has been Created");
				alertInfo.setContentText("Order created and the batche(s) have been removed from stock");
				clearFields();
				alertInfo.show();
				
			} else {
				alertInfo.setHeaderText("Error while creating Order");
				alertInfo.setContentText("There was an error while creating order, please contact admin");
				alertInfo.show();
			}
		} else {
			alertInfo.setHeaderText("Provide all fields");
			alertInfo.setContentText("There are invalid fields, please correct them and try again");
			alertInfo.show();
		}
	}

	@FXML
	//Event for when the user changes the price of the order
	void priceChanged(ActionEvent event) {
		recalculateOrderValues();
	}

	@FXML
	//Action for the remove batch button
	void deselectBatch(ActionEvent event) {
		//Calls the method to remove from the selected batches list
		selectDeselectBatch(false);
	}

	@FXML
	//Action for the add batch button
	void selectBatch(ActionEvent event) {
		//Calls the method to remove from the available batches list
		selectDeselectBatch(true);
	}

	//Calculates the order Cost and Profit
	private void recalculateOrderValues() {
		//Gets the total cost of the current order
		double orderTotal = currentOrder.calcTotalCost();

		//Sets the value into the Label for showing the costs
		lblTotalCost.setText("$" + new DecimalFormat("###,###.##").format(orderTotal));
		
		try {
			//Set the total price of the order, based on the textbox Selling price 
			currentOrder.setTotalPrice(Double.parseDouble(txtSellingPrice.getText()));
			lblErrorInvalidPrice.setVisible(false);
		} catch (Exception e) {
			//Case the user have provided an invalid number
			lblErrorInvalidPrice.setVisible(true);
		}

		//Sets the profit of this order
		double profit = currentOrder.getTotalPrice() - orderTotal;

		lblProfit.setText("$" + new DecimalFormat("###,###.##").format(profit));
	}

	//Handles the moving of objects from one List to the other, based on the option informed
	private void selectDeselectBatch(boolean addingBatches) {
		ListView<Batch> lstSource = null;
		ListView<Batch> lstDestination = null;

		//Sets which direction should be used
		if (addingBatches) {
			lstSource = lstAvailableBatches;
			lstDestination = lstSelectedBatches;
		} else {
			lstSource = lstSelectedBatches;
			lstDestination = lstAvailableBatches;
		}

		// Selects a batch on the source list and send it to the destination list
		final int index = lstSource.getSelectionModel().getSelectedIndex();
		// If the selected index is not -1 means there is something selected
		if (index != -1) {
			Batch selectedBatch = lstSource.getSelectionModel().getSelectedItem();
			//Remove from the source
			lstSource.getItems().remove(index);
			//Add the object to the destination 
			lstDestination.getItems().add(selectedBatch);
			
			//If it is adding new batches, call the Add batch, or else removes from it
			if (addingBatches) {
				currentOrder.addBatch(selectedBatch);
			} else {
				currentOrder.removeBatch(selectedBatch);
			}
			//Recalculates the Order value, based on the Batches on the order
			this.recalculateOrderValues();
		}

	}

	// Runs on GUI initialization
	public void initialize() {
		// Set new Connection
		conn = new DBConnection();
		// Creates the Batch Database Object and set the connection
		batchDao = new BatchDaoImpl(conn.getConnection());
		// Creates the Batch Database Object and set the connection
		ordersDao = new OrdersDaoImpl(conn.getConnection());
		// Sets default Title for all the alerts
		alertInfo.setTitle("Batch");

		// Populates the list with the batches as soon as the GUI loads
		loadAvailableBatches();
		// Gets all the items and populate the combobox
		fillClientsCombo();

		fillEmployeesCombo();
		// Hides all the error messages
		resetErrorMessages();
	}

	//Clear all the error messages
	private void resetErrorMessages() {
		lblErrorClientNotSelected.setVisible(false);
		lblErrorEmployeeNotSelected.setVisible(false);
		lblErrorInvalidPrice.setVisible(false);
		lblErrorSelectedBatch.setVisible(false);
	}

	// Populate the combo box with all the items on the Database;
	public void fillClientsCombo() {
		ObservableList<Clients> clientsList = FXCollections.observableArrayList(ordersDao.selectAllClients());
		cmbClient.setItems(clientsList);
	}

	// Populate the combo box with all the items on the Database;
	public void fillEmployeesCombo() {
		ObservableList<Employee> employeesList = FXCollections.observableArrayList(ordersDao.selectAllEmployees());
		cmbEmployee.setItems(employeesList);
	}

	// Refreshes the data inside the List, with the up to date Batches on the
	// database
	public void loadAvailableBatches() {
		new Thread(() -> {
			progressIndicatorAvailable.setVisible(true);
			ObservableList<Batch> batchesList = FXCollections.observableArrayList(batchDao.selectAllBatches(4));
			lstAvailableBatches.setItems(batchesList);
			progressIndicatorAvailable.setVisible(false);
		}).start();
	}

}
