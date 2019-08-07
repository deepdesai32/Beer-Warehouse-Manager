package com.bytecoders.employee.controller;

import java.sql.Connection;
import java.sql.SQLException;

import com.bytecoders.employee.DBEmployee;
import com.bytecoders.employee.Employee;
import com.bytecoders.employee.dao.DAOEmployee;

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

public class EmployeeViewController {
	
	//connection instantiated
	DBEmployee dbcon = new DBEmployee();
	Connection con = dbcon.openConnection();

		//FXML features built in the Scene Builder
	    @FXML
	    private Label lblLastName;

	    @FXML
	    private Label lblEmail;

	    @FXML
	    private Label lblEmployeeId;

	    @FXML
	    private TextField txtLastName;

	    @FXML
	    private TextField txtEmail;

	    @FXML
	    private TextField txtEmployeeId;

	    @FXML
	    private Label lblFirstName;

	    @FXML
	    private TextField txtFirstName;

	    @FXML
	    private Button btnAddEmployee;

	    @FXML
	    private Button btnDelete;

	    @FXML
	    private Button btnUpdate;

	    @FXML
	    private Label lblTitle;

	    /*@FXML
	    private Button btnExit;*/

	    @FXML
	    private Label lblNewEmail;

	    @FXML
	    private TextField txtNewEmail;

	    @FXML
	    private Button btnSearch;

	    @FXML
	    private Button btnClear;

	    @FXML
	    private Button btnSearchAllEmployee;

	    @FXML
	    private TableView<Employee> tblEmployeeView;

	    @FXML
	    private TableColumn<Employee, Integer> clmId;

	    @FXML
	    private TableColumn<Employee, String> clmName;

	    @FXML
	    private TableColumn<Employee, String> clmLastName;

	    @FXML
	    private TableColumn<Employee, String> clmEmail;

	    @FXML
	    private Label lblId;

	    @FXML
	    private TextField txtId;

	    @FXML
	    private Label lblResultConsole;

	    @FXML
	    private TextArea txaResultConsole;
		
	    //initializing method
		@FXML
		private void initialize() throws Exception{
			clmId.setCellValueFactory(cellData -> cellData.getValue().getEmployeeId().asObject());
			clmName.setCellValueFactory(cellData -> cellData.getValue().getEmployeeFirstName());
			clmLastName.setCellValueFactory(cellData -> cellData.getValue().getEmployeeLastName());
			clmEmail.setCellValueFactory(cellData -> cellData.getValue().getEmployeeEmail());
	    	ObservableList<Employee> empList = DAOEmployee.getAllRecords();
	    	populateTable(empList);
		}
		
		//populating method to tableview
		private void populateTable(ObservableList<Employee> empList) {
	    	tblEmployeeView.setItems(empList);
		}
		
		//adding employee method
	    @FXML
	    void addEmployee(ActionEvent event) throws ClassNotFoundException, SQLException{
	    	if(validate() == false) {
	        	txaResultConsole.setText("No success!\nEmployee has not been added!");
	    	} else {
		    	try {
		    		DAOEmployee.addEmployee(Integer.parseInt(txtId.getText()), txtFirstName.getText(), txtLastName.getText(), txtEmail.getText());
		        	txaResultConsole.setText("Success!\nValues has been added!");
			    	ObservableList<Employee> empList = DAOEmployee.getAllRecords();
			    	populateTable(empList);
			    	txtNewEmail.clear();
				} catch (SQLException e) {
					System.out.println("Exception occur in insertion" + e);
		        	txaResultConsole.setText("No Success!\nID duplicated!");
				}
		    	DAOEmployee.getAllRecords();
	    	};
	    }

	    //updating employee method
	    @FXML
	    void updateEmployee(ActionEvent event) throws ClassNotFoundException, SQLException {    	
	    	try {
		    	DAOEmployee.updateEmployee(Integer.parseInt(txtEmployeeId.getText()),txtNewEmail.getText());
	        	txaResultConsole.setText("Success!\nThe record has been updated!");
		    	ObservableList<Employee> empList = DAOEmployee.searchEmployee(Integer.parseInt(txtEmployeeId.getText()));
		    	populateTable(empList);
		    	txtNewEmail.clear();
			} catch (Exception e) {
				System.out.println("Exception occur in insertion" + e);
	        	txaResultConsole.setText("No Success!\nID used!");
			}
	    }
	    
		//deleting employee method
	    @FXML
	    void deleteEmployee(ActionEvent event) throws ClassNotFoundException, SQLException {
	    	try {
		    	DAOEmployee.deleteEmployee(Integer.parseInt(txtEmployeeId.getText()));
	        	txaResultConsole.setText("Success!\nThe record has been deleted!");
		    	ObservableList<Employee> empList = DAOEmployee.getAllRecords();
		    	populateTable(empList);
		    	txtNewEmail.clear();
		    	txtEmployeeId.clear();
			} catch (Exception e) {
				System.out.println("Exception occur in insertion" + e);
	        	txaResultConsole.setText("No Success!\nID used!");
			}
	    	DAOEmployee.getAllRecords();
	    }
	    
		//searching a specific employee method
	    @FXML
	    private void searchEmployee(ActionEvent event) throws ClassNotFoundException, SQLException {
	    	ObservableList<Employee> empList = DAOEmployee.searchEmployee(Integer.parseInt(txtEmployeeId.getText()));
	    	if(empList.size() > 0) {	    	
	    		populateTable(empList);
		    	txtNewEmail.clear();
	    		txaResultConsole.setText("Record has been found!");
	    	} else {
	    		txaResultConsole.setText("Record has not been found!");
	    	}
	    }
	    
		//searching all employee method
	    @FXML
	    private void searchAllEmployees(ActionEvent event) throws ClassNotFoundException, SQLException {
	    	ObservableList<Employee> empList = DAOEmployee.getAllRecords();
	    	populateTable(empList);
	    	txtNewEmail.clear();
	    }
	    
	    //clearing fields method
	    @FXML
	    private void clear(ActionEvent event) throws ClassNotFoundException, SQLException {
	    	txtId.clear();
	    	txtFirstName.clear();
	    	txtLastName.clear();
	    	txtEmail.clear();
	    	txtEmployeeId.clear();
	    	txtNewEmail.clear();
	    	txaResultConsole.setText("Fields cleared!");
	    }
	    
	    //validating fields method
	    public boolean validate() {
	        StringBuilder errors = new StringBuilder();
	        // Confirm mandatory fields are filled out
	        if (txtId.getText().trim().isEmpty()) {
	            errors.append("- Please enter a ID.\n");
	        }
	        if (txtFirstName.getText().trim().isEmpty()) {
	            errors.append("- Please enter a first name.\n");
	        }
	        if (txtLastName.getText().trim().isEmpty()) {
	            errors.append("- Please enter a last name.\n");
	        }
	        if (txtEmail.getText().trim().isEmpty()) {
	            errors.append("- Please enter a email.\n");
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
