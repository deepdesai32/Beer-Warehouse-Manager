package com.bytecoders.losses;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bytecoders.batch.Batch;

import javafx.collections.ObservableList;


public class ExpiredDB{
	
	//connection variable
	private Connection conn;
	
	//constructor to pass connectionn
	public ExpiredDB(Connection connection) {
		this.conn = connection;
		}
	
	//constructor to initialize connection
	public ExpiredDB() {
		try {
			//variables to pass within getConnection method
			String username = "n01326427";
			String password = "oracle";
			String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
			
			// create a connection
			this.conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
		//list to select all expired items based on date
		public List<Batch> selectExpired(String Date) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			//Selects every value based on if expiry date is before current date, ordered by batch id
			String sql = "SELECT BATCH_ID, ITEMSREGISTRATION_BEERCODE, RECEIVEDDATE, EXPIRYDATE, COST, ITEMQUANTITY, TOTALWEIGHT FROM BATCH B LEFT JOIN orderbatches OB ON b.batch_id = OB.BATCH_BATCH_ID LEFT JOIN stock s ON b.batch_id = S.BATCH_BATCH_ID WHERE B.EXPIRYDATE < ? AND ORDERS_ORDERID IS NULL AND AISLE IS NOT NULL ORDER BY b.batch_id";

			//array list to store values
			List<Batch> ShowExpired = new ArrayList<Batch>();

			try {
				//passes sql query using connection
				ps = conn.prepareStatement(sql);
				//sets date through method being passed, will use in controller
				ps.setString(1, Date);
				rs = ps.executeQuery();
				// Keeps reading the data until the last record on the dataset
				while (rs.next() == true) {
					
					//batch object creation
					Batch batch = new Batch();
					
					//gets all values needed to run query statement
					batch.setID(rs.getInt("BATCH_ID"));
					batch.setReceivedDate(rs.getDate("RECEIVEDDATE").toLocalDate());
					batch.setExpiryDate(rs.getDate("EXPIRYDATE").toLocalDate());
					batch.setCost(rs.getDouble("COST"));
					batch.setItemQuantity(rs.getInt("ITEMQUANTITY"));
					batch.setTotalWeight(rs.getDouble("TOTALWEIGHT"));
					ShowExpired.add(batch);
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					// Closes the statement to keep memory clean
					ps.close();
					rs.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
			//returns array list values
			return ShowExpired;
		}
		
		
		//returns double value 
		public double selectLosses(String Dates) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			//selects the total cost of all expired times using SUM function
			String sql = "SELECT SUM(COST) FROM BATCH B LEFT JOIN orderbatches OB ON b.batch_id = OB.BATCH_BATCH_ID LEFT JOIN stock s ON b.batch_id = S.BATCH_BATCH_ID WHERE B.EXPIRYDATE < ? AND ORDERS_ORDERID IS NULL AND AISLE IS NOT NULL ORDER BY b.batch_id";

			//initiallizes sum, to then be added by query
			double sum = 0;
			
			//array list to store values
			List<Batch> ShowLosses = new ArrayList<Batch>();

			try {
				//passes sql query using connection
				ps = conn.prepareStatement(sql);
				//sets date based user input in controller as it is passed through this method
				ps.setString(1, Dates);
				
				//query execution
				rs = ps.executeQuery();
				// Keeps reading the data until the last record on the dataset
				if(rs.next() == true) {
					//adds to the sum that was initialized
				sum = rs.getDouble(1);
				
				}
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					// Closes the statement to keep memory clean
					ps.close();
					rs.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
			//rerns the total cost of all expired items
			return sum;
		}

}


