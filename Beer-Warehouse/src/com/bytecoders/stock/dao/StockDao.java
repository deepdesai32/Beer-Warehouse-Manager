package com.bytecoders.stock.dao;

import java.util.List;

import com.bytecoders.batch.Batch;
import com.bytecoders.stock.Stock;


public interface StockDao {
	
	public boolean insertIntoStock(Batch batch);
	
	public boolean removeFromStock(Batch batch);

	public boolean updateStock(Stock stock);

	public Stock selectStock(Stock targetStock);
	
	public Stock getEmptyStock();

	List<Stock> selectListOfStocks();
}
