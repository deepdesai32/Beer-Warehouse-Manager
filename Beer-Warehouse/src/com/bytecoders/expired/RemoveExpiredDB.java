package com.bytecoders.expired;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bytecoders.client.Clients;
import com.bytecoders.client.DAO;


public class RemoveExpiredDB{
	//connection variable
private Connection conn;

//constructor to pass connection
public RemoveExpiredDB(Connection connection) {
this.conn = connection;
}

//constructor to initialize connection and driver
public RemoveExpiredDB() {
	try {
		
		//variables to pass within getConnection method.
		String username = "n01326427";
		String password = "oracle";
		String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
		
		// create a connection
		this.conn = DriverManager.getConnection(url, username, password);
	} catch (SQLException e) {
		System.err.println(e);
	}
}
	//remove expired items, if return true if connection was successful, return false if unsuccessful
	public boolean remove(RemoveExpired a) {
		
		//deletes batch based on batch_id
		String sql = "DELETE FROM BATCH WHERE BATCH_ID = ?";

		try {
			PreparedStatement st = this.conn.prepareStatement(sql);
			
			//gets batch id from user in controller
			st.setInt(1, a.getBatchID());
			//executes query
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			return false;

		}
	}
	
}
