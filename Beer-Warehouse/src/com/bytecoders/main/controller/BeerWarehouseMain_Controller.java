package com.bytecoders.main.controller;
/*
 
 * 
 *
 * =====================================================================
 * 
 * 1) Add the FXML file for  GUI, in the proper place, below.
 * 
 * 2) Add the variable for the button that represents your feature;
 *    
 * 3) Add the proper method to handle the events for your GUI;
 * 
 * Example for the Costs GUI.
 * 
 * 1) Location for the fxml file
 * 		private String costsFXMLFile = "/fxml/Costs.fxml";
 * 
 * 2) Variable representing the button in BeerWarehouseMain.fxml
 *  	@FXML
 *    	private Button showCostsBtn;
 *
 * 3) Method for handling button click and then showing your GUI from main window
 * 		@FXML
 *   	void showCosts(ActionEvent event) throws IOException {
 *   		localStage = new Stage();
 *   		Scene yourSCENE = new Scene(FXMLLoader.load(getClass().getResource(costsFXMLFile)));
 *   		
 *   		localStage.setTitle("Batch Cost");
 *   		localStage.setScene(yourSCENE);
 *   		localStage.setResizable(false);
 *   		localStage.show();
 *   	}
 *
 *	
 * 
 * 
 * 
 */
import java.io.IOException;

import com.bytecoders.connection.DBConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BeerWarehouseMain_Controller {

	// Gluing things together
	// -----------------------
	// 
	// Please READ the instructions above, BEFORE modifying this code.
	
	
	
	
	// =======================
	// FXML Files for your GUI
	// =======================
	// Create a variable for your FXML files here.
	private String costsFXMLFile = "/fxml/Costs.fxml";
	private String receiptFXMLFile = "/fxml/Receipt.fxml";
	private String mgmtFXMLFile = "/fxml/Management.fxml";
	private String batchFXMLFile = "/fxml/BatchGUI.fxml";
	private String orderFXMLFile = "/fxml/OrderGUI.fxml";
	private String stockFXMLFile = "/fxml/StockGUI.fxml";
	private String clientFXMLFile = "/fxml/Function1.fxml";
	private String expiredFXMLFile = "/fxml/Adjust_Remove.fxml";
	private String lossesFXMLFile = "/fxml/Expire.fxml";
	private String employeeFXMLFile = "/fxml/EmployeeView.fxml";
	private String itemFXMLFile = "/fxml/ItemRegistrationView.fxml";
	
	
	// add your FXML files here...
	
	
	
	
	// ========================
	// Buttons to call your GUI
	// ========================
	// Create a variable for the button that calls your GUI
    @FXML
    private Button showCostsBtn;

    @FXML
    private Button viewMgmtRepBtn;

    @FXML
    private Button genORcptBtn;
    

    @FXML
    private Button btnBatches;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnStock;
    
    @FXML
    private Button btnClient;
    
    @FXML
    private Button btnRemoveExp;
    
    @FXML
    private Button showLossesBtn;

    @FXML
    private Button btnItem;

    @FXML
    private Button btnEmployee;
    
    // Opens the initial connection to the db
    DBConnection conn = new DBConnection();
    // add your button variable here ...

    
    
    
    // =======================
	// FXML Files for your GUI
	// =======================
	// Create a variable for your FXML files here.	
    @FXML
    void genORcpt(ActionEvent event) throws IOException {
    	localStage = new Stage();
    	Scene rcpt = new Scene(FXMLLoader.load(getClass().getResource(receiptFXMLFile)));
    	
    	localStage.setTitle("Generate Receipt");
    	localStage.setScene(rcpt);
    	localStage.setResizable(false);
    	localStage.show();
    }
    
    @FXML
    void showCosts(ActionEvent event) throws IOException {
    	localStage = new Stage();
    	Scene costs = new Scene(FXMLLoader.load(getClass().getResource(costsFXMLFile)));
    	
    	localStage.setTitle("Batch Cost");
    	localStage.setScene(costs);
    	localStage.setResizable(false);
    	localStage.show();
    }

    @FXML
    void viewMgmtRep(ActionEvent event) throws IOException {
    	localStage = new Stage();
    	Scene mgmtRep = new Scene(FXMLLoader.load(getClass().getResource(mgmtFXMLFile)));
    	
    	localStage.setTitle("Management Report");
    	localStage.setScene(mgmtRep);
    	localStage.setResizable(false);
    	localStage.show();
    }
    
    
    @FXML
    void openBatchesGUI(ActionEvent event) throws IOException {
    	localStage = new Stage();
    	Scene batch = new Scene(FXMLLoader.load(getClass().getResource(batchFXMLFile)));    	
    	localStage.setTitle("Batch");
    	localStage.setScene(batch);
    	localStage.setResizable(false);
    	localStage.show();
    }

    @FXML
    void openOrdersGUI(ActionEvent event) throws IOException {
    	localStage = new Stage();
    	Scene order = new Scene(FXMLLoader.load(getClass().getResource(orderFXMLFile)));    	
    	localStage.setTitle("Order");
    	localStage.setScene(order);
    	localStage.setResizable(false);
    	localStage.show();
    }

    @FXML
    void openStockGUI(ActionEvent event) throws IOException {
    	localStage = new Stage();
    	Scene stock = new Scene(FXMLLoader.load(getClass().getResource(stockFXMLFile)));    	
    	localStage.setTitle("Stock");
    	localStage.setScene(stock);
    	localStage.setResizable(false);
    	localStage.show();
    }

    
    @FXML
    void openClientGUI(ActionEvent event) throws IOException {
    	localStage = new Stage();
    	Scene client = new Scene(FXMLLoader.load(getClass().getResource(clientFXMLFile)));    	
    	localStage.setTitle("Client");
    	localStage.setScene(client);
    	localStage.setResizable(false);
    	localStage.show();
    }
    
    @FXML
    void openRemoveExpGUI(ActionEvent event) throws IOException {
    	localStage = new Stage();
    	Scene removeExp = new Scene(FXMLLoader.load(getClass().getResource(expiredFXMLFile)));    	
    	localStage.setTitle("Remove Expired Stock");
    	localStage.setScene(removeExp);
    	localStage.setResizable(false);
    	localStage.show();
    }
    
    @FXML
    void showLosses(ActionEvent event) throws IOException {
    	localStage = new Stage();
    	Scene showLosses= new Scene(FXMLLoader.load(getClass().getResource(lossesFXMLFile)));    	
    	localStage.setTitle("Show Losses");
    	localStage.setScene(showLosses);
    	localStage.setResizable(false);
    	localStage.show();
    }
    
    @FXML
    void openEmployeeGUI(ActionEvent event) throws IOException {
    	localStage = new Stage();
    	Scene showEmployee= new Scene(FXMLLoader.load(getClass().getResource(employeeFXMLFile)));    	
    	localStage.setTitle("Employee");
    	localStage.setScene(showEmployee);
    	localStage.setResizable(false);
    	localStage.show();
    }
    
    @FXML
    void openItemGUI(ActionEvent event) throws IOException {
    	localStage = new Stage();
    	Scene showItem= new Scene(FXMLLoader.load(getClass().getResource(itemFXMLFile)));    	
    	localStage.setTitle("Item");
    	localStage.setScene(showItem);
    	localStage.setResizable(false);
    	localStage.show();
    }   

    // add your handler here ...
    
        
        
	// ===================================================================
    // YOU DON'T NEED TO TOUCH BELOW THIS LINE ...
	// the stage to be used by different features of the program
	private Stage localStage;
}
