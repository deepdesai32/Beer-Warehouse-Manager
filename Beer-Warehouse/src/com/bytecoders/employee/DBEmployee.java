package com.bytecoders.employee;

import java.sql.Statement;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBEmployee {
	
	//variables connections
	final private String username = "n01326427";
	final private String password = "oracle";
	final private String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
	final private String driver = "oracle.jdbc.driver.OracleDriver";

	static private Connection connection = null;
	
	//open connection constructor
	public DBEmployee() {
		this.openConnection();
	}
	
	//open connection method
	public Connection openConnection() {

		if (connection == null) {
			try {
				Class.forName(driver);
				connection = DriverManager.getConnection(url, username, password);
				System.out.println("Connection successful");
			} catch (Exception e) {
				System.out.println("Fail to connect");
				System.out.println(e.toString() + e.getMessage());
			}
		}

		return connection;
	}
	
	//close connection method
	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("Connection closing successful");
			} catch (SQLException e) {
				System.out.println("Fail to close connection");
				System.out.println(e.toString() + e.getMessage());
			}
		}
	}
	
	//getConnection method
	public Connection getConnection() {
		return connection;
	}
    
	//execute query method
    public static void dbExecuteQuery(String sqlStmt) throws SQLException, ClassNotFoundException {
    	Statement stmt = null;
    	
    	try {
    		DBEmployee db = new DBEmployee();
    		connection = db.openConnection();
    		stmt = connection.createStatement();
			stmt.executeUpdate(sqlStmt);
		} catch (SQLException e) {
			System.out.println("Problem occured at dbExecuteQuery operation" + e);
			e.printStackTrace();
			throw e;
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}  	
    }
    
    //execute query for result set purpose
    public static ResultSet dbExecute(String sqlQuery) throws ClassNotFoundException, SQLException {
    	Statement stmt = null;
    	ResultSet rs = null;
    	CachedRowSetImpl crs = null;
    	
    	try {
    		DBEmployee db = new DBEmployee();
    		connection = db.openConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			crs = new CachedRowSetImpl();
			crs.populate(rs);
		} catch (SQLException e) {
			System.out.println("Error occurred in dbExecute operation " + e);
			throw e;
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
		}
    	return crs;
    }
}
