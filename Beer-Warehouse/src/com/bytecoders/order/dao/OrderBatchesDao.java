package com.bytecoders.order.dao;

import java.util.List;

import com.bytecoders.batch.Batch;
import com.bytecoders.order.Order;

public interface OrderBatchesDao {

	public boolean insertOrder(Order order);		
	
	public List<Batch> selectAllBacthesOfAnOrder(Order order);
	
}
