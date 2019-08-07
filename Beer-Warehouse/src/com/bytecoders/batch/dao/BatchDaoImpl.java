package com.bytecoders.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bytecoders.batch.Batch;
import com.bytecoders.item.ItemRegistration;
import com.bytecoders.stock.dao.StockDao;
import com.bytecoders.stock.dao.StockDaoImpl;



// Batch class for accessing all the tables necessary to handle a Batch
public class BatchDaoImpl implements BatchDao {
	public Connection conn = null;

	// Receives a connection that will be used as long as the class lives
	public BatchDaoImpl(Connection connection) {
		this.conn = connection;
	}

	@Override
	// Insert method for inserting a new Batch on the BATCH table
	public boolean insertBatch(Batch batch) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// The insert clause doesn't use the Primary Key, which is created by the
		// Database itself
		String query = "INSERT INTO BATCH (RECEIVEDDATE, EXPIRYDATE, COST, ITEMQUANTITY, TOTALWEIGHT, ITEMSREGISTRATION_BEERCODE) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			String[] key = { "batch_id" };

			ps = conn.prepareStatement(query, key);
			ps.setDate(1, java.sql.Date.valueOf(batch.getReceivedDate()));
			ps.setDate(2, java.sql.Date.valueOf(batch.getExpiryDate()));
			ps.setDouble(3, batch.getCost());
			ps.setInt(4, batch.getItemQuantity());
			ps.setDouble(5, batch.getTotalWeight());
			ps.setInt(6, batch.getItem().getIdBeer());
			ps.executeUpdate();

			// Obtains the generated Key
			rs = ps.getGeneratedKeys();
			// Sets the key to the Batch ID
			if (rs.next()) {
				int batchID = rs.getInt(1);
				batch.setID(batchID);
			}

			// Moves the batch into the stock
			StockDao stockDao = new StockDaoImpl(conn);
			stockDao.insertIntoStock(batch);
			// If everything runs ok, return true
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
	// Method for UPDATING a batch, it updates all the values of that batch, except
	// the Primary Key
	public boolean updateBatch(Batch batch) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Updates only one batch using the Primary Key
		String query = "UPDATE BATCH SET RECEIVEDDATE = ?, EXPIRYDATE = ?, COST = ?, ITEMQUANTITY = ?, TOTALWEIGHT = ?, ITEMSREGISTRATION_BEERCODE = ? WHERE BATCH_ID = ?";
		try {
			ps = conn.prepareStatement(query);
			ps.setDate(1, java.sql.Date.valueOf(batch.getReceivedDate()));
			ps.setDate(2, java.sql.Date.valueOf(batch.getExpiryDate()));
			ps.setDouble(3, batch.getCost());
			ps.setInt(4, batch.getItemQuantity());
			ps.setDouble(5, batch.getTotalWeight());
			ps.setInt(6, batch.getItem().getIdBeer());
			ps.setInt(7, batch.getID());
			ps.executeUpdate();
			// If everything runs ok, return true
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
	// Delete a given batch based on the Primary Key
	public boolean deleteBatch(Batch batch) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Moves the batch into the stock
		StockDao stockDao = new StockDaoImpl(conn);
		stockDao.removeFromStock(batch);

		String query = "DELETE FROM BATCH WHERE BATCH_ID = ?";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, batch.getID());
			ps.executeUpdate();
			// If everything runs ok, return true
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
	// Returns a list of All the Batches on the table
	public List<Batch> selectAllBatches(int choice) {

		String query = null;
		switch (choice) {

		case 1:
			// Select all the batches inside the Batch table
			query = "SELECT BATCH_ID, ITEMSREGISTRATION_BEERCODE, RECEIVEDDATE, EXPIRYDATE, COST, ITEMQUANTITY, TOTALWEIGHT FROM BATCH ORDER BY BATCH_ID";
			break;

		case 2:
			// Select ONLY the batches that have NOT expired AND are not in any order AND are not in Stock
			query = "SELECT BATCH_ID, ITEMSREGISTRATION_BEERCODE, RECEIVEDDATE, EXPIRYDATE, COST, ITEMQUANTITY, TOTALWEIGHT FROM BATCH B LEFT JOIN orderbatches OB ON b.batch_id = OB.BATCH_BATCH_ID LEFT JOIN stock s ON b.batch_id = S.BATCH_BATCH_ID WHERE B.EXPIRYDATE >= CURRENT_DATE AND ORDERS_ORDERID IS NULL AND AISLE IS NULL ORDER BY b.batch_id";
			break;
		case 3:
			// Select ONLY the batches that HAVE expired AND are not in any order AND ARE INSIDE the Stock
			query = "SELECT BATCH_ID, ITEMSREGISTRATION_BEERCODE, RECEIVEDDATE, EXPIRYDATE, COST, ITEMQUANTITY, TOTALWEIGHT FROM BATCH B LEFT JOIN orderbatches OB ON b.batch_id = OB.BATCH_BATCH_ID LEFT JOIN stock s ON b.batch_id = S.BATCH_BATCH_ID WHERE B.EXPIRYDATE < CURRENT_DATE AND ORDERS_ORDERID IS NULL AND AISLE IS NOT NULL ORDER BY b.batch_id";
			break;
		case 4:
			// Select All the batches that are INSIDE the Stock
			query = "SELECT BATCH_ID, ITEMSREGISTRATION_BEERCODE, RECEIVEDDATE, EXPIRYDATE, COST, ITEMQUANTITY, TOTALWEIGHT FROM BATCH B JOIN STOCK S ON B.BATCH_ID = S.BATCH_BATCH_ID ORDER BY b.batch_id";
			break;
		case 5:
			//Select All the Batches that are NOT instock AND have NOT expired
			query = "SELECT BATCH_ID, ITEMSREGISTRATION_BEERCODE, RECEIVEDDATE, EXPIRYDATE, COST, ITEMQUANTITY, TOTALWEIGHT FROM BATCH B LEFT JOIN orderbatches OB ON b.batch_id = OB.BATCH_BATCH_ID LEFT JOIN stock s ON b.batch_id = S.BATCH_BATCH_ID WHERE B.EXPIRYDATE >= CURRENT_DATE AND ORDERS_ORDERID IS NULL AND AISLE IS NULL ORDER BY b.batch_id";
			break;
		default:
			return null;
		}
		
		//Calls the method that access the table, passing the selected query
		return this.selectListOfBatches(query);

	}

