package com.bytecoders.costs.controller;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.bytecoders.connection.DBConnection;
import com.bytecoders.costs.dao.CostsDAOImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Costs_Controller {
	
	// The database connection
	DBConnection dbcon = new DBConnection();
	Connection con = dbcon.openConnection();

	// Costs Dao Implementation 
	CostsDAOImpl cdi = new CostsDAOImpl(con);

	@FXML
	private TextField batchIDTField;
	@FXML
	private Label batchCostLbl;
	
    @FXML
    private Button findBatchBtn;

    @FXML
    //private ChoiceBox<String> idList;
    private ComboBox<String> idList;

    @FXML
    private Button refreshBtn;
	
    public void refreshIDs() {
    	List<String> ids = cdi.getAllIds();
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
	
	public void searchBatch(ActionEvent actionEvent) {
		String batchId = idList.getSelectionModel().getSelectedItem();

		// validate input
		if ( batchId.contentEquals("") || batchId.contentEquals(" ") ) {
			Alert alertMsg = new Alert(AlertType.INFORMATION,"Please provide batch ID");
			alertMsg.show();
			batchCostLbl.setText("Please provide batch ID");
			batchIDTField.requestFocus();
		}
		else {		
			// get the cost
			double cost;
			cost = cdi.getBatchCost(Integer.parseInt(batchId));
			
			if ( cost >= 0 ) {
				batchCostLbl.setText(String.valueOf(cost));
			}
			else {
				batchCostLbl.setText("Cost not found.");
			}
		}

	}

	public void reset(ActionEvent actionEvent) {
		//batchIDTField.setText("");
		idList.getSelectionModel().clearSelection();
		batchCostLbl.setText("0");
	}
}
