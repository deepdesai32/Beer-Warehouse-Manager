package com.bytecoders.management.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagementDAOImpl implements ManagementDAO {

	private Connection conn = null;
	private StringBuilder data;	// returned data 

	public ManagementDAOImpl(Connection conn) {
		this.conn = conn;
	}

	// just get all relevant data between start and end dates.
	@Override
	public String getInfo(Date start, Date end) {

		String query = "SELECT " + 
					"o.order_id as oid, " +
					"c.name as cname, " + 
					"c.address as caddr, " +
					//"e.name as ename, " + 
					"e.address as eaddr, " +
					//"e.column1 as c1, " + 
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
					"INNER JOIN employee e ON e.employee_id = o.employee_employee_id " + 
					"INNER JOIN orderbatches ob ON o.order_id = ob.orders_orderid " + 
					"INNER JOIN batch b ON b.batch_id = ob.batch_batch_id " + 
					"INNER JOIN itemsregistration i ON b.itemsregistration_beercode = i.beercode " + 
				"WHERE O.ORDERDATE BETWEEN ? AND ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setDate(1, start);
			ps.setDate(2, end);

			data = new StringBuilder();
			ResultSet rs = ps.executeQuery();

			// put the records inside the data object
			while (rs.next()) {
				data.append("-----------------------").append("\n");
				data.append("Order ID: " + rs.getInt("oid")).append("\n");
				data.append("Customer: " + rs.getString("cname")).append("\n");
				data.append("Address: " + rs.getString("caddr")).append("\n");
				//data.append("Employee: " + rs.getString("ename")).append("\n");
				data.append("Seller Address: " + rs.getString("eaddr")).append(" ");
				data.append("Order Date: " + rs.getDate("odate")).append("\n");
				data.append("Receiving Date: " + rs.getDate("recdate")).append("\n");
				data.append("Expire Date: " + rs.getDate("expdate")).append("\n");
				data.append("Total Cost: " + rs.getDouble("cost")).append("\n");
				data.append("Item Quantity: " + rs.getInt("iqty")).append("\n");
				data.append("Total Weight: " + rs.getDouble("totalweight")).append("\n");
				data.append("Total Price: " + rs.getDouble("totalprice")).append("\n");
				data.append("Beer Code: " + rs.getInt("beercode")).append("\n");
				data.append("Beer Name: " + rs.getString("beername")).append("\n");
				data.append("Beer ABV: " + rs.getDouble("beergradding")).append("\n");
				data.append("Beer Price: " + rs.getDouble("beerprice")).append("\n");
				data.append("Beer IBU: " + rs.getDouble("beeribu")).append("\n");
			}

			// and return it so it can be put to the GUI component.
			return data.toString();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

}
