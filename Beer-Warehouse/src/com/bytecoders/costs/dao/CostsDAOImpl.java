package com.bytecoders.costs.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CostsDAOImpl implements CostsDAO {

	private double cost;
	private Connection conn = null;
	private ArrayList<String> idList;
	
	public CostsDAOImpl(Connection conn) {
		this.conn = conn;
	}

	// gets all batches ID so they can populate ComboBox in the GUI
	@Override
	public ArrayList<String> getAllIds() {
		String query = "SELECT BATCH_ID FROM BATCH";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			idList = new ArrayList<>();
			
			while (rs.next()) {
				idList.add(Integer.toString(rs.getInt("batch_id")));
			}
			
			return idList;
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	// just retrieve the cost for the given batchID
	@Override
	public double getBatchCost(int batchID) {
		String query = "SELECT COST FROM BATCH WHERE BATCH_ID = ?";

		// prepare the SQL Statement
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, batchID);

			// get results
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				cost = rs.getDouble("COST");
			}

			// validate, checking if last read had actual data.
			if (rs.wasNull())
				return -1;
			else
				// only return if there is a cost value.
				return cost;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

}
