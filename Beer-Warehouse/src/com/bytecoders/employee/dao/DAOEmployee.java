package com.bytecoders.employee.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bytecoders.employee.DBEmployee;
import com.bytecoders.employee.Employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOEmployee {
	
	public static Connection conn;
	
	//adding method with query in database
	public static void addEmployee(int idEmployee, String firstName, String lastName, String email) throws SQLException, ClassNotFoundException {
		
		String sql = "INSERT INTO EMPLOYEEBW (idEmployee, firstName, lastName, email) VALUES ('"+idEmployee+"','"+firstName+"', '"+lastName+"', '"+email+"')";
		//try {
			DBEmployee db = new DBEmployee();
			conn = db.openConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
		/*} catch (SQLException e) {
			System.out.println("Exception occur while inserting the data" + e);
			e.printStackTrace();
		}*/
	}
	
	//updating method with query in database
	public static void updateEmployee(int id, String email) throws ClassNotFoundException, SQLException {
		Statement stmt = null;
		String sql = "UPDATE EMPLOYEEBW SET EMAIL = '" + email + "' WHERE IDEMPLOYEE = " + id;
		
		//try {
			DBEmployee db = new DBEmployee();
			conn = db.openConnection();
    		stmt = conn.createStatement();
    		stmt.executeUpdate(sql);
		/*} catch (SQLException e) {
			System.out.println("Exception occur while inserting the data" + e);
			e.printStackTrace();
		}*/
	}
	
	//deleting method with query in database
	public static void deleteEmployee(int id) throws ClassNotFoundException, SQLException {
		Statement stmt = null;
		String sql = "DELETE FROM EMPLOYEEBW WHERE IDEMPLOYEE = "+id;
		//try {
			DBEmployee db = new DBEmployee();
			conn = db.openConnection();
     		stmt = conn.createStatement();
    		stmt.executeQuery(sql);
		/*} catch (SQLException e) {
			System.out.println("Exception occur while inserting the data" + e);
			e.printStackTrace();
		}*/		
	}
	
	//getting all method with query in database
	public static ObservableList<Employee> getAllRecords() throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM EMPLOYEEBW ORDER BY IDEMPLOYEE";		
		try {
    		ResultSet rSet = DBEmployee.dbExecute(sql);
    		ObservableList<Employee> empList = getEmployeeObjects(rSet);
    		return empList;
		} catch (Exception e) {
			System.out.println("Exception occur in reading table" + e);
			e.printStackTrace();
			throw e;
		}	
	}
	
	//getting a specific employee method with query in database
	public static ObservableList<Employee> getEmployeeObjects(ResultSet rSet) throws ClassNotFoundException, SQLException {		
		try {
			ObservableList<Employee> empList = FXCollections.observableArrayList();			
			while(rSet.next()) {
				Employee emp = new Employee();
				emp.setIdEmployee(rSet.getInt("idEmployee"));
				emp.setFirstName(rSet.getString("firstName"));
				emp.setLastName(rSet.getString("lastName"));
				emp.setEmail(rSet.getString("email"));
				empList.add(emp);
			}
			return empList;
			} catch (Exception e) {
				System.out.println("Error occurred while fetching the data from DB" + e);
				e.printStackTrace();
				throw e;
			}
	}

	public static ObservableList<Employee> searchEmployee(int id) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM EMPLOYEEBW WHERE IDEMPLOYEE = " + id;		
		try {
			ResultSet rSet = DBEmployee.dbExecute(sql);
			ObservableList<Employee> list = getEmployeeObjects(rSet);
			return list;
		} catch (SQLException e) {
			System.out.println("Error occurred while searching the data from DB" + e);
			e.printStackTrace();
			throw e;
		}	
	}
}