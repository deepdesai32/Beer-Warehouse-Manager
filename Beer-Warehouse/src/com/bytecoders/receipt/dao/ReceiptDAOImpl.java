package com.bytecoders.receipt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bytecoders.receipt.Receipt;

public class ReceiptDAOImpl implements ReceiptDAO {

	private Connection conn = null;
	private Receipt rcpt;	// the receipt we will return
	private List<String> idList;	// list of order ids
	
	public ReceiptDAOImpl(Connection connection) {
		this.conn = connection;
	}


	// these ids will populate the ComboBox in the GUI
	@Override
	public List<String> getAllIds() {
		String query = "SELECT ORDER_ID FROM ORDERS";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			idList = new ArrayList<>();
			
			// while we have data, add its id to the list.
			while (rs.next()) {
				idList.add(Integer.toString(rs.getInt("order_id")));
			}
			
			return idList;
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	// this is pretty straightforward. 
	// it just searches for relevant information about the received orderID
	// populates the Receipt object with it and return that object.
	@Override
	public Receipt generateReceipt(int orderID) {
		// select order info relevant to receipt
		String query = "SELECT " + 
				"o.order_id as oid, " +
				"c.name as cname, " + 
				"c.address as caddr, " +
				"o.orderdate as odate, " + 
				"b.receiveddate as recdate, " + 
				"b.expirydate as expdate, " + 
				"b.cost as cost, " + 
				"b.itemquantity as iqty, " +
				"b.totalweight as totalweight, " + 
				"o.totalprice as totalprice, " + 
				"i.beercode as beercode, " + 
				"i.beername as beername, " + 
				"i.gradding as beergradding, " + 
				"i.price as beerprice, " +
				"i.ibu as beeribu " + 
			"FROM orders o " + 
				"INNER JOIN clients c ON c.client_id = o.clients_client_id " + 
				"INNER JOIN employeebw e ON e.idEmployee = o.employeebw_idEmployee " + 
				"INNER JOIN orderbatches ob ON o.order_id = ob.orders_orderid " + 
				"INNER JOIN batch b ON b.batch_id = ob.batch_batch_id " + 
				"INNER JOIN itemsregistration i ON b.itemsregistration_beercode = i.beercode "  + 
			"WHERE O.ORDER_ID = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, orderID);
			ResultSet rs = ps.executeQuery();
			
			// new receipt object
			rcpt = new Receipt();
			
			// populate object
			while (rs.next()) {
				rcpt.setOrderID(rs.getInt("oid"));
				rcpt.setCliName(rs.getString("cname"));
				rcpt.setCliAddress(rs.getString("caddr"));
				rcpt.setOrderDate(rs.getDate("odate"));
				rcpt.setDeliverDate(rs.getDate("recdate"));
				rcpt.setExpDate(rs.getDate("expdate"));
				rcpt.setCost(rs.getDouble("cost"));
				rcpt.setItemQty(rs.getInt("iqty"));
				rcpt.setTotalWeight(rs.getDouble("totalweight"));
				rcpt.setItemPrice(rs.getDouble("totalprice"));
				rcpt.setBeercode(rs.getInt("beercode"));
				rcpt.setBeerName(rs.getString("beername"));
				rcpt.setBeerABV(rs.getDouble("beergradding"));
				rcpt.setBeerPrice(rs.getDouble("beerprice"));
				rcpt.setBeerIBU(rs.getDouble("beeribu"));
			}

			// return populated object to caller
			return rcpt;
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
}
