package com.bytecoders.item.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bytecoders.item.DBItemRegistration;
import com.bytecoders.item.ItemRegistration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOItemRegistration {

	public static Connection conn;
	
	public static void addItemRegistration(int idBeer, String name, String type, String gradding, String ibu, double price) throws  ClassNotFoundException, SQLException {
		
		String sql = "INSERT INTO ITEMSREGISTRATION (BEERCODE, BEERNAME, TYPE, GRADDING, IBU, PRICE) VALUES ("+idBeer+",'"+name+"', '"+type+"', '"+gradding+"', '"+ibu+"', "+price+")";
	//	try {
			DBItemRegistration db = new DBItemRegistration();
			conn = db.openConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
		/*} catch (SQLException e) {
			System.out.println("Exception occur while inserting the data" + e);
			
		}	*/
	}

	public static void updateBeer(int idBeer, String name, String type, String gradding, String ibu, double price) throws ClassNotFoundException, SQLException {
		Statement stmt = null;
		String sql = "UPDATE ITEMSREGISTRATION SET BEERNAME = '" + name + "', TYPE = '" + type + "', GRADDING = '" + gradding + "', IBU = '" + ibu + "', PRICE = " + price + " WHERE BEERCODE = " + idBeer;
		
		//try {
			DBItemRegistration db = new DBItemRegistration();
			conn = db.openConnection();
    		stmt = conn.createStatement();
    		stmt.executeUpdate(sql);
		/*} catch (SQLException e) {
			System.out.println("Exception occur while inserting the data" + e);
			e.printStackTrace();
		}*/
	}

	public static void deleteBeer(int idBeer) throws ClassNotFoundException, SQLException {
		Statement stmt = null;
		String sql = "DELETE FROM ITEMSREGISTRATION WHERE BEERCODE = " + idBeer;
		//try {
			DBItemRegistration db = new DBItemRegistration();
			conn = db.openConnection();
     		stmt = conn.createStatement();
    		stmt.executeQuery(sql);
		/*} catch (SQLException e) {
			System.out.println("Exception occur while inserting the data" + e);
			e.printStackTrace();
		}*/
	}

	public static ObservableList<ItemRegistration> getAllRecords() throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM ITEMSREGISTRATION ORDER BY BEERCODE";
		try {
    		ResultSet rSet = DBItemRegistration.dbExecute(sql);
    		ObservableList<ItemRegistration> itemList = getBeerObjects(rSet);
    		return itemList;
		} catch (Exception e) {
			System.out.println("Exception occur in reading table" + e);
			e.printStackTrace();
			throw e;
		}	
	}

	private static ObservableList<ItemRegistration> getBeerObjects(ResultSet rSet) throws ClassNotFoundException, SQLException {
		try {
			ObservableList<ItemRegistration> itemList = FXCollections.observableArrayList();			
			while(rSet.next()) {
				ItemRegistration item = new ItemRegistration();
				item.setIdBeer(rSet.getInt(1));
				item.setName(rSet.getString(2));
				item.setType(rSet.getString(3));
				item.setGradding(rSet.getString(4));
				item.setIbu(rSet.getString(5));
				item.setPrice(rSet.getDouble(6));
				itemList.add(item);
		}
		return itemList;
		} catch (Exception e) {
			System.out.println("Error occurred while fetching the data from DB" + e);
			e.printStackTrace();
			throw e;
		}
	}
	
	public static ObservableList<ItemRegistration> searchBeer(int idBeer) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM ITEMSREGISTRATION WHERE BEERCODE = " + idBeer;		
		try {
    		ResultSet rSet = DBItemRegistration.dbExecute(sql);
    		ObservableList<ItemRegistration> itemList = getBeerObjects(rSet);
    		return itemList;
		} catch (Exception e) {
			System.out.println("Exception occur in reading table" + e);
			e.printStackTrace();
			throw e;
		}
	}
}