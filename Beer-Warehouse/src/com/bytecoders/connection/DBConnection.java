package com.bytecoders.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	final private String username = "n01326427";
	final private String password = "oracle";
	final private String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
	final private String driver = "oracle.jdbc.driver.OracleDriver";

	static private Connection connection = null;

	//Opens the connection as soon as the object is created
	public DBConnection() {
		this.openConnection();
	}

	//Connects to the Database, based on the information of this class
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
		//Returns the current connection
		return connection;
	}

	//Closes the connection to the database of this object
	public void closeConnection() {
		//If the connection is open, it then tries to close it
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

	//Returns the current connection of the object
	public Connection getConnection() {
		return connection;
	}
	
	
}