	//Run the provided select query and returns a list of Batches
	private List<Batch> selectListOfBatches(String query) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Batch> batchesList = new ArrayList<Batch>();

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// Keeps reading the data until the last record on the dataset
			while (rs.next() == true) {
				Batch batch = new Batch();
				batch.setID(rs.getInt("BATCH_ID"));
				batch.setReceivedDate(rs.getDate("RECEIVEDDATE").toLocalDate());
				batch.setExpiryDate(rs.getDate("EXPIRYDATE").toLocalDate());
				batch.setCost(rs.getDouble("COST"));
				batch.setItemQuantity(rs.getInt("ITEMQUANTITY"));
				batch.setTotalWeight(rs.getDouble("TOTALWEIGHT"));
				batch.setItem(this.selectItem(rs.getInt("ITEMSREGISTRATION_BEERCODE")));
				batchesList.add(batch);
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
		return batchesList;
	}

	@Override
	// Returns one Batch given it Primary Key
	public Batch selectBatch(Batch targetBatch) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT BATCH_ID, ITEMSREGISTRATION_BEERCODE, RECEIVEDDATE, EXPIRYDATE, COST, ITEMQUANTITY, TOTALWEIGHT FROM BATCH WHERE BATCH_ID = ? ORDER BY BATCH_ID";
		Batch selectedBatch = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, targetBatch.getID());
			rs = ps.executeQuery();
			if (rs.next() == true) {
				selectedBatch = new Batch();
				selectedBatch.setID(rs.getInt("BATCH_ID"));
				selectedBatch.setReceivedDate(rs.getDate("RECEIVEDDATE").toLocalDate());
				selectedBatch.setExpiryDate(rs.getDate("EXPIRYDATE").toLocalDate());
				selectedBatch.setCost(rs.getDouble("COST"));
				selectedBatch.setItemQuantity(rs.getInt("ITEMQUANTITY"));
				selectedBatch.setTotalWeight(rs.getDouble("TOTALWEIGHT"));
				selectedBatch.setItem(this.selectItem(rs.getInt("ITEMSREGISTRATION_BEERCODE")));
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
		return selectedBatch;
	}

	// Select an Item given the Primary Key
	private ItemRegistration selectItem(int targetItem) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT beername, beercode FROM itemsregistration WHERE BEERCODE = ?";
		ItemRegistration selectedItem = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, targetItem);
			rs = ps.executeQuery();
			if (rs.next() == true) {
				selectedItem = new ItemRegistration();
				selectedItem.setIdBeer(rs.getInt("beercode"));
				selectedItem.setName(rs.getString(("beername")));
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
		return selectedItem;
	}

	@Override
	//Select all the Items from the Items table
	public List<ItemRegistration> selectAllItems() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<ItemRegistration> itemsList = new ArrayList<ItemRegistration>();
		String query = "SELECT beername, beercode FROM itemsregistration order BY BEERCODE";
		ItemRegistration selectedItem = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next() == true) {
				selectedItem = new ItemRegistration();
				selectedItem.setIdBeer(rs.getInt("beercode"));
				selectedItem.setName(rs.getString(("beername")));
				itemsList.add(selectedItem);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				// Closes the statement to keep memory clean
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return itemsList;
	}

}
