package com.bytecoders.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bytecoders.batch.Batch;
import com.bytecoders.batch.dao.BatchDao;
import com.bytecoders.batch.dao.BatchDaoImpl;
import com.bytecoders.order.Order;
import com.bytecoders.stock.dao.StockDao;
import com.bytecoders.stock.dao.StockDaoImpl;

public class OrderBatchesDaoImpl implements OrderBatchesDao {
	public Connection conn = null;

	public OrderBatchesDaoImpl(Connection connection) {
		this.conn = connection;
	}

	@Override
	// Insert the given order on the table
	public boolean insertOrder(Order order) {
		String query = "INSERT INTO ORDERBATCHES (ORDERS_ORDERID, BATCH_BATCH_ID) VALUES (?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			for (Batch batch : order.getBatchList()) {
				ps.setInt(1, order.getID());
				ps.setInt(2, batch.getID());
				ps.executeUpdate();

				// After assigning the batch to the order, it will be removed from stock
				StockDao stockDao = new StockDaoImpl(conn);
				stockDao.removeFromStock(batch);
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	// Select All the batches from a received Order
	public List<Batch> selectAllBacthesOfAnOrder(Order order) {
		List<Batch> batchesList = new ArrayList<Batch>();
		String query = "SELECT BATCH_BATCH_ID FROM ORDERBATCHES WHERE ORDERS_ORDERID = ? ORDER BY BATCH_BATCH_ID";
		Batch selectedBatch = null;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, order.getID());
			ResultSet rs = ps.executeQuery();
			BatchDao batchDao = new BatchDaoImpl(conn);
			while (rs.next() == true) {
				// Select all the info of the batch calling the Function of BatchDao, informing
				// the BatchID
				selectedBatch = new Batch();
				selectedBatch.setID(rs.getInt("BATCH_BATCH_ID"));
				batchesList.add(batchDao.selectBatch(selectedBatch));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return batchesList;
	}

}
