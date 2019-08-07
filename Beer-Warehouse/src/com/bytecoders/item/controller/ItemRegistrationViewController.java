package com.bytecoders.item.controller;

import java.sql.Connection;
import java.sql.SQLException;

import com.bytecoders.item.DBItemRegistration;
import com.bytecoders.item.ItemRegistration;
import com.bytecoders.item.dao.DAOItemRegistration;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ItemRegistrationViewController {
	
	DBItemRegistration dbcon = new DBItemRegistration();
	Connection con = dbcon.openConnection();
	
    @FXML
    private Label lblType;

    @FXML
    private Label lblGradding;

    @FXML
    private Label lblBeerId;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtGradding;

    @FXML
    private TextField txtIbu;

    @FXML
    private TextField txtPrice;

    @FXML
    private Button btnAddBeer;

    @FXML
    private Button btnClear;

    @FXML
    private TextField txtBeerId;

    @FXML
    private TextField txtBeerName;

    @FXML
    private TextField txtBeerType;

    @FXML
    private TextField txtBeerGradding;

    @FXML
    private TextField txtBeerIbu;

    @FXML
    private TextField txtBeerPrice;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearchAllBeer;

    @FXML
    private Button btnClearSearch;
    
    @FXML
    private Button btnExit;

    @FXML
    private Label lblName;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblBeerName;

    @FXML
    private TableView<ItemRegistration> tblItemRegistartionView;

    @FXML
    private TableColumn<ItemRegistration, Integer> clmId;

    @FXML
    private TableColumn<ItemRegistration, String> clmName;

    @FXML
    private TableColumn<ItemRegistration, String> clmType;

    @FXML
    private TableColumn<ItemRegistration, String> clmGradding;

    @FXML
    private TableColumn<ItemRegistration, String> clmIbu;

    @FXML
    private TableColumn<ItemRegistration, Double> clmPrice;

    @FXML
    private Label lblId;

    @FXML
    private Label lblResultConsole;

    @FXML
    private TextArea txaResultConsole;

    @FXML
    private Label lblIbu;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblBeerType;

    @FXML
    private Label lblBeerGradding;

    @FXML
    private Label lblBeerIbu;

    @FXML
    private Label lblBeerPrice;
    
    @FXML
    private void initialize() throws Exception {
    	clmId.setCellValueFactory(cellData -> cellData.getValue().getIdBeerProperty().asObject());
    	clmName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    	clmType.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
    	clmGradding.setCellValueFactory(cellData -> cellData.getValue().getGraddingProperty());
    	clmIbu.setCellValueFactory(cellData -> cellData.getValue().getIbuProperty());;
    	clmPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());     	
    	ObservableList<ItemRegistration> itemList = DAOItemRegistration.getAllRecords();
    	populateTable(itemList);
    }
    
    private void populateTable(ObservableList<ItemRegistration> itemList) {
    	tblItemRegistartionView.setItems(itemList);
	}

	@FXML
    void addBeer(ActionEvent event) throws ClassNotFoundException, SQLException{
		if(validate() == false) {
        	txaResultConsole.setText("No success!\nBeer has not been added!");
    	} else {
		try {
			DAOItemRegistration.addItemRegistration(Integer.parseInt(txtId.getText()), txtName.getText(), txtType.getText(), txtGradding.getText(), txtIbu.getText(), (Double.parseDouble(txtPrice.getText())));
        	txaResultConsole.setText("Success!\nValues has been added!");
        	ObservableList<ItemRegistration> itemList = DAOItemRegistration.getAllRecords();
        	populateTable(itemList);
		} catch (SQLException e) {
			System.out.println("Exception occur in insertion" + e);
        	txaResultConsole.setText("No Success!\nID duplicated!");
		}
    	DAOItemRegistration.getAllRecords();
    	};
    }
	
    @FXML
    void updateBeer(ActionEvent event) throws ClassNotFoundException, SQLException {
    	try {
        	double price;
    		if(txtBeerPrice.getText().isEmpty()) {
    			price =0;
    		}else {
				price = Double.parseDouble(txtBeerPrice.getText());
			}
	    	DAOItemRegistration.updateBeer(Integer.parseInt(txtBeerId.getText()),txtBeerName.getText(), txtBeerType.getText(), txtBeerGradding.getText(), txtBeerIbu.getText(), price);
        	txaResultConsole.setText("Success!\nThe record has been updated!");
	    	ObservableList<ItemRegistration> itemList = DAOItemRegistration.searchBeer(Integer.parseInt(txtBeerId.getText()));
	    	populateTable(itemList);
		} catch (Exception e) {
			System.out.println("Exception occur in insertion" + e);
        	txaResultConsole.setText("No Success!\nID used!");
		}
    	DAOItemRegistration.getAllRecords();
    }

 
    @FXML
    void deleteBeer(ActionEvent event) throws ClassNotFoundException, SQLException {
    	try {
	    	DAOItemRegistration.deleteBeer(Integer.parseInt(txtBeerId.getText()));
        	txaResultConsole.setText("Success!\nThe record has been deleted!");
	    	ObservableList<ItemRegistration> itemList = DAOItemRegistration.getAllRecords();
	    	populateTable(itemList);
	    	
		} catch (Exception e) {
			System.out.println("Exception occur in insertion" + e);
        	txaResultConsole.setText("No Success!\nID used!");
		}
    	DAOItemRegistration.getAllRecords();
    	btnClearSearch.clipProperty();
    }

    @FXML
    void searchAllBeer(ActionEvent event) throws ClassNotFoundException, SQLException {
    	ObservableList<ItemRegistration> itemList = DAOItemRegistration.getAllRecords();
    	populateTable(itemList);	    	

    }

    @FXML
    void searchBeer(ActionEvent event) throws ClassNotFoundException, SQLException {
    	ObservableList<ItemRegistration> itemList = DAOItemRegistration.searchBeer(Integer.parseInt(txtBeerId.getText()));
    	if(itemList.size() > 0) {	    	
    		populateTable(itemList);
    		txaResultConsole.setText("Record has been found!");
    		ItemRegistration item = itemList.get(0);
    		txtBeerGradding.setText(item.getGradding());
    		txtBeerName.setText(item.getName());
    		txtBeerType.setText(item.getType());
    		txtBeerIbu.setText(item.getIbu());
    		Double priceParse = item.getPrice(); 
    		txtBeerPrice.setText(priceParse.toString()); 		
    	} else {
    		txaResultConsole.setText("Record has not been found!");
    	}
    }

    @FXML
    void clear(ActionEvent event) throws ClassNotFoundException, SQLException {
    	txtId.clear();
    	txtName.clear();
    	txtType.clear();
    	txtGradding.clear();
    	txtIbu.clear();
    	txtPrice.clear();
    	txaResultConsole.setText("Fields cleared!");
    }
    
    @FXML
    void clearSearch(ActionEvent event) throws ClassNotFoundException, SQLException {
       	txtBeerId.clear();
    	txtBeerName.clear();
    	txtBeerType.clear();
    	txtBeerGradding.clear();
    	txtBeerIbu.clear();
    	txtBeerPrice.clear();  	
    	txaResultConsole.setText("Fields cleared!");
    }
    
    public boolean validate() {
        StringBuilder errors = new StringBuilder();
        // Confirm mandatory fields are filled out
        if (txtId.getText().trim().isEmpty()) {
            errors.append("- Please enter a ID.\n");
        }
        if (txtName.getText().trim().isEmpty()) {
            errors.append("- Please enter a beer name.\n");
        }
        if (txtType.getText().trim().isEmpty()) {
            errors.append("- Please enter a beer type.\n");
        }
        if (txtGradding.getText().trim().isEmpty()) {
            errors.append("- Please enter a beer gradding.\n");
        }
        if (txtIbu.getText().trim().isEmpty()) {
            errors.append("- Please enter a beer IBU.\n");
        }
        if (txtPrice.getText().trim().isEmpty()) {
            errors.append("- Please enter a beer price.\n");
        }
        // If any missing information is found, show the error messages and return false
        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return false;
        }
        // No errors
        return true;
    }

}
