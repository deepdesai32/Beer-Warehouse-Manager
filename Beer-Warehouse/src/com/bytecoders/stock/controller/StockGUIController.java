package com.bytecoders.stock.controller;

import java.util.ArrayList;
import java.util.List;

import com.bytecoders.batch.Batch;
import com.bytecoders.batch.dao.BatchDao;
import com.bytecoders.batch.dao.BatchDaoImpl;
import com.bytecoders.connection.DBConnection;
import com.bytecoders.order.dao.OrdersDao;
import com.bytecoders.stock.Stock;
import com.bytecoders.stock.dao.StockDao;
import com.bytecoders.stock.dao.StockDaoImpl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;

public class StockGUIController {
	@FXML
	private ListView<Stock> lstAvailableBatches;
	@FXML
	private ListView<Stock> lstSelectedBatches;
	@FXML
	private Button btnSelectBatch;
	@FXML
	private Button btnDeselectBatch;
	@FXML
	private Button btnUpdateStock;


	@FXML
	private ProgressIndicator progressIndicatorStock;

	@FXML
	private ProgressIndicator progressIndicatorAvailable;

	// Creates and does initial connection to server
	DBConnection conn = null;
	// Creates the BatchDao for interacting with the database
	OrdersDao ordersDao = null;
	// Creates the BatchDao for interacting with the database
	BatchDao batchDao = null;

	StockDao stockDao = null;

	private Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

	ObservableList<Stock> currentStockList = null;
	ObservableList<Stock> stockList = null;

	// Runs on GUI initialization
	public void initialize() {
		// Set new Connection
		conn = new DBConnection();
		// Creates the Batch Database Object and set the connection
		batchDao = new BatchDaoImpl(conn.getConnection());

		stockDao = new StockDaoImpl(conn.getConnection());
		// Sets default Title for all the alerts
		alertInfo.setTitle("Stock");

		// Populates the list with the batches as soon as the GUI loads
		loadCurrentStock();
		loadBatchesAvailableToBeOnStock();
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

	@FXML
	//Action for when the user clicks the update button
	void updateStock(ActionEvent event) {
		int removedBatches = 0, addedBatches = 0;

		//Loops through the Batches available to be on stock(Left)
		for (Stock stock : stockList) {
			// If the Aisle is not equal to zero, it is currently in stock
			// And needs to be removed from it
			if (stock.getAisle() != 0) {
				stockDao.removeFromStock(stock.getBatch());
				System.out.println("Removed from stock " + stock);
				removedBatches++;
			}
		}

		//Loops through the Batches that are on the current Stock List(Right)
		for (Stock stock : currentStockList) {
			// If the Aisle is equal to zero, it is a batch that is not currently in stock
			// So it needs to be inserted into stock
			if (stock.getAisle() == 0) {
				stockDao.insertIntoStock(stock.getBatch());
				System.out.println("Inserted into stock " + stock.getBatch());
				addedBatches++;
			}
		}

		// Reload the lists with the current data

		loadCurrentStock();
		loadBatchesAvailableToBeOnStock();

		alertInfo.setHeaderText("Stock Updated");
		alertInfo.setContentText("There were " + addedBatches + " batches added, and " + removedBatches + " removed");
		alertInfo.show();
	}

	// Refreshes the data inside the List, with the up to date Batches on the
	// database
	public void loadCurrentStock() {
		//Uses threads to no block the GUI and run faster
		new Thread(() -> {
			//Set the progression indicator for the user to know it is loading
			progressIndicatorStock.setVisible(true);
			//Clears the list and then put all the information of the current stock in it
			lstSelectedBatches.setItems(null);
			currentStockList = FXCollections.observableArrayList(stockDao.selectListOfStocks());
			lstSelectedBatches.setItems(currentStockList);

			progressIndicatorStock.setVisible(false);
		}).start();
	}

	//
	public void loadBatchesAvailableToBeOnStock() {
		//Uses threads to no block the GUI and run faster
		new Thread(() -> {
			//Set the progression indicator for the user to know it is loading
			progressIndicatorAvailable.setVisible(true);
			//Clears the list and then put all the information of the current stock in it
			lstAvailableBatches.setItems(null);
			// Gets All the batches that have not expired and are not in stock
			List<Batch> batchAvailableList = batchDao.selectAllBatches(5);
			List<Stock> StockAvailableList = new ArrayList<Stock>();

			//Is necessary to create a list of stocks, so it add the batches into empty stocks objects
			for (Batch batch : batchAvailableList) {
				Stock stock = new Stock();
				stock.setBatch(batch);
				StockAvailableList.add(stock);
			}

			//Populate the list with the final information on available batches
			stockList = FXCollections.observableArrayList(StockAvailableList);
			lstAvailableBatches.setItems(stockList);
			progressIndicatorAvailable.setVisible(false);
		}).start();
	}

	//Handles the moving of objects from one List to the other, based on the option informed
	private void selectDeselectBatch(boolean addingBatches) {
		ListView<Stock> lstSource = null;
		ListView<Stock> lstDestination = null;

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
			Stock selectedBatch = lstSource.getSelectionModel().getSelectedItem();
			lstSource.getItems().remove(index);
			lstDestination.getItems().add(selectedBatch);
		}

	}
}
