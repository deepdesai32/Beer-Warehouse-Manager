package com.bytecoders.stock.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bytecoders.batch.Batch;
import com.bytecoders.batch.dao.BatchDao;
import com.bytecoders.batch.dao.BatchDaoImpl;
import com.bytecoders.stock.Stock;


public class StockDaoImpl implements StockDao {
	public Connection conn = null;

	// Receives a connection that will be used as long as the class lives
	public StockDaoImpl(Connection connection) {
		this.conn = connection;
	}

	@Override
	//Insert an informed batch into the stock
	public boolean insertIntoStock(Batch batch) {
		//gets an empty stock for the batch to be put on
		Stock emptyStock = this.getEmptyStock();
		PreparedStatement ps = null;
		ResultSet rs = null;
		//Puts the batch into the stock on the table
		String query = "UPDATE STOCK SET BATCH_BATCH_ID = ? WHERE AISLE = ? AND SHELF = ?";

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, batch.getID());
			ps.setInt(2, emptyStock.getAisle());
			ps.setInt(3, emptyStock.getShelf());
			ps.executeUpdate();

			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				// Closes the statement to keep memory clean
				ps.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}

	@Override
	//
	public boolean updateStock(Stock stock) {
		String query = "UPDATE STOCK SET BATCH_BATCH_ID = ? WHERE AISLE = ? AND SHELF = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, stock.getBatch().getID());
			ps.setInt(2, stock.getAisle());
			ps.setInt(3, stock.getShelf());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public Stock selectStock(Stock targetStock) {
		String query = "SELECT AISLE, SHELF, BATCH_BATCH_ID FROM STOCK WHERE AISLE = ? AND SHELF = ?";
		Stock selectedStock = null;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, targetStock.getAisle());
			ps.setInt(2, targetStock.getShelf());

			ResultSet rs = ps.executeQuery();
			if (rs.next() == true) {
				selectedStock = new Stock();
				selectedStock.setAisle(rs.getInt("AISLE"));
				selectedStock.setShelf(rs.getInt("SHELF"));
				int batchID = rs.getInt("BATCH_BATCH_ID");
				if (batchID != 0) {
					BatchDao batchDao = new BatchDaoImpl(conn);
					Batch targetBatch = new Batch();
					targetBatch.setID(batchID);
					selectedStock.setBatch(batchDao.selectBatch(targetBatch));
				}

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return selectedStock;
	}

	@Override
	public Stock getEmptyStock() {
		String query = "SELECT MIN(AISLE), SHELF FROM STOCK WHERE BATCH_BATCH_ID IS NULL GROUP BY SHELF ORDER BY MIN(AISLE), SHELF fetch first rows only";
		Stock emptyStock = null;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next() == true) {
				emptyStock = new Stock();
				emptyStock.setAisle(rs.getInt(1));
				emptyStock.setShelf(rs.getInt(2));
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

		return emptyStock;
	}

	@Override
	public boolean removeFromStock(Batch batch) {
		PreparedStatement ps = null;
		String query = "UPDATE STOCK SET BATCH_BATCH_ID = null WHERE BATCH_BATCH_ID = ?";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, batch.getID());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				// Closes the statement to keep memory clean
				ps.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}


	@Override
	public List<Stock> selectListOfStocks() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Stock> stockList = new ArrayList<Stock>();
		
		String query = "SELECT AISLE, SHELF, BATCH_BATCH_ID, ITEMSREGISTRATION_BEERCODE, RECEIVEDDATE, EXPIRYDATE, COST, ITEMQUANTITY, TOTALWEIGHT FROM STOCK S JOIN BATCH B ON S.BATCH_BATCH_ID = B.BATCH_ID WHERE BATCH_BATCH_ID IS NOT NULL ORDER BY BATCH_BATCH_ID";
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// Keeps reading the data until the last record on the dataset
			while (rs.next() == true) {
				Stock stock = new Stock();
				stock.setAisle((rs.getInt("AISLE")));
				stock.setShelf((rs.getInt("SHELF")));
				
				stock.setBatch(new Batch());
				stock.getBatch().setID(rs.getInt("BATCH_BATCH_ID"));
				stock.getBatch().setReceivedDate(rs.getDate("RECEIVEDDATE").toLocalDate());
				stock.getBatch().setExpiryDate(rs.getDate("EXPIRYDATE").toLocalDate());
				stock.getBatch().setCost(rs.getDouble("COST"));
				stock.getBatch().setItemQuantity(rs.getInt("ITEMQUANTITY"));
				stock.getBatch().setTotalWeight(rs.getDouble("TOTALWEIGHT"));										
				
				stockList.add(stock);
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
		return stockList;
	}

	
}
