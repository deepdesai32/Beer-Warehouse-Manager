package com.bytecoders.client;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;




//needs to implement DAO Class and uses Clients class
public class ClientDB implements DAO<Clients>{
	
	//connection variable
	private Connection conn;
	
	//constructor passing connection
	public ClientDB(Connection connection) {
	this.conn = connection;
	}
	
	//connection constructor
	public ClientDB() {
		try {
			
			//variables to implement inside the driver manager
			String username = "n01326427";
			String password = "oracle";
			String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
			
			// create a connection using variables above
			this.conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	//method to add a client, returns true if executed properly and false if not executed properly
	public boolean add(Clients c) {
		// sql statement which adds name, address, and phone number to clients table
		//client id is autoimplemented in client table within database
		String sql = "INSERT INTO Clients (name, address, phone_number)" + " VALUES (?, ?, ?)";

		try {
			PreparedStatement st = this.conn.prepareStatement(sql);
			//getting client variables to set into Values, gets variables after they are set in the controller class
			st.setString(1, c.getName());
			st.setString(2, c.getAddress());
			st.setString(3, c.getPhoneNum());
			
			//executes query
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			return false;

		}
	}
	
	//method to update client, returns true if executed properly and false if not executed properly
	public boolean update(Clients d) {
		// sql statement which updates name, address, and phone number to clients table based on client_id

		
		String sql2 = "UPDATE clients SET " + "name = ?, " + " address = ?," + " phone_number = ?" + " WHERE client_id = ?";

		try {
			PreparedStatement st2 = this.conn.prepareStatement(sql2);
			//getting client variables to set into Values, gets variables after they are set in the controller class
			st2.setString(1, d.getName());
			st2.setString(2, d.getAddress());
			st2.setString(3, d.getPhoneNum());
			
			//getting clientID for where clause
			st2.setString(4, d.getClientID());
			
			//executes query
			st2.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			return false;

		}
	}
	
	//deletes client. returns true if executed properly and false if not executed properly
	public boolean delete(Clients e) {
		
		//deletes a client based on their clientID
		String sql3 = "DELETE FROM clients WHERE client_id = ?";
		

		try {
			PreparedStatement st3 = this.conn.prepareStatement(sql3);
			//gets client ID from controller class to set in where class
			st3.setString(1, e.getClientID());
			//executes query
			st3.executeUpdate();
			return true;
		} catch (SQLException e1) {
			System.err.println(e1);
			return false;

		}
	}
	
	

}
